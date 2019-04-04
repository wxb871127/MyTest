package expression;

import android.text.TextUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static expression.ExpressionFuncOpt.getFunctionExpression;
import static expression.ExpressionFuncOpt.isFunction;
import static expression.ExpressionOpt.OPT_PRIORITY_MAP;
import static expression.ExpressionOpt.isExpression;
import static expression.ExpressionOpt.isNumeric;
import static expression.ExpressionOpt.isOption;
import static expression.ExpressionOpt.isVariable;

/**
 * 表达式解释器
 */
public class ExpressionParse {

    public static String parseExpression(String expression, Map<String, Object> valueMap){
        if(isNumeric(expression))
            return expression;

        String finalExpression = getFinalExpression(expression, valueMap);
        if(finalExpression == null)
            throw new IllegalArgumentException("表达式中的变量找不到对应的值");

        return computeExpression(finalExpression);
    }

    //获取无变量的表达式
    private static String getFinalExpression(String expression, Map<String, Object> valueMap){
        StringBuilder curCharBuilder = new StringBuilder("");
        StringBuilder finalExpression = new StringBuilder();
        for (int i=0; i<expression.length(); i++) {
            char charact = expression.charAt(i);
            if (charact != ' ') {
                if(!isOption(String.valueOf(charact)) && !isOption(curCharBuilder.toString())){//不是运算符就拼装
                    curCharBuilder.append(charact);
                }else {
                    if(!TextUtils.isEmpty(curCharBuilder.toString()) && isVariable(curCharBuilder.toString())) {//变量就直接赋值
                        String value = (String) valueMap.get(curCharBuilder.toString());
                        if(value == null || TextUtils.isEmpty(value))
                            throw new IllegalArgumentException("map中找不到变量" + curCharBuilder.toString() + "的值");
                        finalExpression.append(value);
                    }else
                        finalExpression.append(curCharBuilder);
                    finalExpression.append(charact);
                    curCharBuilder.delete(0, curCharBuilder.length());
                }
            }
        }
        return finalExpression.toString();
    }

    //计算表达式的值
    public static String computeExpression(String expression){
        List<ExpressionFuncOpt> list = ExpressionFuncOpt.getFunctionExpression(expression);
        if(list != null || list.size() > 0){
            for(ExpressionFuncOpt opt : list){
                expression = expression.replace(opt.getExpression(), computeFunctionExpression(opt));
            }
        }
        return computePureExpression(expression);
    }

    //计算函数表达式的值
    private static String computeFunctionExpression(ExpressionFuncOpt opt){
        String[] params = opt.getFunctionParams();
        for(int i=0; i<params.length; i++){
           if(isFunction(params[i])) {
               getFunctionExpression(params[i]);
//               params[i] = computeFunctionExpression(params[i]);
           }else if(isExpression(params[i]))
               params[i] = computePureExpression(params[i]);
        }
        return computeFunction(opt);
//    return null;
    }

    private static String computeFunction(ExpressionFuncOpt opt){
        String optMethod = opt.getFunctionMehod();
        if(optMethod.startsWith("Math."))
            return computeMathFunction(optMethod.substring(optMethod.indexOf(".")+1, optMethod.length()), opt.getFunctionParams());
        return null;
    }

