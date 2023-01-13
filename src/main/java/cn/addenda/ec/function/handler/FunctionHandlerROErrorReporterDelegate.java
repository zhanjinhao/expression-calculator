package cn.addenda.ec.function.handler;

import cn.addenda.ro.error.ROError;
import cn.addenda.ro.error.reporter.AbstractROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.FunctionException;
import cn.addenda.ro.grammar.lexical.token.Token;

/**
 * @Author ISJINHAO
 * @Date 2021/7/27 22:23
 */
public class FunctionHandlerROErrorReporterDelegate extends AbstractROErrorReporterDelegate {

    @Override
    public void fillErrorMsg() {
        addErrorMsg(FUNCTION_unknown_CALCULATION, FUNCTION_unknown_CALCULATION_MSG);
        addErrorMsg(FUNCTION_dateType_CALCULATION, FUNCTION_dateType_CALCULATION_MSG);
        addErrorMsg(FUNCTION_dateParameter_CALCULATION, FUNCTION_dateParameter_CALCULATION_MSG);
        addErrorMsg(FUNCTION_formatPattern_CALCULATION, FUNCTION_formatPattern_CALCULATION_MSG);
        addErrorMsg(FUNCTION_repeatedPattern_CALCULATION, FUNCTION_repeatedPattern_CALCULATION_MSG);
        addErrorMsg(FUNCTION_HANDLER_REPEATED, FUNCTION_HANDLER_REPEATED_MSG);
        addErrorMsg(FUNCTION_HANDLER_INSTANTIATION, FUNCTION_HANDLER_INSTANTIATION_MSG);

        addSuffixFunction(Function.class, (error) -> {
            Function function = (Function) error;
            Token method = function.getMethod();
            return "Current method is: " + method.getLiteral() + ", and current index is: " + method.getIndex() + ".";
        });
    }

    @Override
    public void error(int errorCode) {
        throw new FunctionException(errorCode, getErrorMsg(errorCode));
    }

    @Override
    public void error(int errorCode, ROError roError) {
        throw new FunctionException(errorCode, getErrorMsg(errorCode) + SEPARATOR + getSuffix(roError));
    }

    public static final int FUNCTION_unknown_CALCULATION = 50002;
    public static final String FUNCTION_unknown_CALCULATION_MSG = "Error occur when evaluating function. ";

    public static final int FUNCTION_dateType_CALCULATION = 50003;
    public static final String FUNCTION_dateType_CALCULATION_MSG = "Unsupported date type. ";

    public static final int FUNCTION_dateParameter_CALCULATION = 50004;
    public static final String FUNCTION_dateParameter_CALCULATION_MSG = "Unsupported date parameter. ";

    public static final int FUNCTION_formatPattern_CALCULATION = 50005;
    public static final String FUNCTION_formatPattern_CALCULATION_MSG = "Unsupported format pattern. ";

    public static final int FUNCTION_repeatedPattern_CALCULATION = 50006;
    public static final String FUNCTION_repeatedPattern_CALCULATION_MSG = "Repeated format pattern. ";

    public static final int FUNCTION_HANDLER_REPEATED = 51001;
    public static final String FUNCTION_HANDLER_REPEATED_MSG = "FunctionHandler has existed: ";

    public static final int FUNCTION_HANDLER_INSTANTIATION = 52001;
    public static final String FUNCTION_HANDLER_INSTANTIATION_MSG = "Failed to instantiate functionHandler: ";

}
