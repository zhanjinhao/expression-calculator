package cn.addenda.ec.function.handler.string;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.string.SubstringDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/28 19:12
 */
public class SubstringHandler extends AbstractFunctionHandler {

    public SubstringHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new SubstringDescriptor(functionCalculator));
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
        int length = parameters.length;
        if (length == 2) {
            return substring2(parameters);
        } else if (length == 3) {
            return substring3(parameters);
        }
        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_unknown_CALCULATION);
        return null;
    }

    private Object substring3(Object[] parameters) {
        Object parameter = parameters[1];
        if (parameter instanceof String) {
            return substring3String(parameters);
        } else {
            return substring3Numeric(parameters);
        }
    }

    private Object substring3Numeric(Object[] parameters) {
        String str = (String) parameters[0];
        int index = Integer.parseInt(parameters[1].toString());
        int len = Integer.parseInt(parameters[2].toString());

        int length = str.length();

        if (index < 0) {
            index = index + length;
        }

        if (index < 0) {
            return "";
        }

        int end = Math.min(index + len, length);

        return str.substring(index, end);
    }

    private Object substring3String(Object[] parameters) {
        String str = (String) parameters[0];
        String seg = (String) parameters[1];
        int len = Integer.parseInt(parameters[2].toString());

        int index = str.indexOf(seg);
        if (index == -1) {
            return "";
        }

        int end = Math.min(index + len, str.length());

        return str.substring(index + seg.length(), end);
    }

    private Object substring2(Object[] parameters) {
        String str = (String) parameters[0];
        int index = Integer.parseInt(parameters[1].toString());

        int length = str.length();

        if (index < 0) {
            index = index + length;
        }

        if (index < 0) {
            return "";
        }

        if (length < index) {
            return "";
        }
        return str.substring(index);
    }
}
