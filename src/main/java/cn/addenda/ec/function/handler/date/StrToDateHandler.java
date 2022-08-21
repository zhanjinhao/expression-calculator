package cn.addenda.ec.function.handler.date;

import cn.addenda.ec.function.evaluator.FunctionCalculator;
import cn.addenda.ec.function.handler.AbstractFunctionHandler;
import cn.addenda.ec.function.handler.FunctionHandlerROErrorReporterDelegate;
import cn.addenda.ec.function.handler.date.format.DateFormatParserFactory;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.function.descriptor.date.StrToDateDescriptor;
import cn.addenda.ro.grammar.lexical.token.Token;
import cn.addenda.ro.grammar.lexical.token.TokenType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * 字符串转日期
 *
 * @Author ISJINHAO
 * @Date 2021/4/11 15:49
 */
public class StrToDateHandler extends AbstractFunctionHandler {

    public StrToDateHandler(FunctionCalculator functionCalculator) {
        super(functionCalculator, new StrToDateDescriptor(functionCalculator));
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

        String dateStr = (String) parameters[0];
        String pattern = (String) parameters[1];

        List<Token> tokens = DateFormatParserFactory.patternParse(pattern);

        if (tokens == null) {
            return null;
        }

        InnerStringBuilder dateStrJava = new InnerStringBuilder();
        InnerStringBuilder patternJava = new InnerStringBuilder();

        Flag flag = Flag.initFlag();

        int index = 0;

        int dateStrLen = dateStr.length();

        try {
            for (Token token : tokens) {
                if (DateConst.YEAR_FORMAT.equals(token)) {
                    if (!flag.isEmpty(0)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 4);
                    index = index + 4;
                    patternJava.append("yyyy");
                    flag.setFg(0);
                } else if (DateConst.MONTH_FORMAT.equals(token)) {
                    if (!flag.isEmpty(1)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 2);
                    index = index + 2;
                    patternJava.append("MM");
                    flag.setFg(1);
                } else if (DateConst.DAY_FORMAT.equals(token)) {
                    if (!flag.isEmpty(2)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 2);
                    index = index + 2;
                    patternJava.append("dd");
                    flag.setFg(2);
                } else if (DateConst.HOUR_FORMAT.equals(token)) {
                    if (!flag.isEmpty(3)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 2);
                    index = index + 2;
                    patternJava.append("HH");
                    flag.setFg(3);
                } else if (DateConst.MINUTE_FORMAT.equals(token)) {
                    if (!flag.isEmpty(4)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 2);
                    index = index + 2;
                    patternJava.append("mm");
                    flag.setFg(4);
                } else if (DateConst.SECOND_FORMAT.equals(token)) {
                    if (!flag.isEmpty(5)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    dateStrJava.append(dateStr, index, index + 2);
                    index = index + 2;
                    patternJava.append("ss");
                    flag.setFg(5);
                } else if (DateConst.MICROSECOND_FORMAT.equals(token)) {
                    if (!flag.isEmpty(6)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_repeatedPattern_CALCULATION);
                        return null;
                    }
                    int i = 0;
                    StringBuilder sb = new StringBuilder();
                    char c;
                    while (i < 6 && (i + index) < dateStrLen && Character.isDigit(c = dateStr.charAt(i + index))) {
                        dateStrJava.append(c);
                        i++;
                    }
                    index = index + i;
                    while (i < 9) {
                        sb.append('0');
                        i++;
                    }
                    dateStrJava.append(sb.toString());
                    patternJava.append("n");
                    flag.setFg(6);
                } else if (TokenType.IDENTIFIER.equals(token.getType())) {
                    Object patternLiteral = token.getLiteral();
                    if (!(patternLiteral instanceof String)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateParameter_CALCULATION);
                        return null;
                    }
                    String patternStr = (String) patternLiteral;
                    String substring = dateStr.substring(index, patternStr.length() + index);
                    if (!patternStr.equals(substring)) {
                        error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateParameter_CALCULATION);
                        return null;
                    }
                    index = index + patternStr.length();
                }
            }
            if (index != dateStrLen) {
                error(FunctionHandlerROErrorReporterDelegate.FUNCTION_dateParameter_CALCULATION);
                return null;
            }

            boolean time = flag.isTime();
            boolean date = flag.isDate();
            if (time && date) {
                return LocalDateTime.parse(dateStrJava.toString(), DateTimeFormatter.ofPattern(patternJava.toString()));
            } else if (time) {
                return LocalTime.parse(dateStrJava.toString(), DateTimeFormatter.ofPattern(patternJava.toString()));
            } else if (date) {
                return LocalDate.parse(dateStrJava.toString(), DateTimeFormatter.ofPattern(patternJava.toString()));
            }
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            error(FunctionHandlerROErrorReporterDelegate.FUNCTION_unknown_CALCULATION);
            return null;
        }
        return null;
    }

    private static class Flag {

        private int fg;

        private Flag(int slotNum) {
            this.fg = 1 << slotNum;
        }

        public static Flag initFlag() {
            return new Flag(7);
        }

        public boolean isEmpty(int index) {
            return (fg & 1 << index) == 0;
        }

        public void setFg(int index) {
            fg = fg | 1 << index;
        }

        public boolean isDate() {
            return (fg & ((1 << 3) - 1)) != 0;
        }

        public boolean isTime() {
            return (fg & ((1 << 4) - 1) << 3) != 0;
        }

    }


    private static class InnerStringBuilder {

        private StringBuilder stringBuilder = new StringBuilder();

        public InnerStringBuilder append(String str) {
            stringBuilder.append(str).append("-");
            return this;
        }

        public InnerStringBuilder append(char c) {
            stringBuilder.append(c);
            return this;
        }

        public InnerStringBuilder append(CharSequence s, int start, int end) {
            stringBuilder.append(s, start, end).append("-");
            return this;
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }

        public char charAt(int index) {
            return stringBuilder.charAt(index);
        }

    }

}
