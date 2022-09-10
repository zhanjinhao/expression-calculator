package cn.addenda.ec.function.handler.string;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ro.grammar.ast.expression.*;
import cn.addenda.ro.grammar.function.descriptor.string.ReplaceDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/28 19:11
 */
public class ReplaceHandler extends AbstractFunctionHandler {

    public ReplaceHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new ReplaceDescriptor(functionCalculator));
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

        String str = (String) parameters[0];
        String oldSeg = (String) parameters[1];
        String newSeg = (String) parameters[2];

        return str.replaceAll(oldSeg, newSeg);
    }

}
