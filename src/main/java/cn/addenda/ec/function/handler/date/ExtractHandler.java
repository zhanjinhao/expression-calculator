package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.*;
import cn.addenda.ro.grammar.function.descriptor.date.ExtractDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;

/**
 * @Author ISJINHAO
 * @Date 2021/8/15 20:33
 */
public class ExtractHandler extends AbstractFunctionHandler {

    public ExtractHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new ExtractDescriptor(functionCalculator));
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

        TimeUnit timeUnit = (TimeUnit) parameters[0];

        Token token = timeUnit.getTimeType();
        Literal literal = (Literal) timeUnit.getCurd();

        int numericValue = getNumericValue(literal.getValue().getLiteral(), token);

        if (numericValue == -1) {
            error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION);
            return null;
        }

        return numericValue;
    }
}
