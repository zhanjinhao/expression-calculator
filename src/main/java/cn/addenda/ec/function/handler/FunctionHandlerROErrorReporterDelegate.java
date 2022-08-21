package cn.addenda.ec.function.handler;

import cn.addenda.ro.error.ROError;
import cn.addenda.ro.error.reporter.AbstractROErrorReporterDelegate;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.FunctionException;
import cn.addenda.ro.grammar.lexical.token.Token;

/**
 * AstROErrorReporterDelegate 和 FunctionROErrorReporterDelegate 区别：
 * 一个SQL语句对应一个Ast，所以，将tokenSequence传入到AstROErrorReporterDelegate中，就可以将Ast当ROError用。
 * FunctionHandler会在多个Ast中被使用，所以不能将Function传入FunctionROErrorReporterDelegate，即无法将FunctionHandler当ROError用。
 *
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

        addSuffixFunction(FunctionHandler.class, (error) -> {
            FunctionHandler functionHandler = (FunctionHandler) error;
            return "Current functionHandler is: " + functionHandler.functionName() + ".";
        });

    }

    @Override
    public void error(int errorCode) {
        throw new FunctionException(errorCode, getErrorMsg(errorCode));
    }

    @Override
    public void error(int errorCode, ROError attachment) {
        throw new FunctionException(errorCode, getErrorMsg(errorCode) + SEPARATOR + getSuffix(attachment));
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

    public static final int FUNCTION_HANDLER_REPEATED = 51000;
    public static final String FUNCTION_HANDLER_REPEATED_MSG = "FunctionHandler has existed: ";

    public static final int FUNCTION_HANDLER_INSTANTIATION = 52000;
    public static final String FUNCTION_HANDLER_INSTANTIATION_MSG = "Failed to instantiate functionHandler: ";

}
