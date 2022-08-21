package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.date.DateDescriptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author ISJINHAO
 * @Date 2021/7/31 22:59
 */
public class DateHandler extends AbstractFunctionHandler {

    public DateHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new DateDescriptor(functionCalculator));
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
        Object parameter = parameters[0];

        if (parameter instanceof Date) {
            Date date = (Date) parameter;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        } else if (parameter instanceof LocalDate) {
            return parameter;
        } else if (parameter instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) parameter;
            return localDateTime.toLocalDate();
        }
        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateType_CALCULATION, function);
        return null;
    }

}
