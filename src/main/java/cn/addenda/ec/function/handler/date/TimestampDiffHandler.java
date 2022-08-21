package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.*;
import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.function.descriptor.date.TimestampDiffDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author ISJINHAO
 * @Date 2021/7/27 23:23
 */
public class TimestampDiffHandler extends AbstractFunctionHandler {

    public TimestampDiffHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new TimestampDiffDescriptor(functionCalculator));
    }

    private static final LocalTime ZERO_TIME = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
    private static final LocalDate ZERO_DATE = LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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

        Token interval = ((Identifier) ((Attachment) parameters[0]).getAttachment()).getName();
        Object start = parameters[1];
        Object end = parameters[2];

        // 时间只能和时间计算
        if ((start instanceof LocalTime && !(end instanceof LocalTime)) ||
                (!(start instanceof LocalTime) && end instanceof LocalTime)) {
            error(FunctionHandlerROErrorReporterDelegate.FUNCTION_unknown_CALCULATION);
            return null;
        }
        LocalDateTime startDateTime = completeDate(start);
        LocalDateTime sendDateTime = completeDate(end);

        if (DateConst.YEAR.equals(interval)) {
            return ChronoUnit.YEARS.between(startDateTime, sendDateTime);
        } else if (DateConst.MONTH.equals(interval)) {
            return ChronoUnit.MONTHS.between(startDateTime, sendDateTime);
        } else if (DateConst.DAY.equals(interval)) {
            return ChronoUnit.DAYS.between(startDateTime, sendDateTime);
        } else if (DateConst.HOUR.equals(interval)) {
            return ChronoUnit.HOURS.between(startDateTime, sendDateTime);
        } else if (DateConst.MINUTE.equals(interval)) {
            return ChronoUnit.MINUTES.between(startDateTime, sendDateTime);
        } else if (DateConst.SECOND.equals(interval)) {
            return ChronoUnit.SECONDS.between(startDateTime, sendDateTime);
        } else if (DateConst.MICROSECOND.equals(interval)) {
            return ChronoUnit.MICROS.between(startDateTime, sendDateTime);
        }
        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION);
        return null;
    }

    private LocalDateTime completeDate(Object date) {
        if (date instanceof Date) {
            return ((Date) date).toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).atTime(ZERO_TIME);
        } else if (date instanceof LocalTime) {
            return ZERO_DATE.atTime((LocalTime) date);
        } else if (date instanceof LocalDateTime) {
            return (LocalDateTime) date;
        }
        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_unknown_CALCULATION);
        return null;
    }

}
