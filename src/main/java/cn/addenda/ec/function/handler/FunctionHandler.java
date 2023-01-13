package cn.addenda.ec.function.handler;

import cn.addenda.ro.grammar.ast.expression.CurdType;
import cn.addenda.ro.grammar.ast.expression.Function;
import cn.addenda.ro.grammar.function.descriptor.FunctionDescriptor;

/**
 * @Author ISJINHAO
 * @Date 2021/7/24 22:58
 */
public interface FunctionHandler extends FunctionDescriptor {

    Object evaluate(Function function, CurdType type, Object... parameters);

}
