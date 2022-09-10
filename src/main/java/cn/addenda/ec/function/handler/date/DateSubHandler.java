package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandler;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.ast.expression.TimeInterval;
import cn.addenda.ro.grammar.function.descriptor.date.DateSubDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/27 21:46
 */
public class DateSubHandler extends AbstractFunctionHandler {

    public DateSubHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new DateSubDescriptor(functionCalculator));
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
        FunctionHandler dateAddFunction = functionCalculator.getFunction("date_add");

        TimeInterval interval = (TimeInterval) parameters[1];
        TimeInterval intervalParameter = new TimeInterval(interval.getTimeType(), interval.getInterval().negate());

        return dateAddFunction.evaluate(function, type, parameters[0], intervalParameter);
    }

}
