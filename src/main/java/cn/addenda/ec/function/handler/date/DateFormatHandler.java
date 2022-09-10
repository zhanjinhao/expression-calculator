package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ec.function.handler.date.format.DateFormatParserFactory;
import cn.addenda.ro.grammar.ast.expression.*;
import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.function.descriptor.date.DateFormatDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;
import cn.addenda.ro.grammar.lexical.token.TokenType;

import java.util.List;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 15:38
 */
public class DateFormatHandler extends AbstractFunctionHandler {

    public DateFormatHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new DateFormatDescriptor(functionCalculator));
    }

    @Override
    public boolean isIndependent() {
        return functionDescriptor.isIndependent();
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
        Object date = parameters[0];
        Object parameter1 = parameters[1];
        String pattern;
        if (parameter1 instanceof String) {
            pattern = (String) parameter1;
        } else {
            pattern = (String) ((Literal) parameter1).getValue().getLiteral();
        }

        List<Token> tokens = DateFormatParserFactory.patternParse(pattern);
        if (tokens == null || tokens.isEmpty()) {
            return "";
        }

        return format(date, tokens);
    }

    private String format(Object date, List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            int value = doGetNumericValue(date, token);
            if (value == -2) {
                sb.append(token.getLiteral());
            } else {
                sb.append(value);
            }
        }
        return sb.toString();
    }

    private int doGetNumericValue(Object date, Token token) {
        int value = -1;
        if (DateConst.YEAR_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.YEAR);
        } else if (DateConst.MONTH_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.MONTH);
        } else if (DateConst.DAY_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.DAY);
        } else if (DateConst.HOUR_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.HOUR);
        } else if (DateConst.MINUTE_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.MINUTE);
        } else if (DateConst.SECOND_FORMAT.equals(token)) {
            value = getNumericValue(date, DateConst.SECOND);
        } else if (DateConst.MICROSECOND_FORMAT.equals(token)) {
            // Calendar只能拿到毫秒，转为微秒需要 multiply 1000
            value = getNumericValue(date, DateConst.MICROSECOND);
        } else if (TokenType.IDENTIFIER.equals(token.getType())) {
            value = -2;
        }

        if (value == -1) {
            error(FunctionHandlerROErrorReporterDelegate.FUNCTION_formatPattern_CALCULATION, this);
        }
        return value;
    }

}
