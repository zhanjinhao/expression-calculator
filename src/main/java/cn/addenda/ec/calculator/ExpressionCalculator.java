package cn.addenda.ec.calculator;

import cn.addenda.ec.function.calculator.FunctionCalculator;
import cn.addenda.ro.error.reporter.ROErrorReporter;
import cn.addenda.ro.grammar.ast.AbstractCurdParser;
import cn.addenda.ro.grammar.ast.CurdVisitor;
import cn.addenda.ro.grammar.ast.create.Insert;
import cn.addenda.ro.grammar.ast.delete.Delete;
import cn.addenda.ro.grammar.ast.expression.*;
import cn.addenda.ro.grammar.ast.expression.visitor.ExpressionVisitorForDelegation;
import cn.addenda.ro.grammar.ast.retrieve.Select;
import cn.addenda.ro.grammar.ast.update.Update;
import cn.addenda.ro.grammar.lexical.token.Token;

import java.util.List;

/**
 * 能处理的数据类型包括：
 * char、Character、short、Short、byte、Byte、int、Integer、long、Long、double、Double、float、Float
 * boolean、Boolean、String、Date、LocalDate、LocalDateTime、BigDecimal、BigInteger、Enum。
 *
 * @Author ISJINHAO
 * @Date 2021/4/11 17:04
 */
public class ExpressionCalculator extends ExpressionVisitorForDelegation<Object> implements Calculator {

    private final FunctionCalculator functionCalculator;

    private CalculatorRunTimeContext calculatorRunTimeContext;

    private final CurdType type;

    private final Curd curd;

    private final ROErrorReporter roErrorReporter = new CalculatorROErrorReporterDelegate();

    public ExpressionCalculator(CurdVisitor<Object> client, AbstractCurdParser abstractCurdParser) {
        super(client);
        curd = abstractCurdParser.parse();
        if (curd instanceof Insert) {
            type = CurdType.INSERT;
        } else if (curd instanceof Delete) {
            type = CurdType.DELETE;
        } else if (curd instanceof Select) {
            type = CurdType.SELECT;
        } else if (curd instanceof Update) {
            type = CurdType.UPDATE;
        } else {
            type = CurdType.EXPRESSION;
        }
        functionCalculator = (FunctionCalculator) abstractCurdParser.getFunctionEvaluator();
        setErrorReporter(roErrorReporter);
    }

    public ROErrorReporter getRoErrorReporter() {
        return roErrorReporter;
    }

    @Override
    public Object calculate(CalculatorRunTimeContext calculatorRunTimeContext) {
        this.calculatorRunTimeContext = calculatorRunTimeContext;
        return curd.accept(this);
    }

