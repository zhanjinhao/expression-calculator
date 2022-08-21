package cn.addenda.ec.calculator;

import cn.addenda.ec.function.evaluator.DefaultFunctionCalculator;
import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ro.grammar.ast.CurdParserFactory;
import cn.addenda.ro.grammar.ast.expression.ExpressionParser;
import cn.addenda.ro.grammar.lexical.scan.TokenSequence;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 19:37
 */
public class CalculatorFactory {

    private CalculatorFactory() {
    }

    public static Calculator createExpressionCalculator(String sql, FunctionCalculator functionCalculator) {
        ExpressionParser expressionParser = CurdParserFactory.createExpressionParser(sql, functionCalculator);
        return new ExpressionCalculator(null, expressionParser);
    }

    public static Calculator createExpressionCalculator(String sql) {
        return createExpressionCalculator(sql, DefaultFunctionCalculator.getInstance());
    }

    public static Calculator createExpressionCalculator(TokenSequence tokenSequence, FunctionCalculator functionCalculator) {
        ExpressionParser expressionParser = CurdParserFactory.createExpressionParser(tokenSequence, functionCalculator);
        return new ExpressionCalculator(null, expressionParser);
    }

    public static Calculator createExpressionCalculator(TokenSequence tokenSequence) {
        return createExpressionCalculator(tokenSequence, DefaultFunctionCalculator.getInstance());
    }

}
