package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ec.utils.DateUtils;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.ast.expression.TimeInterval;
import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.function.descriptor.date.DateAddDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @Author ISJINHAO
 * @Date 2021/7/27 21:46
 */
public class DateAddHandler extends AbstractFunctionHandler {

    public DateAddHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new DateAddDescriptor(functionCalculator));
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
        TimeInterval interval = (TimeInterval) parameters[1];
        if (date instanceof Date) {
            return dateAdd((Date) date, interval);
        } else if (date instanceof LocalDateTime) {
            return localDateTimeAdd((LocalDateTime) date, interval);
        } else if (date instanceof LocalDate) {
            return localDateAdd((LocalDate) date, interval);
        } else if (date instanceof LocalTime) {
            return localTimeAdd((LocalTime) date, interval);
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateType_CALCULATION, function);
        return null;
    }

    private Object localTimeAdd(LocalTime date, TimeInterval interval) {
        long intervalValue = interval.getInterval().longValue();
        Token type = interval.getTimeType();

        if (DateConst.MICROSECOND.equals(type)) {
            return date.plusNanos(intervalValue * 1000000);
        } else if (DateConst.SECOND.equals(type)) {
            return date.plusSeconds(intervalValue);
        } else if (DateConst.MINUTE.equals(type)) {
            return date.plusMinutes(intervalValue);
        } else if (DateConst.HOUR.equals(type)) {
            return date.plusHours(intervalValue);
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION, this);
        return null;
    }

    private Object localDateAdd(LocalDate date, TimeInterval interval) {
        long intervalValue = interval.getInterval().longValue();
        Token type = interval.getTimeType();

        if (DateConst.DAY.equals(type)) {
            return date.plusDays(intervalValue);
        } else if (DateConst.WEEK.equals(type)) {
            return date.plusWeeks(intervalValue);
        } else if (DateConst.MONTH.equals(type)) {
            return date.plusMonths(intervalValue);
        } else if (DateConst.QUARTER.equals(type)) {
            return date.plusMonths(3 * intervalValue);
        } else if (DateConst.YEAR.equals(type)) {
            return date.plusYears(intervalValue);
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION, this);
        return null;

    }

    private Object localDateTimeAdd(LocalDateTime date, TimeInterval interval) {
        long intervalValue = interval.getInterval().longValue();
        Token type = interval.getTimeType();

        if (DateConst.MICROSECOND.equals(type)) {
            return date.plusNanos(intervalValue * 1000000);
        } else if (DateConst.SECOND.equals(type)) {
            return date.plusSeconds(intervalValue);
        } else if (DateConst.MINUTE.equals(type)) {
            return date.plusMinutes(intervalValue);
        } else if (DateConst.HOUR.equals(type)) {
            return date.plusHours(intervalValue);
        } else if (DateConst.DAY.equals(type)) {
            return date.plusDays(intervalValue);
        } else if (DateConst.WEEK.equals(type)) {
            return date.plusWeeks(intervalValue);
        } else if (DateConst.MONTH.equals(type)) {
            return date.plusMonths(intervalValue);
        } else if (DateConst.QUARTER.equals(type)) {
            return date.plusMonths(3 * intervalValue);
        } else if (DateConst.YEAR.equals(type)) {
            return date.plusYears(intervalValue);
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION, this);
        return null;
    }

    private Object dateAdd(Date date, TimeInterval interval) {
        int intervalValue = interval.getInterval().intValue();
        Token type = interval.getTimeType();

        if (DateConst.MICROSECOND.equals(type)) {
            return DateUtils.addMilliseconds(date, intervalValue);
        } else if (DateConst.SECOND.equals(type)) {
            return DateUtils.addSeconds(date, intervalValue);
        } else if (DateConst.MINUTE.equals(type)) {
            return DateUtils.addMinutes(date, intervalValue);
        } else if (DateConst.HOUR.equals(type)) {
            return DateUtils.addHours(date, intervalValue);
        } else if (DateConst.DAY.equals(type)) {
            return DateUtils.addDays(date, intervalValue);
        } else if (DateConst.WEEK.equals(type)) {
            return DateUtils.addWeeks(date, intervalValue);
        } else if (DateConst.MONTH.equals(type)) {
            return DateUtils.addMonths(date, intervalValue);
        } else if (DateConst.QUARTER.equals(type)) {
            return DateUtils.addMonths(date, intervalValue * 3);
        } else if (DateConst.YEAR.equals(type)) {
            return DateUtils.addYears(date, intervalValue);
        }

        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION, this);
        return null;
    }

}
