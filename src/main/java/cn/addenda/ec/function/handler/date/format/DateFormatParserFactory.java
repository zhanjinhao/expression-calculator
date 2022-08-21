package cn.addenda.ec.function.handler.date.format;

import cn.addenda.ro.grammar.lexical.token.Token;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ISJINHAO
 * @Date 2021/8/5 22:22
 */
public class DateFormatParserFactory {

    private DateFormatParserFactory() {

    }

    private static final Map<String, List<Token>> cache = new ConcurrentHashMap<>();

    public static List<Token> patternParse(String formatStr) {
        return cache.computeIfAbsent(formatStr, item -> {
            DateFormatParser dateFormatParser = new DateFormatParser(formatStr);
            return dateFormatParser.parse();
        });
    }

}
