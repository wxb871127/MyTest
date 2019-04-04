package com.expression;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *  抽象函数表达式 用于扩展
 */
public abstract class FuncExpression extends Expression{
    protected List<Expression> params;//函数参数，这里存放函数的参数就可以多态实现具体的计算了
    protected void addParams(Expression expression){
        if(params == null)
            params = new ArrayList<>();
        params.add(expression);
    }

}
