package cn.addenda.ec.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @Author ISJINHAO
 * @Date 2021/4/12 16:19
 */
public class MathCalculatorDelegate {

    private MathCalculatorDelegate() {
    }

    /**
     * -
     */
    public static Object negate(Object object) {
        assertNotNull(object);
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).negate();
        } else if (object instanceof BigInteger) {
            return ((BigInteger) object).negate();
        } else if (checkInteger(object)) {
            return -Long.parseLong(object.toString());
        } else if (checkDecimal(object)) {
            return -Double.parseDouble(object.toString());
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Byte、Short、Integer、Long 八种数据类型进行取负数运算");
    }

    /**
     * !
     */
    public static boolean nand(Object object) {
        assertNotNull(object);
        if (object instanceof Boolean) {
            return !(Boolean) object;
        }
        throw new CalculatorException("只能对 Boolean 类型的数据进行与非运算");
    }

    /**
     * +
     */
    public static Object plus(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).add((BigDecimal) right);
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) + Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).add((BigInteger) right);
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) + Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left + (String) right;
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、String 九种数据类型进行加法运算");
    }

    /**
     * -
     */
    public static Object minus(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).subtract((BigDecimal) right);
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) - Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).subtract((BigInteger) right);
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) - Long.parseLong(right.toString());
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long 八种数据类型进行减法运算");
    }

    /**
     * *
     */
    public static Object multiply(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).multiply((BigDecimal) right);
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return new BigDecimal(left.toString()).multiply(new BigDecimal(right.toString()));
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).multiply((BigInteger) right);
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) * Long.parseLong(right.toString());
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long 八种数据类型进行乘法运算");
    }

    /**
     * /
     */
    public static Object divide(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            BigDecimal rightBigDecimal = (BigDecimal) right;
            if (rightBigDecimal.compareTo(BIG_DECIMAL_ZERO) == 0) {
                throw new CalculatorException("除法的右操作数不能为0");
            }
            return ((BigDecimal) left).divide(rightBigDecimal);
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            double rightDouble = Double.parseDouble(right.toString());
            if (rightDouble == 0) {
                throw new CalculatorException("除法的右操作数不能为0");
            }
            return Double.parseDouble(left.toString()) / rightDouble;
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            BigInteger rightBigInteger = (BigInteger) right;
            if (rightBigInteger.compareTo(BIG_INTEGER_ZERO) == 0) {
                throw new CalculatorException("除法的右操作数不能为0");
            }
            return ((BigInteger) left).divide((BigInteger) right);
        } else if (checkInteger(left) && checkInteger(right)) {
            long rightLong = Long.parseLong(right.toString());
            if (rightLong == 0) {
                throw new CalculatorException("除法的右操作数不能为0");
            }
            return Long.parseLong(left.toString()) / rightLong;
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long 八种数据类型进行除法运算");
    }

    private static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.valueOf(0.0d);
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.valueOf(0L);

    /**
     * =
     */
    public static boolean equal(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) == 0;
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) == Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).compareTo((BigInteger) right) == 0;
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) == Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left.toString().equals(right.toString());
        } else if (left instanceof Enum && right instanceof Enum) {
            return ((Enum<?>) left).ordinal() == ((Enum<?>) right).ordinal();
        } else if (left instanceof Character && right instanceof Character) {
            return ((Character) left).compareTo((Character) right) == 0;
        } else if (left instanceof LocalDate && right instanceof LocalDate) {
            return left.equals(right);
        } else if (checkDateTime(left) && checkDateTime(right)) {
            return extractTimeStamp(left) == extractTimeStamp(right);
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、Enum、Character、Date、LocalDate、LocalDateTime 十三种数据类型进行比较运算");
    }

    public static boolean greater(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) > 0;
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) > Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).compareTo((BigInteger) right) > 0;
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) > Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left.toString().compareTo(right.toString()) > 0;
        } else if (left instanceof Enum && right instanceof Enum) {
            return ((Enum<?>) left).ordinal() > ((Enum<?>) right).ordinal();
        } else if (left instanceof Character && right instanceof Character) {
            return ((Character) left).compareTo((Character) right) > 0;
        } else if (left instanceof LocalDate && right instanceof LocalDate) {
            return ((LocalDate) left).compareTo((LocalDate) right) > 0;
        } else if (checkDateTime(left) && checkDateTime(right)) {
            return extractTimeStamp(left) > extractTimeStamp(right);
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、Enum、Character、Date、LocalDate、LocalDateTime 十三种数据类型进行比较运算");
    }

    public static boolean greaterEqual(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) >= 0;
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) >= Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).compareTo((BigInteger) right) >= 0;
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) >= Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left.toString().compareTo(right.toString()) >= 0;
        } else if (left instanceof Enum && right instanceof Enum) {
            return ((Enum<?>) left).ordinal() >= ((Enum<?>) right).ordinal();
        } else if (left instanceof Character && right instanceof Character) {
            return ((Character) left).compareTo((Character) right) >= 0;
        } else if (left instanceof LocalDate && right instanceof LocalDate) {
            return ((LocalDate) left).compareTo((LocalDate) right) >= 0;
        } else if (checkDateTime(left) && checkDateTime(right)) {
            return extractTimeStamp(left) >= extractTimeStamp(right);
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、Enum、Character、Date、LocalDate、LocalDateTime 十三种数据类型进行比较运算");
    }

    public static boolean less(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) < 0;
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) < Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).compareTo((BigInteger) right) < 0;
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) < Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left.toString().compareTo(right.toString()) < 0;
        } else if (left instanceof Enum && right instanceof Enum) {
            return ((Enum<?>) left).ordinal() < ((Enum<?>) right).ordinal();
        } else if (left instanceof Character && right instanceof Character) {
            return ((Character) left).compareTo((Character) right) < 0;
        } else if (left instanceof LocalDate && right instanceof LocalDate) {
            return ((LocalDate) left).compareTo((LocalDate) right) < 0;
        } else if (checkDateTime(left) && checkDateTime(right)) {
            return extractTimeStamp(left) < extractTimeStamp(right);
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、Enum、Character、Date、LocalDate、LocalDateTime 十三种数据类型进行比较运算");
    }

    public static boolean lessEqual(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) <= 0;
        } else if ((checkDecimal(left) && checkDecimal(right))
                || (checkInteger(left) && checkDecimal(right)) || (checkDecimal(left) && checkInteger(right))) {
            return Double.parseDouble(left.toString()) <= Double.parseDouble(right.toString());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).compareTo((BigInteger) right) <= 0;
        } else if (checkInteger(left) && checkInteger(right)) {
            return Long.parseLong(left.toString()) <= Long.parseLong(right.toString());
        } else if (left instanceof String && right instanceof String) {
            return left.toString().compareTo(right.toString()) <= 0;
        } else if (left instanceof Enum && right instanceof Enum) {
            return ((Enum<?>) left).ordinal() <= ((Enum<?>) right).ordinal();
        } else if (left instanceof Character && right instanceof Character) {
            return ((Character) left).compareTo((Character) right) <= 0;
        } else if (left instanceof LocalDate && right instanceof LocalDate) {
            return ((LocalDate) left).compareTo((LocalDate) right) <= 0;
        } else if (checkDateTime(left) && checkDateTime(right)) {
            return extractTimeStamp(left) <= extractTimeStamp(right);
        }
        throw new CalculatorException("只能对 BigDecimal、BigInteger、Double、Float、Short、Integer、Byte、Long、Enum、Character、Date、LocalDate、LocalDateTime 十三种数据类型进行比较运算");
    }

    private static long extractTimeStamp(Object object) {
        if (object instanceof Date) {
            return ((Date) object).getTime();
        } else if (object instanceof LocalDateTime) {
            return ((LocalDateTime) object).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }
        throw new CalculatorException("只能对 Date 或 LocalDateTime 类型提取时间戳!");
    }

    private static boolean checkInteger(Object object) {
        return object instanceof Integer || object instanceof Long
                || object instanceof Short || object instanceof Byte || object instanceof BigInteger;
    }

    private static boolean checkDecimal(Object object) {
        return object instanceof Float || object instanceof Double || object instanceof BigDecimal;
    }

    private static boolean checkDateTime(Object object) {
        return object instanceof Date || object instanceof LocalDateTime;
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new CalculatorException("不能对 null 进行数学运算");
        }
    }

    public static Object like(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if (!(left instanceof String) || !(right instanceof String)) {
            throw new CalculatorException("只能对 String 数据类型进行like运算");
        }
        String leftStr = (String) left;
        String rightStr = (String) right;
        return leftStr.matches(sqlPatternToRegex(rightStr));
    }

    /**
     * copy from Apache Cayenne, RegexUtil#sqlPatternToRegex(String)
     */
    private static String sqlPatternToRegex(String pattern) {
        if (pattern == null) {
            throw new NullPointerException("Null pattern.");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("Empty pattern.");
        }
        StringBuilder buffer = new StringBuilder();

        // convert * into regex syntax
        // e.g. abc*x becomes ^abc.*x$
        // or abc?x becomes ^abc.?x$
        buffer.append("^");
        for (int j = 0; j < pattern.length(); j++) {
            char nextChar = pattern.charAt(j);
            if (nextChar == '%') {
                nextChar = '*';
            }
            if (nextChar == '*' || nextChar == '?') {
                buffer.append('.');
            }
            // escape special chars
            else if (nextChar == '.'
                    || nextChar == '/'
                    || nextChar == '$'
                    || nextChar == '^') {
                buffer.append('\\');
            }
            buffer.append(nextChar);
        }

        buffer.append("$");
        return buffer.toString();
    }

    public static Object contains(Object left, Object right) {
        assertNotNull(left);
        assertNotNull(right);
        if ((left instanceof String) && (right instanceof String)) {
            return ((String) left).contains((String) right);
        }
        throw new CalculatorException("只能对 String 数据类型进行contains运算");
    }

}
