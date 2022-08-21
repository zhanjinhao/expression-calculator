package cn.addenda.ec.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 16:24
 */
public class CalculatorRunTimeContext {

    /**
     * identifier name -> argument
     */
    private final Map<String, Object> argumentMap = new HashMap<>();

    public Object get(String name) {
        return argumentMap.get(name);
    }

    public boolean put(String name, Object object) {
        return argumentMap.put(name, object) == null;
    }

    public boolean remove(String name) {
        return argumentMap.remove(name) == null;
    }

}
