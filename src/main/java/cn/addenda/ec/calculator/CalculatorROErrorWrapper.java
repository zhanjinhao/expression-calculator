package cn.addenda.ec.calculator;

import cn.addenda.ro.error.ROError;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 17:26
 */
public class CalculatorROErrorWrapper implements ROError {

    private boolean checkOperand = false;

    private final Object object;

    public CalculatorROErrorWrapper(Object object, boolean checkOperand) {
        this.object = object;
        this.checkOperand = checkOperand;
    }

    public Object getObject() {
        return object;
    }

    public boolean getCheckOperand() {
        return checkOperand;
    }

}
