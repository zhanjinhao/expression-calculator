package cn.addenda.ec.calculator;

import cn.addenda.ec.function.calculator.DefaultFunctionCalculator;
import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ro.grammar.ast.CurdUtils;
import cn.addenda.ro.grammar.ast.expression.Curd;
import cn.addenda.ro.grammar.lexical.scan.TokenSequence;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 19:37
 */
public class CalculatorFactory {

    private CalculatorFactory() {
    }

    public static Calculator createExpressionCalculator(String sql) {
        return createExpressionCalculator(sql, DefaultFunctionCalculator.getInstance());
    }

    public static Calculator createExpressionCalculator(String sql, FunctionCalculator functionCalculator) {
        Curd curd = CurdUtils.parseExpression(sql, functionCalculator, false);
        return new ExpressionCalculator(null, curd, functionCalculator);
    }

    public static Calculator createExpressionCalculator(TokenSequence tokenSequence) {
        return createExpressionCalculator(tokenSequence, DefaultFunctionCalculator.getInstance());
    }

    public static Calculator createExpressionCalculator(TokenSequence tokenSequence, FunctionCalculator functionCalculator) {
        Curd curd = CurdUtils.parseExpression(tokenSequence, functionCalculator, false);
        return new ExpressionCalculator(null, curd, functionCalculator);
    }

    public static Calculator createExpressionCalculator(Curd curd) {
        return createExpressionCalculator(curd, DefaultFunctionCalculator.getInstance());
    }

    public static Calculator createExpressionCalculator(Curd curd, FunctionCalculator functionCalculator) {
        return new ExpressionCalculator(null, curd, functionCalculator);
    }

}
