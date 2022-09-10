package cn.addenda.ec.function.calculator;

import cn.addenda.ec.function.handler.date.*;
import cn.addenda.ec.function.handler.logic.DecodeHandler;
import cn.addenda.ec.function.handler.logic.IfHandler;
import cn.addenda.ec.function.handler.string.ConcatHandler;
import cn.addenda.ec.function.handler.string.ReplaceHandler;
import cn.addenda.ec.function.handler.string.SubstringHandler;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 15:25
 */
public class DefaultFunctionCalculator extends AbstractFunctionCalculator {

    private static final FunctionCalculator instance = new DefaultFunctionCalculator();

    public static FunctionCalculator getInstance() {
        return instance;
    }

    private DefaultFunctionCalculator() {

        addFunction(DateAddHandler.class);
        addFunction(DateFormatHandler.class);
        addFunction(DateHandler.class);
        addFunction(DateSubHandler.class);
        addFunction(ExtractHandler.class);
        addFunction(FromUnixtimeHandler.class);
        addFunction(NowHandler.class);
        addFunction(StrToDateHandler.class);
        addFunction(TimestampDiffHandler.class);
        addFunction(UnixTimestampHandler.class);

        addFunction(DecodeHandler.class);
        addFunction(IfHandler.class);

        addFunction(ConcatHandler.class);
        addFunction(ReplaceHandler.class);
        addFunction(SubstringHandler.class);

    }

    @Override
    public String name() {
        return "DefaultFunctionCalculator";
    }
}
