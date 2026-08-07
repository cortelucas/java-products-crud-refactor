package cortelucas.domain.validation;

public class TextValidator {

    private TextValidator() {
    }

    public static void validate(String value, String fieldLabel, int minLength, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O " + fieldLabel + " do produto não pode ser vazio.");
        }

        if (value.trim().length() < minLength) {
            throw new IllegalArgumentException(
                    "O " + fieldLabel + " do produto precisa ter pelo menos " + minLength + " caracteres.");
        }

        if (value.trim().length() > maxLength) {
            throw new IllegalArgumentException(
                    "O " + fieldLabel + " do produto não pode ter mais de " + maxLength + " caracteres.");
        }
    }
}
