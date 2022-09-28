package cn.addenda.ec.calculator;

/**
 * @Author ISJINHAO
 * @Date 2021/12/30 15:19
 */
public interface Calculator {

    Object calculate(CalculatorRunTimeContext calculatorRunTimeContext);

    default Object calculate() {
        return calculate(null);
    }

}
