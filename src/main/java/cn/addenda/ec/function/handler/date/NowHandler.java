package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.date.NowDescriptor;

import java.time.LocalDateTime;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 14:56
 */
public class NowHandler extends AbstractFunctionHandler {

    public NowHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new NowDescriptor());
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
        return LocalDateTime.now();
    }

}
