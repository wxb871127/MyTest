package expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static expression.ExpressionFuncOpt.getFuncParamsSeparator;
import static expression.ExpressionFuncOpt.isFunction;

/**
 *  表达式运算符
 */
public class ExpressionOpt {
    // 运算符优先级map
    private static final Map<String, Integer> OPT_PRIORITY_MAP = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 6968472606692771458L;
        {
            put("(", 0);

            put("+", 2);
            put("-", 2);
            put("!", 2);

            put("*", 3);
            put("/", 3);
            put("%", 3);

            put(">", 6);
            put(">=", 6);
            put("<", 6);
            put("<=", 6);

            put("==", 7);
            put("!=", 7);
            put("contains", 7);
            put(")", 7);

            put("&&", 11);
            put("||", 12);

            put("=", 20);
        }
    };

    // 判断是否为运算符
    public static boolean isOption(String str){
        return OPT_PRIORITY_MAP.containsKey(str);
    }

    //判断字符串是否是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?\\d+\\.?\\d*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isVariable(String str){
        return  (!isNumeric(str) && !isFunction(str) && !isOption(str)) && !str.contains(getFuncParamsSeparator());
    }
}
