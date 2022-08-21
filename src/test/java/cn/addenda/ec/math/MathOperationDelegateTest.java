package cn.addenda.ec.math;

import cn.addenda.ec.calculator.MathCalculatorDelegate;
import org.junit.Test;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 19:43
 */
public class MathOperationDelegateTest {

    @Test
    public void test() {

        System.out.println(MathCalculatorDelegate.negate(1234567890111122222L));
        System.out.println(MathCalculatorDelegate.nand(true));

        System.out.println(MathCalculatorDelegate.plus(1234567890111122222L, 1234567890111122222L));
        System.out.println(MathCalculatorDelegate.minus(3, 2));
        System.out.println(MathCalculatorDelegate.multiply(1234567890111122222L, 1234567890111122222L));
        System.out.println(MathCalculatorDelegate.divide(3d, 2));
        System.out.println(MathCalculatorDelegate.contains("12345678", "234"));
        System.out.println(MathCalculatorDelegate.like("12345678", "?234%"));
        System.out.println(MathCalculatorDelegate.like("12345678", "??234%"));

    }

}
