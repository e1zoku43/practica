import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyCalculator {
    private static final double EXCHANGE_RATE = 97.31; 

    public static void main(String[] args) {
        String input = "toDollars(737р + toRubles($85.4))";
        double result = calculate(input);
        System.out.println("$" + round(result));
    }

    public static double calculate(String input) {
        String expression = input.replaceAll("\\s", "");
        return evaluateExpression(expression);
    }

    private static double evaluateExpression(String expression) {
        if (expression.startsWith("toDollars(")) {
            return toDollars(evaluateExpression(expression.substring(10, expression.length() - 1)));
        } else if (expression.startsWith("toRubles(")) {
            return toRubles(evaluateExpression(expression.substring(9, expression.length() - 1)));
        } else if (expression.contains("+")) {
            int plusIndex = expression.indexOf("+");
            double leftValue = evaluateExpression(expression.substring(0, plusIndex));
            double rightValue = evaluateExpression(expression.substring(plusIndex + 1));
            return leftValue + rightValue;
        } else if (expression.contains("-")) {
            int minusIndex = expression.indexOf("-");
            double leftValue = evaluateExpression(expression.substring(0, minusIndex));
            double rightValue = evaluateExpression(expression.substring(minusIndex + 1));
            return leftValue - rightValue;
        } else if (expression.startsWith("$")) {
            return Double.parseDouble(expression.substring(1));
        } else if (expression.endsWith("р")) {
            return Double.parseDouble(expression.substring(0, expression.length() - 1));
        } else {
            return Double.parseDouble(expression);
        }
    }

    private static double toDollars(double rubles) {
        return round(rubles / EXCHANGE_RATE);
    }

    private static double toRubles(double dollars) {
        return round(dollars * EXCHANGE_RATE);
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
