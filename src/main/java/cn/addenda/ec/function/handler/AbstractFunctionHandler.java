package cn.addenda.ec.function.handler;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.function.descriptor.FunctionDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author ISJINHAO
 * @Date 2021/7/29 13:56
 */
public abstract class AbstractFunctionHandler extends ErrorReportableFunctionHandler {

    protected FunctionCalculator functionCalculator;

    protected FunctionDescriptor functionDescriptor;

    protected AbstractFunctionHandler(FunctionCalculator functionCalculator, FunctionDescriptor functionDescriptor) {
        this.functionCalculator = functionCalculator;
        this.functionDescriptor = functionDescriptor;
    }

    protected int getNumericValue(Object date, Token token) {
        if (date instanceof Date) {
            return getNumericValueFromDate((Date) date, token);
        } else if (date instanceof LocalDate) {
            return getNumericValueFromLocalDate((LocalDate) date, token);
        } else if (date instanceof LocalDateTime) {
            return getNumericValueFromLocalDateTime((LocalDateTime) date, token);
        } else if (date instanceof LocalTime) {
            return getNumericValueFromLocalTime((LocalTime) date, token);
        }
        return -1;
    }

    private int getNumericValueFromLocalTime(LocalTime date, Token token) {
        if (DateConst.HOUR.equals(token)) {
            return date.get(ChronoField.HOUR_OF_DAY);
        } else if (DateConst.MINUTE.equals(token)) {
            return date.get(ChronoField.MINUTE_OF_HOUR);
        } else if (DateConst.SECOND.equals(token)) {
            return date.get(ChronoField.SECOND_OF_MINUTE);
        } else if (DateConst.MICROSECOND.equals(token)) {
            return date.get(ChronoField.MICRO_OF_SECOND);
        }
        return -1;

    }

    private int getNumericValueFromLocalDateTime(LocalDateTime date, Token token) {
        if (DateConst.YEAR.equals(token)) {
            return date.get(ChronoField.YEAR);
        } else if (DateConst.MONTH.equals(token)) {
            return date.get(ChronoField.MONTH_OF_YEAR);
        } else if (DateConst.DAY.equals(token)) {
            return date.get(ChronoField.DAY_OF_MONTH);
        } else if (DateConst.HOUR.equals(token)) {
            return date.get(ChronoField.HOUR_OF_DAY);
        } else if (DateConst.MINUTE.equals(token)) {
            return date.get(ChronoField.MINUTE_OF_HOUR);
        } else if (DateConst.SECOND.equals(token)) {
            return date.get(ChronoField.SECOND_OF_MINUTE);
        } else if (DateConst.MICROSECOND.equals(token)) {
            // Calendar只能拿到毫秒，转为微秒需要 multiply 1000
            return date.get(ChronoField.MICRO_OF_SECOND);
        }
        return -1;
    }

    private int getNumericValueFromLocalDate(LocalDate date, Token token) {
        if (DateConst.YEAR.equals(token)) {
            return date.get(ChronoField.YEAR);
        } else if (DateConst.MONTH.equals(token)) {
            return date.get(ChronoField.MONTH_OF_YEAR);
        } else if (DateConst.DAY.equals(token)) {
            return date.get(ChronoField.DAY_OF_MONTH);
        }
        return -1;
    }

    private int getNumericValueFromDate(Date date, Token token) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (DateConst.YEAR.equals(token)) {
            return calendar.get(Calendar.YEAR);
        } else if (DateConst.MONTH.equals(token)) {
            return calendar.get(Calendar.MONTH) + 1;
        } else if (DateConst.DAY.equals(token)) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        } else if (DateConst.HOUR.equals(token)) {
            return calendar.get(Calendar.HOUR_OF_DAY);
        } else if (DateConst.MINUTE.equals(token)) {
            return calendar.get(Calendar.MINUTE);
        } else if (DateConst.SECOND.equals(token)) {
            return calendar.get(Calendar.SECOND);
        } else if (DateConst.MICROSECOND.equals(token)) {
            // Calendar只能拿到毫秒，转为微秒需要 multiply 1000
            return calendar.get(Calendar.MILLISECOND) * 1000;
        }

        return -1;
    }

}
