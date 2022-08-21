package cn.addenda.ec.function.handler.date.format;

import cn.addenda.ro.grammar.constant.DateConst;
import cn.addenda.ro.grammar.lexical.token.Token;
import cn.addenda.ro.grammar.lexical.token.TokenType;
import cn.addenda.ro.grammar.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ISJINHAO
 * @Date 2021/8/5 22:22
 */
public class DateFormatParser implements Parser<List<Token>> {

    private final String pattern;

    public DateFormatParser(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public List<Token> parse() {
        ArrayList<Token> tokens = new ArrayList<>();
        int length = pattern.length();

        int lastPercentSign = 0;
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            char cur = pattern.charAt(i);
            if ('%' == cur) {
                tokens.add(newToken(pattern.substring(lastPercentSign, i)));
                lastPercentSign = i;
                flag = true;
                continue;
            }

            if (flag && i != length - 1) {
                tokens.add(newToken(pattern.substring(lastPercentSign, i + 1)));
                flag = false;
                lastPercentSign = i + 1;
            }

        }
        tokens.add(newToken(pattern.substring(lastPercentSign, length)));
        return tokens;
    }


    private Token newToken(String patternSeg) {
        Token token = DateConst.getToken(patternSeg);
        if (token == null) {
            return new Token(TokenType.IDENTIFIER, patternSeg);
        }
        return token;
    }

}
