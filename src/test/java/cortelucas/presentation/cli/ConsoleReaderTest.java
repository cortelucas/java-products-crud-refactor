package cortelucas.presentation.cli;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReaderTest {

    private ConsoleReader readerFor(String simulatedInput) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        return new ConsoleReader(new Scanner(inputStream));
    }

    @Test
    @DisplayName("lerTexto deve retornar o texto digitado sem espaços nas bordas")
    void lerTextoShouldReturnTrimmedText() {
        ConsoleReader reader = readerFor("  Notebook Gamer  \n");
        assertEquals("Notebook Gamer", reader.lerTexto("Nome"));
    }

    @Test
    @DisplayName("lerOpcao deve retornar -1 para entrada não numérica")
    void lerOpcaoShouldReturnMinusOneForInvalidInput() {
        ConsoleReader reader = readerFor("abc\n");
        assertEquals(-1, reader.lerOpcao());
    }

    @Test
    @DisplayName("lerOpcao deve retornar o número digitado")
    void lerOpcaoShouldReturnParsedNumber() {
        ConsoleReader reader = readerFor("3\n");
        assertEquals(3, reader.lerOpcao());
    }

    @Test
    @DisplayName("lerDouble deve pedir novamente até receber um valor válido e não negativo")
    void lerDoubleShouldRetryUntilValidNonNegativeValue() {
        ConsoleReader reader = readerFor("abc\n-10\n4500.00\n");
        assertEquals(4500.00, reader.lerDouble("Valor", "preço do produto"));
    }

    @Test
    @DisplayName("lerInt deve pedir novamente até receber um valor válido e não negativo")
    void lerIntShouldRetryUntilValidNonNegativeValue() {
        ConsoleReader reader = readerFor("xyz\n-5\n10\n");
        assertEquals(10, reader.lerInt("Quantidade", "quantidade do produto"));
    }

    @Test
    @DisplayName("lerTextoValidado deve pedir novamente até receber texto dentro do tamanho permitido")
    void lerTextoValidadoShouldRetryUntilValidLength() {
        ConsoleReader reader = readerFor("ab\nNotebook Gamer\n");
        assertEquals("Notebook Gamer", reader.lerTextoValidado("Nome", "nome do produto", 3, 255));
    }

    @Test
    @DisplayName("lerTextoOpcional deve retornar vazio quando Enter é pressionado sem texto")
    void lerTextoOpcionalShouldReturnEmptyWhenBlank() {
        ConsoleReader reader = readerFor("\n");
        assertEquals(Optional.empty(), reader.lerTextoOpcional("Novo nome"));
    }

    @Test
    @DisplayName("lerTextoOpcional deve retornar o valor quando texto é informado")
    void lerTextoOpcionalShouldReturnValueWhenProvided() {
        ConsoleReader reader = readerFor("Mouse RGB\n");
        assertEquals(Optional.of("Mouse RGB"), reader.lerTextoOpcional("Novo nome"));
    }

    @Test
    @DisplayName("lerTextoOpcionalValidado deve retornar vazio quando Enter é pressionado")
    void lerTextoOpcionalValidadoShouldReturnEmptyWhenBlank() {
        ConsoleReader reader = readerFor("\n");
        assertEquals(Optional.empty(),
                reader.lerTextoOpcionalValidado("Novo nome", "nome do produto", 3, 255));
    }

    @Test
    @DisplayName("lerTextoOpcionalValidado deve pedir novamente até receber texto válido ou vazio")
    void lerTextoOpcionalValidadoShouldRetryUntilValidOrBlank() {
        ConsoleReader reader = readerFor("x\nNotebook Gamer\n");
        assertEquals(Optional.of("Notebook Gamer"),
                reader.lerTextoOpcionalValidado("Novo nome", "nome do produto", 3, 255));
    }

    @Test
    @DisplayName("lerDoubleOpcional deve retornar vazio quando Enter é pressionado")
    void lerDoubleOpcionalShouldReturnEmptyWhenBlank() {
        ConsoleReader reader = readerFor("\n");
        assertEquals(Optional.empty(), reader.lerDoubleOpcional("Novo valor", "preço do produto"));
    }

    @Test
    @DisplayName("lerDoubleOpcional deve pedir novamente até valor válido e não negativo")
    void lerDoubleOpcionalShouldRetryUntilValidNonNegativeValue() {
        ConsoleReader reader = readerFor("abc\n-1\n5000.00\n");
        assertEquals(Optional.of(5000.00), reader.lerDoubleOpcional("Novo valor", "preço do produto"));
    }

    @Test
    @DisplayName("lerIntOpcional deve retornar vazio quando Enter é pressionado")
    void lerIntOpcionalShouldReturnEmptyWhenBlank() {
        ConsoleReader reader = readerFor("\n");
        assertEquals(Optional.empty(), reader.lerIntOpcional("Nova quantidade", "quantidade do produto"));
    }

    @Test
    @DisplayName("lerIntOpcional deve pedir novamente até valor válido e não negativo")
    void lerIntOpcionalShouldRetryUntilValidNonNegativeValue() {
        ConsoleReader reader = readerFor("xyz\n-3\n7\n");
        assertEquals(Optional.of(7), reader.lerIntOpcional("Nova quantidade", "quantidade do produto"));
    }
}