package cn.addenda.ec.function.handler.logic;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.logic.DecodeDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/31 8:45
 */
public class DecodeHandler extends AbstractFunctionHandler {

    public DecodeHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new DecodeDescriptor());
    }

    @Override
    public String functionName() {
        return functionDescriptor.functionName();
    }

    @Override
    public int innerType() {
        return functionDescriptor.innerType();
    }

    @Override
    public void staticCheck(Function function, CurdType type) {
        functionDescriptor.staticCheck(function, type);
    }

    @Override
    public Object evaluate(Function function, CurdType type, Object... parameters) {
        int length = parameters.length;
        Object stand = parameters[0];

        for (int i = 1; i < length - 1; i = i + 2) {
            Object condition = parameters[i];
            Object result = parameters[i + 1];
            if (stand.equals(condition)) {
                return result;
            }
        }

        return parameters[length - 1];
    }

}
