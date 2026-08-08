package cortelucas.presentation.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.domain.entities.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductPresenterTest {

    private final ProductPresenter presenter = new ProductPresenter();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Deve imprimir os dados do produto formatados")
    void shouldPrintProductFormatted() {
        Product product = new Product("abc12345", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10);

        presenter.imprimirProduto(product);

        String output = outContent.toString();
        assertTrue(output.contains("ID: abc12345"));
        assertTrue(output.contains("Nome: Notebook Gamer"));
        assertTrue(output.contains("Segmento: Eletrônicos"));
        assertTrue(output.contains("Marca: Dell"));
        assertTrue(output.contains("Valor: R$ 4500,00") || output.contains("Valor: R$ 4500.00"));
        assertTrue(output.contains("Qtd: 10"));
    }

    @Test
    @DisplayName("Deve imprimir mensagem de vazio quando a lista não tem produtos")
    void shouldPrintEmptyMessageWhenListIsEmpty() {
        presenter.imprimirLista(List.of(), "Nenhum produto cadastrado.");

        assertEquals("Nenhum produto cadastrado.\n", outContent.toString());
    }

    @Test
    @DisplayName("Deve imprimir todos os produtos da lista")
    void shouldPrintAllProductsInList() {
        Product p1 = new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10);
        Product p2 = new Product("id2", "Mouse Sem Fio", "Eletrônicos", "Logitech", 150.00, 30);

        presenter.imprimirLista(List.of(p1, p2), "Nenhum produto cadastrado.");

        String output = outContent.toString();
        assertTrue(output.contains("Notebook Gamer"));
        assertTrue(output.contains("Mouse Sem Fio"));
        assertFalse(output.contains("Nenhum produto cadastrado."));
    }
}