package cn.addenda.ec.function.evaluator;

import cn.addenda.ec.function.handler.FunctionHandler;
import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.error.ROError;
import cn.addenda.ro.grammar.function.evaluator.FunctionEvaluator;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 14:02
 */
public interface FunctionCalculator extends ROError, FunctionEvaluator<FunctionHandler> {

    @Override
    FunctionHandler getFunction(String functionName);

    @Override
    void addFunction(Class<? extends FunctionHandler> function);

    Object evaluate(Function function, CurdType type, Object... parameters);
}
