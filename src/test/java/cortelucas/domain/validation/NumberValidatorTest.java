package cortelucas.domain.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class NumberValidatorTest {

    @Test
    @DisplayName("Não deve lançar exceção para double não negativo")
    void shouldNotThrowForNonNegativeDouble() {
        assertDoesNotThrow(() -> NumberValidator.validateNonNegative(0.0, "preço"));
        assertDoesNotThrow(() -> NumberValidator.validateNonNegative(4500.00, "preço"));
    }

    @Test
    @DisplayName("Deve lançar exceção para double negativo")
    void shouldThrowForNegativeDouble() {
        assertThrows(IllegalArgumentException.class,
                () -> NumberValidator.validateNonNegative(-1.0, "preço"));
    }

    @Test
    @DisplayName("Não deve lançar exceção para int não negativo")
    void shouldNotThrowForNonNegativeInt() {
        assertDoesNotThrow(() -> NumberValidator.validateNonNegative(0, "quantidade"));
        assertDoesNotThrow(() -> NumberValidator.validateNonNegative(10, "quantidade"));
    }

    @Test
    @DisplayName("Deve lançar exceção para int negativo")
    void shouldThrowForNegativeInt() {
        assertThrows(IllegalArgumentException.class,
                () -> NumberValidator.validateNonNegative(-1, "quantidade"));
    }
}