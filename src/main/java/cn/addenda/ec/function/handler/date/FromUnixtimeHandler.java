package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.date.FromUnixtimeDescriptor;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间戳转日期
 *
 * @Author ISJINHAO
 * @Date 2021/4/11 15:51
 */
public class FromUnixtimeHandler extends AbstractFunctionHandler {

    public FromUnixtimeHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new FromUnixtimeDescriptor(functionCalculator));
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
        Class<?> parameterClass = parameter.getClass();
        if (int.class.isAssignableFrom(parameterClass) || long.class.isAssignableFrom(parameterClass)
                || Integer.class.isAssignableFrom(parameterClass) || Long.class.isAssignableFrom(parameterClass)
                || BigInteger.class.isAssignableFrom(parameterClass)) {
            // from_unixtime传入的是秒，需要 multiply 1000
            Instant instant = Instant.ofEpochMilli(Long.parseLong(parameter.toString()) * 1000);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateParameter_CALCULATION, function);
        return null;
    }
}
