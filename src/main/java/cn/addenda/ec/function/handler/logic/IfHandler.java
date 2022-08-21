package cn.addenda.ec.function.handler.logic;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.logic.IfDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/8/13 21:43
 */
public class IfHandler extends AbstractFunctionHandler {

    public IfHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new IfDescriptor(functionCalculator));
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

        Object condition = parameters[0];
        if (boolean.class.isAssignableFrom(condition.getClass()) || Boolean.class.isAssignableFrom(condition.getClass())) {
            if (Boolean.parseBoolean(condition.toString())) {
                return parameters[1];
            }
            return parameters[2];
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_unknown_CALCULATION);
        return null;
    }
}
