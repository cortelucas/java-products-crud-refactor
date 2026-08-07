package cortelucas.domain.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TextValidatorTest {

    @Test
    @DisplayName("Não deve lançar exceção para texto válido")
    void shouldNotThrowForValidText() {
        assertDoesNotThrow(() -> TextValidator.validate("Notebook Gamer", "nome", 3, 255));
    }

    @Test
    @DisplayName("Deve lançar exceção para texto nulo")
    void shouldThrowForNullText() {
        assertThrows(IllegalArgumentException.class,
                () -> TextValidator.validate(null, "nome", 3, 255));
    }

    @Test
    @DisplayName("Deve lançar exceção para texto vazio")
    void shouldThrowForEmptyText() {
        assertThrows(IllegalArgumentException.class,
                () -> TextValidator.validate("   ", "nome", 3, 255));
    }

    @Test
    @DisplayName("Deve lançar exceção para texto menor que o mínimo")
    void shouldThrowForTextBelowMinLength() {
        assertThrows(IllegalArgumentException.class,
                () -> TextValidator.validate("AB", "nome", 3, 255));
    }

    @Test
    @DisplayName("Deve lançar exceção para texto maior que o máximo")
    void shouldThrowForTextAboveMaxLength() {
        String textoGrande = "A".repeat(256);
        assertThrows(IllegalArgumentException.class,
                () -> TextValidator.validate(textoGrande, "nome", 3, 255));
    }

    @Test
    @DisplayName("Deve permitir texto no limite exato do máximo")
    void shouldAllowTextAtExactMaxLength() {
        String textoLimite = "A".repeat(255);
        assertDoesNotThrow(() -> TextValidator.validate(textoLimite, "nome", 3, 255));
    }
}
