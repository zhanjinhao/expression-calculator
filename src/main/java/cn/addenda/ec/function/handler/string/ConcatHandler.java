package cn.addenda.ec.function.handler.string;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.string.ConcatDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/28 19:13
 */
public class ConcatHandler extends AbstractFunctionHandler {

    public ConcatHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new ConcatDescriptor(functionCalculator));
    }

    @Override
    public boolean isIndependent() {
        return functionDescriptor.isIndependent();
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

        StringBuilder sb = new StringBuilder();
        for (Object parameter : parameters) {
            sb.append(parameter);
        }

        return sb.toString();
    }
}
