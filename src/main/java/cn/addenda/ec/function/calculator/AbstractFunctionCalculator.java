package cn.addenda.ec.function.calculator;

import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.error.ROError;
import cn.addenda.ro.error.reporter.ROErrorReporter;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.FunctionDescriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author addenda
 * @datetime 2021/7/24 23:21
 */
public abstract class AbstractFunctionCalculator implements FunctionCalculator, ROErrorReporter {

    private final HashMap<String, FunctionHandler> functionHandlerMap = new HashMap<>();

    private final ROErrorReporter errorReporterDelegate = new FunctionHandlerROErrorReporterDelegate();

    @Override
    public void addFunction(Class<? extends FunctionHandler> function) {
        if (function == null) {
            return;
        }
        try {
            FunctionHandler functionHandler;
            // 对于 AbstractFunctionHandler 来说，需要调用带 FunctionCalculator 参数的构造方法
            if (AbstractFunctionHandler.class.isAssignableFrom(function)) {
                Constructor<? extends FunctionDescriptor> constructor = function.getConstructor(FunctionCalculator.class);
                functionHandler = (FunctionHandler) constructor.newInstance(this);
            } else {
                functionHandler = function.newInstance();
            }
            if (functionHandlerMap.containsKey(functionHandler.functionName())) {
                error(FunctionHandlerROErrorReporterDelegate.FUNCTION_HANDLER_REPEATED);
                return;
            }
            functionHandlerMap.put(functionHandler.functionName(), functionHandler);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            error(FunctionHandlerROErrorReporterDelegate.FUNCTION_HANDLER_INSTANTIATION, e);
        }
    }

    @Override
    public FunctionHandler getFunction(String functionName) {
        return functionHandlerMap.get(functionName);
    }

    @Override
    public Set<String> functionNameSet() {
        return functionHandlerMap.keySet();
    }

    @Override
    public Map<String, Integer> functionNameInnerType() {
        Map<String, Integer> map = new HashMap<>();
        functionHandlerMap.forEach((key, value) -> map.put(key, value.innerType()));
        return map;
    }

    @Override
    public void staticCheck(Function function, CurdType type) {
        String functionName = (String) function.getMethod().getLiteral();
        functionHandlerMap.get(functionName).staticCheck(function, type);
    }

    @Override
    public Object evaluate(Function function, CurdType type, Object... parameters) {
        String functionName = (String) function.getMethod().getLiteral();
        return getFunction(functionName).evaluate(function, type, parameters);
    }

    @Override
    public void error(int errorCode) {
        errorReporterDelegate.error(errorCode);
    }

    @Override
    public void error(int errorCode, ROError attachment) {
        errorReporterDelegate.error(errorCode, attachment);
    }

    @Override
    public void error(int errorCode, Throwable throwable) {
        errorReporterDelegate.error(errorCode, throwable);
    }
}
