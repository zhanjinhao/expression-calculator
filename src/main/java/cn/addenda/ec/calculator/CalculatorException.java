package cn.addenda.ec.calculator;

import cn.addenda.ro.ROException;

/**
 * @Author ISJINHAO
 * @Date 2021/4/11 16:57
 */
public class CalculatorException extends ROException {

    public CalculatorException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public CalculatorException(String message) {
        super(0, message);
    }

}
