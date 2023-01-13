package cn.addenda.ec.calculator;

import cn.addenda.ro.error.ROError;
import cn.addenda.ro.error.reporter.AbstractROErrorReporterDelegate;
import cn.addenda.ro.grammar.function.FunctionException;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 16:00
 */
public class CalculatorROErrorReporterDelegate extends AbstractROErrorReporterDelegate {

    public static final int STATEMENT_logic_OPERATOR = 60001;
    public static final String STATEMENT_logic_OPERATOR_MSG = "error occurred when operate logic expression.";
    public static final int STATEMENT_comparison_OPERATOR = 60002;
    public static final String STATEMENT_comparison_OPERATOR_MSG = "error occurred when operate comparison expression.";
    public static final int STATEMENT_binaryArithmetic_OPERATOR = 60003;
    public static final String STATEMENT_binaryArithmetic_OPERATOR_MSG = "error occurred when operate binaryArithmetic expression.";
    public static final int STATEMENT_unaryArithmetic_OPERATOR = 60004;
    public static final String STATEMENT_unaryArithmetic_OPERATOR_MSG = "error occurred when operate unaryArithmetic expression.";

    @Override
    protected void fillErrorMsg() {
        addErrorMsg(STATEMENT_logic_OPERATOR, STATEMENT_logic_OPERATOR_MSG);
        addErrorMsg(STATEMENT_comparison_OPERATOR, STATEMENT_comparison_OPERATOR_MSG);
        addErrorMsg(STATEMENT_binaryArithmetic_OPERATOR, STATEMENT_binaryArithmetic_OPERATOR_MSG);
        addErrorMsg(STATEMENT_unaryArithmetic_OPERATOR, STATEMENT_unaryArithmetic_OPERATOR_MSG);

        addSuffixFunction(CalculatorROErrorWrapper.class, (error) -> {
            CalculatorROErrorWrapper calculatorROErrorWrapper = (CalculatorROErrorWrapper) error;
            if (calculatorROErrorWrapper.getCheckOperand()) {
                return "The operand is : " + calculatorROErrorWrapper.getObject().toString() + ".";
            } else {
                return calculatorROErrorWrapper.getObject().toString();
            }
        });
    }

    @Override
    public void error(int errorCode) {
        throw new CalculatorException(errorCode, getErrorMsg(errorCode));
    }

    @Override
    public void error(int errorCode, ROError roError) {
        throw new FunctionException(errorCode, getErrorMsg(errorCode) + SEPARATOR + getSuffix(roError));
    }
}
