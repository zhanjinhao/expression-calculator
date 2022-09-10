package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.date.UnixTimestampDescriptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期转时间戳
 *
 * @Author ISJINHAO
 * @Date 2021/4/11 14:54
 */
public class UnixTimestampHandler extends AbstractFunctionHandler {

    public UnixTimestampHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new UnixTimestampDescriptor(functionCalculator));
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

        Object date = parameters[0];

        if (date instanceof Date) {
            return ((Date) date).getTime();
        } else if (date instanceof LocalDateTime) {
            return ((LocalDateTime) date).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateType_CALCULATION, function);

        return null;
    }

}
