package com.expression;

import java.lang.reflect.Method;

/**
 *  数学库函数表达式
 */


public final class MathFuncExpression extends FuncExpression{
    private final String funcParamsSeparator = ",";
    private final String funcPatten = "Math\\.\\w+\\(.*?\\)";

    @Override
    protected String computeExpression(String expression) {
//        String[] params = getFunctionParams(expression);
//        for(int i=0; i<params.length; i++){
//
//            addParams(params[i]);
//
//            if(isFunction(params[i])) {
//                params[i] = computeExpression(params[i]);
//            }else if(isExpression(params[i]))
//                params[i] = computePureExpression(params[i]);
//        }
//        return computeFunction(opt);
        return null;
    }

    public String[] getFunctionParams(String expression){
        int leftbrackets = expression.indexOf("(");
        int lastRightbrakets = expression.lastIndexOf(")");
        expression = expression.substring(leftbrackets+1, lastRightbrakets);
        String[] params = expression.split(funcParamsSeparator);
        return  params;
    }

    private String computeMathFunction(String mathMethod, String[] params){
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

}
