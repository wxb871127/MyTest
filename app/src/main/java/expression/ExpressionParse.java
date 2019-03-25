package expression;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static expression.ExpressionFuncOpt.isFunction;
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
                    if(isVariable(curCharBuilder.toString())) {//变量就直接赋值
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


        computeFunctionExpression(expression);
        StringBuilder curCharBuilder = new StringBuilder("");
        Stack<String> optStack = new Stack<>(); // 运算符栈
        Stack<BigDecimal> numStack = new Stack<>(); // 数值栈，数值以BigDecimal存储计算，避免精度计算问题

        return null;
    }

    //计算函数表达式的值
    private static String computeFunctionExpression(String expression){
        List<ExpressionFuncOpt> list = ExpressionFuncOpt.getFunctionExpression(expression);
        if(list == null || list.size() == 0)
            return expression;
        Stack<ExpressionFuncOpt> stack = new Stack<>();
        for(ExpressionFuncOpt opt : list){//处理每个函数
           stack.push(opt);
        }
        return null;
    }

}
