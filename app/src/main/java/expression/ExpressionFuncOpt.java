package expression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  函数表达式
 */
public class ExpressionFuncOpt {
    private static final String funcParamsSeparator = ",";
    //支持的函数名
    private static final List<String> functions = new ArrayList<String>(){
        {
            add("Math\\.\\w+"); //数学库
        }
    };
    private static String[] params;//形参列表
    private static String expression;//完整表达式
    private static int startIndex;//表达式中开始索引
    private static int endIndex;//表达式中结束索引


    public static String getFuncParamsSeparator(){
        return funcParamsSeparator;
    }

    //判断是否为函数形参分隔符
    public static boolean isFuncParamsSeparator(String str){
        return str.equals(funcParamsSeparator);
    }

    //判断是否为函数
    public static boolean isFunction(String str) {
        for (String s : functions) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find())
                return true;
        }
        return false;
    }

    //获取函数表达式
    public static List<ExpressionFuncOpt> getFunctionExpression(String str){
        List<ExpressionFuncOpt> list = new ArrayList<>();
        boolean find = false;
        for (String s : functions) {
            s +=  "\\(.*?\\)";
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()){
                find = true;
                ExpressionFuncOpt opt = new ExpressionFuncOpt();
                opt.expression = matcher.group();
                opt.startIndex = matcher.start();
                opt.endIndex = matcher.end();
                opt.params = getFunctionParams(matcher.group());
                list.add(opt);
            }
            if(find)
                return list;
        }
        return null;
    }

    public static String[] getFunctionParams(String expression){
        int leftbrackets = expression.indexOf("(");
        int lastRightbrakets = expression.lastIndexOf(")");
        expression = expression.substring(leftbrackets+1, lastRightbrakets);
        String[] params = expression.split(funcParamsSeparator);
        return  params;
    }

    public static String[] getFunctionParams(){
        return params;
    }
}
