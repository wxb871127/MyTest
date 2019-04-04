package com.expression;

import android.view.View;
import android.view.ViewGroup;

/**
 *   表达式类基类  函数表达式，双目表达式中包含了其他表达式采用组合模式
 */
public class Expression {
    //表达式解析控制流程使用模版模式实现
    protected  String computeExpression(String expression){

        return null;
    }

    protected int getExpressionType(){
        return 0;
    }
}