    private static String computeMathFunction(String mathMethod, String[] params){
        try{
            Class mathCls = Class.forName("java.lang.Math");
            Method[] methods = mathCls.getDeclaredMethods();
            Object[] invokParams = new Object[params.length];
            for (Method method : methods) {
                if (mathMethod.equals(method.getName())) {
                    Class[] parameterTypes = method.getParameterTypes();
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (parameterTypes[i] == double.class) {
                            invokParams[i] = Double.parseDouble(params[i]);
                        } else if (parameterTypes[i] == float.class) {
                            invokParams[i] = Float.parseFloat(params[i]);
                        } else if (parameterTypes[i] == int.class) {
                            invokParams[i] = Integer.parseInt(params[i]);
                        } else if (parameterTypes[i] == long.class) {
                            invokParams[i] = Long.parseLong(params[i]);
                        }
                    }
                    Object object = method.invoke(null, invokParams);
                    return object.toString();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String computePureExpression(String expression){
        StringBuilder curNumBuilder = new StringBuilder("");
        Stack<String> optStack = new Stack<>(); // 运算符栈
        Stack<BigDecimal> numStack = new Stack<>(); // 数值栈，数值以BigDecimal存储计算，避免精度计算问题
        // 逐个读取字符，并根据运算符判断参与何种计算
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c != ' ') { // 空白字符直接丢弃掉
                if ((c >= '0' && c <= '9') || c == '.') {
                    curNumBuilder.append(c); // 持续读取一个数值的各个字符
                } else {
                    if (curNumBuilder.length() > 0) {
                        // 如果追加器有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
                        numStack.push(new BigDecimal(curNumBuilder.toString()));
                        curNumBuilder.delete(0, curNumBuilder.length());
                    }

                    String curOpt = String.valueOf(c);
                    if (optStack.empty()) {
                        // 运算符栈栈顶为空则直接入栈
                        optStack.push(curOpt);
                    } else {
                        if (curOpt.equals("(")) {
                            // 当前运算符为左括号，直接入运算符栈
                            optStack.push(curOpt);
                        } else if (curOpt.equals(")")) {
                            // 当前运算符为右括号，触发括号内的字表达式进行计算
                            directCalc(optStack, numStack, true);
                        } else if (curOpt.equals("=")) {
                            // 当前运算符为等号，触发整个表达式剩余计算，并返回总的计算结果
                            directCalc(optStack, numStack, false);
                            return numStack.pop().toString();//.doubleValue();
                        } else {
                            // 当前运算符为加减乘除之一，要与栈顶运算符比较，判断是否要进行一次二元计算
                            compareAndCalc(optStack, numStack, curOpt);
                        }
                    }
                }
            }
        }

        // 表达式不是以等号结尾的场景
        if (curNumBuilder.length() > 0) {
            // 如果追加器有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
            numStack.push(new BigDecimal(curNumBuilder.toString()));
        }
        directCalc(optStack, numStack, false);
        return numStack.pop().toString();//.doubleValue();
    }

    public static void directCalc(Stack<String> optStack, Stack<BigDecimal> numStack,
                                  boolean isBracket) {
        String opt = optStack.pop(); // 当前参与计算运算符
        BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
        BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
        BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);

        // 计算结果当做操作数入栈
        numStack.push(bigDecimal);

        if (isBracket) {
            if ("(".equals(optStack.peek())) {
                // 括号类型则遇左括号停止计算，同时将左括号从栈中移除
                optStack.pop();
            } else {
                directCalc(optStack, numStack, isBracket);
            }
        } else {
            if (!optStack.empty()) {
                // 等号类型只要栈中还有运算符就继续计算
                directCalc(optStack, numStack, isBracket);
            }
        }
    }

    public static void compareAndCalc(Stack<String> optStack, Stack<BigDecimal> numStack,
                                      String curOpt) {
        // 比较当前运算符和栈顶运算符的优先级
        String peekOpt = optStack.peek();
        int priority = getPriority(peekOpt, curOpt);
        if (priority == -1 || priority == 0) {
            // 栈顶运算符优先级大或同级，触发一次二元运算
            String opt = optStack.pop(); // 当前参与计算运算符
            BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
            BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
            BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);

            // 计算结果当做操作数入栈
            numStack.push(bigDecimal);

            // 运算完栈顶还有运算符，则还需要再次触发一次比较判断是否需要再次二元计算
            if (optStack.empty()) {
                optStack.push(curOpt);
            } else {
                compareAndCalc(optStack, numStack, curOpt);
            }
        } else {
            // 当前运算符优先级高，则直接入栈
            optStack.push(curOpt);
        }
    }

    public static BigDecimal floatingPointCalc(String opt, BigDecimal bigDecimal1,
                                               BigDecimal bigDecimal2) {
        BigDecimal resultBigDecimal = new BigDecimal(0);
        switch (opt) {
            case "+":
                resultBigDecimal = bigDecimal1.add(bigDecimal2);
                break;
            case "-":
                resultBigDecimal = bigDecimal1.subtract(bigDecimal2);
                break;
            case "*":
                resultBigDecimal = bigDecimal1.multiply(bigDecimal2);
                break;
            case "/":
                resultBigDecimal = bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_DOWN); // 注意此处用法
                break;
            default:
                break;
        }
        return resultBigDecimal;
    }

    public static int getPriority(String opt1, String opt2) {
        int priority = OPT_PRIORITY_MAP.get(opt2) - OPT_PRIORITY_MAP.get(opt1);
        return priority;
    }
}