    @Override
    public Object visitInCondition(InCondition inCondition) {
        Curd select = inCondition.getSelect();
        if (select != null) {
            throw new CalculatorException("inCondition 语法只有 in (1, 2, 3) 语法可以计算。");
        }
        Token inColumn = inCondition.getIdentifier();
        String inColumnName = String.valueOf(inColumn.getLiteral());
        Object inValue = calculatorRunTimeContext.get(inColumnName);
        if (inValue == null) {
            throw new CalculatorException("计算器上下文里没有 " + inColumnName + " 的值！");
        }
        List<Curd> range = inCondition.getRange();
        for (Curd item : range) {
            if (MathCalculatorDelegate.equal(inValue, item.accept(this))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object visitWhereSeg(WhereSeg whereSeg) {
        return whereSeg.getLogic().accept(this);
    }

    @Override
    public Object visitLogic(Logic logic) {
        Object left = logic.getLeftCurd().accept(this);
        Curd rightSelectStatement = logic.getRightCurd();
        if (rightSelectStatement != null) {
            Object right = rightSelectStatement.accept(this);
            Token token = logic.getToken();
            assertBoolean(left, CalculatorROErrorReporterDelegate.STATEMENT_logic_OPERATOR);
            assertBoolean(right, CalculatorROErrorReporterDelegate.STATEMENT_logic_OPERATOR);
            switch (token.getType()) {
                case AND:
                    return (Boolean) left && (Boolean) right;
                case OR:
                    return (Boolean) left || (Boolean) right;
                default:
                    error(CalculatorROErrorReporterDelegate.STATEMENT_logic_OPERATOR, "操作符类型不对，需要 'and' 或 'or'，当前是：" + token.getLiteral(), false);
            }
        }
        assertBoolean(left, CalculatorROErrorReporterDelegate.STATEMENT_logic_OPERATOR);
        return left;
    }

    @Override
    public Object visitComparison(Comparison comparison) {
        Object left = comparison.getLeftCurd().accept(this);
        Curd rightSelectStatement = comparison.getRightCurd();
        if (rightSelectStatement != null) {
            Object right = rightSelectStatement.accept(this);
            Curd comparisonSymbol = comparison.getComparisonSymbol();
            if (comparisonSymbol instanceof Identifier) {
                Token operator = ((Identifier) comparisonSymbol).getName();
                switch (operator.getType()) {
                    case BANG_EQUAL:
                        return !MathCalculatorDelegate.equal(left, right);
                    case EQUAL:
                        return MathCalculatorDelegate.equal(left, right);
                    case GREATER:
                        return MathCalculatorDelegate.greater(left, right);
                    case GREATER_EQUAL:
                        return MathCalculatorDelegate.greaterEqual(left, right);
                    case LESS:
                        return MathCalculatorDelegate.less(left, right);
                    case LESS_EQUAL:
                        return MathCalculatorDelegate.lessEqual(left, right);
                    case LIKE:
                        return MathCalculatorDelegate.like(left, right);
                    case CONTAINS:
                        return MathCalculatorDelegate.contains(left, right);
                    default:
                        error(CalculatorROErrorReporterDelegate.STATEMENT_comparison_OPERATOR, "操作符类型不对，需要 '>' 或 '<' 或 '>=' 或 '<=' 或 '=' 或 '!=' 或 'like' 或 'contains' 或 'is not?'，当前是：" + operator.getLiteral(), false);
                }
            } else if (comparisonSymbol instanceof IsNot) {
                IsNot isNot = (IsNot) comparisonSymbol;
                boolean nullFlag = left == null;
                Token notToken = isNot.getNotToken();
                return (notToken == null) == nullFlag;
            }
        }
        assertBoolean(left, CalculatorROErrorReporterDelegate.STATEMENT_comparison_OPERATOR);
        return left;
    }

    private void assertBoolean(Object left, int error) {
        if (!(left instanceof Boolean)) {
            error(error, left, true);
        }
    }

    @Override
    public Object visitBinaryArithmetic(BinaryArithmetic binaryArithmetic) {
        Object left = binaryArithmetic.getLeftCurd().accept(this);
        Curd rightCurd = binaryArithmetic.getRightCurd();
        if (rightCurd != null) {
            Object right = rightCurd.accept(this);
            Token operator = binaryArithmetic.getToken();
            switch (operator.getType()) {
                case PLUS:
                    return MathCalculatorDelegate.plus(left, right);
                case MINUS:
                    return MathCalculatorDelegate.minus(left, right);
                case SLASH:
                    return MathCalculatorDelegate.divide(left, right);
                case STAR:
                    return MathCalculatorDelegate.multiply(left, right);
                default:
                    error(CalculatorROErrorReporterDelegate.STATEMENT_binaryArithmetic_OPERATOR, "操作符类型不对，需要 '+' 或 '-' 或 '*' 或 '/'，当前是：" + operator.getLiteral(), false);
            }
        }
        return left;
    }

    @Override
    public Object visitUnaryArithmetic(UnaryArithmetic unaryArithmetic) {
        Object accept = unaryArithmetic.getCurd().accept(this);
        Token operator = unaryArithmetic.getOperator();
        switch (operator.getType()) {
            case BANG:
                return MathCalculatorDelegate.nand(accept);
            case MINUS:
                return MathCalculatorDelegate.negate(accept);
            default:
                error(CalculatorROErrorReporterDelegate.STATEMENT_unaryArithmetic_OPERATOR, "操作符类型不对，需要 '-' 或 '!'，当前是：" + operator.getLiteral(), false);
        }
        return null;
    }

    @Override
    public Object visitLiteral(Literal literal) {
        Token token = literal.getValue();
        Object object = token.getLiteral();
        if (object instanceof String) {
            // 单独处理 true 和 false
            String string = (String) object;
            switch (string) {
                case "true":
                    return true;
                case "false":
                    return false;
                case "null":
                    return null;
                default:
                    return object;
            }
        }
        return object;
    }

    @Override
    public Object visitGrouping(Grouping grouping) {
        return grouping.getCurd().accept(this);
    }

    @Override
    public Object visitIdentifier(Identifier identifier) {
        return calculatorRunTimeContext.get((String) identifier.getName().getLiteral());
    }

    /**
     * 当存在函数的递归调用时，在调用者处完成递归。降低FunctionEvaluator的实现难度。
     */
    @Override
    public Object visitFunction(Function function) {
        List<Curd> parameterList = function.getParameterList();
        int size = parameterList == null ? 0 : parameterList.size();
        Object[] parameters = new Object[size];
        for (int i = 0; i < size; i++) {
            Curd item = parameterList.get(i);
            parameters[i] = item.accept(this);
        }
        return functionCalculator.evaluate(function, type, parameters);
    }

    @Override
    public Object visitAssignmentList(AssignmentList assignmentList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitTimeInterval(TimeInterval timeInterval) {
        return timeInterval;
    }

    @Override
    public Object visitTimeUnit(TimeUnit timeUnit) {
        Object accept = timeUnit.getCurd().accept(this);
        return new TimeUnit(timeUnit.getTimeType(), new Literal(new Token(null, accept)));
    }

    @Override
    public Object visitIsNot(IsNot isNot) {
        return isNot;
    }

    @Override
    public Object visitAttachment(Attachment attachment) {
        return attachment;
    }

    private void error(int errorCode, Object object, boolean checkOperand) {
        roErrorReporter.error(errorCode, new CalculatorROErrorWrapper(object, checkOperand));
    }

}
