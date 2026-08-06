package cortelucas.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("Deve criar um produto váliod com todos os campos corretos")
    void shouldCreateValidProduct() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, 10);

        assertEquals(id, product.getId());
        assertEquals("Notebook Gamer", product.getName());
        assertEquals("Eletrônicos", product.getSegment());
        assertEquals("Dell", product.getBrand());
        assertEquals(1000.0, product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    @DisplayName("Não deve criar produto com nome nulo")
    void shouldNotCreateProductWithNullName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, null, "Eletrônicos", "Dell", 1000.0, 10));
    }

    @Test
    @DisplayName("Não deve criar produto com o nome vazio")
    void shouldNotCreateProductWithEmptyName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, "", "Eletrônicos", "Dell", 1000.0, 10));
    }

    @Test
    @DisplayName("Não deve criar produto com nome menor que 3 caracteres")
    void shouldNotCreateProductWithShortName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, "No", "Eletrônicos", "Dell", 1000.0, 10));
    }

    @Test
    @DisplayName("Não deve criar produto com marca vazia")
    void shouldNotCreateProductWithEmptyBrand() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, "Notebook Gamer", "Eletrônicos", "", 1000.0, 10));
    }

    @Test
    @DisplayName("Não deve criar produto com preço negativo")
    void shouldNotCreateProductWithNegativePrice() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", -1000.0, 10));
    }

    @Test
    @DisplayName("Não deve criar produto com quantidade negativa")
    void shouldNotCreateProductWithNegativeQuantity() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        assertThrows(IllegalArgumentException.class,
                () -> new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, -10));
    }

    @Test
    @DisplayName("Deve permitir criar produto com quantidade zero")
    void shouldCreateProductWithZeroQuantity() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, 0);
        assertEquals(0, product.getQuantity());
    }

    @Test
    @DisplayName("Deve permitir alterar o nome do produto para um valor válido")
    void shouldUpdateProductName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, 10);
        product.setName("Notebook Gamer 2");
        assertEquals("Notebook Gamer 2", product.getName());
    }

    @Test
    @DisplayName("Não deve permitir alterar o nome do produto para um valor inválido")
    void shouldNotUpdateProductName() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, 10);
        assertThrows(IllegalArgumentException.class, () -> product.setName(""));
    }

    @Test
    @DisplayName("Não deve permitir alterar a quantidade para um valor negativo")
    void shouldNotUpdateProductQuantity() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product(id, "Notebook Gamer", "Eletrônicos", "Dell", 1000.0, 10);
        assertThrows(IllegalArgumentException.class, () -> product.setQuantity(-1));
    }
}
