package cn.addenda.ec;

import cn.addenda.ec.calculator.Calculator;
import cn.addenda.ec.calculator.CalculatorFactory;
import cn.addenda.ec.calculator.CalculatorRunTimeContext;
import org.junit.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @Author ISJINHAO
 * @Date 2022/1/1 20:33
 */
public class ExpressionCalculatorTest {

    @Test
    public void test1() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("a > 4 and b is not null and c contains '123' and d = str_to_date('2021-12-12 00:00:00', '%Y-%m-%d %H:%i:%s')");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 0));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }

    @Test
    public void test2() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("a > 4 and b is not null and c contains '123' and d = str_to_date('2021-12-12 00:00:00', '%Y-%m-%d %H:%i:%s')");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }

    @Test
    public void test3() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("date_sub(date_add(str_to_date('2021-12-12 08:00:00', '%Y-%m-%d %H:%i:%s'), interval 10 day), interval 2 hour)");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }

    @Test
    public void test4() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("extract(hour from str_to_date('2021-12-12 08:00:00', '%Y-%m-%d %H:%i:%s'))");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }

    @Test
    public void test5() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("a in ( 1, 2, 3)");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("1"));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }


    @Test
    public void test6() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("date_sub(date_add('2021-12-12', interval 10 day), interval 2 month)");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }


    @Test
    public void test7() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("date_sub(date_add('2021-12-12 08:00:00', interval 10 day), interval 2 hour)");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }


    @Test
    public void test8() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("extract(hour from '2021-12-12 08:00:00')");
        CalculatorRunTimeContext calculatorRunTimeContext = new CalculatorRunTimeContext();
        calculatorRunTimeContext.put("a", new BigInteger("5"));
        calculatorRunTimeContext.put("b", true);
        calculatorRunTimeContext.put("c", "12345");
        calculatorRunTimeContext.put("d", LocalDateTime.of(2021, 12, 12, 0, 0, 1));
        System.out.println(statementOperator.calculate(calculatorRunTimeContext));
    }

    @Test
    public void test9() {
        Calculator statementOperator = CalculatorFactory.createExpressionCalculator("replace('123', '1', '\\'')");
        System.out.println(statementOperator.calculate());
    }

}
