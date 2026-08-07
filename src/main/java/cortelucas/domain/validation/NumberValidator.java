package cortelucas.domain.validation;

public class NumberValidator {

    private NumberValidator() {
    }

    public static void validateNonNegative(double value, String fieldLabel) {
        if (value < 0) {
            throw new IllegalArgumentException("O " + fieldLabel + " do produto não pode ser negativo.");
        }
    }

    public static void validateNonNegative(int value, String fieldLabel) {
        if (value < 0) {
            throw new IllegalArgumentException("A " + fieldLabel + " do produto não pode ser negativa.");
        }
    }
}
