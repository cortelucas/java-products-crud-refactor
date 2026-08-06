package cortelucas.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.application.dtos.DeleteProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

import static org.junit.jupiter.api.Assertions.*;

class DeleteProductTest {

    private ProductRepository repository;
    private DeleteProduct deleteProduct;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        deleteProduct = new DeleteProduct(repository);
        repository.create(new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10));
    }

    @Test
    @DisplayName("Deve remover o produto e retorná-lo no output")
    void shouldRemoveProductAndReturnIt() {
        DeleteProductDTO.Output output = deleteProduct.execute("id1");

        assertNotNull(output.product());
        assertEquals("Notebook Gamer", output.product().getName());
    }

    @Test
    @DisplayName("Deve remover o produto do repositório de fato")
    void shouldRemoveProductFromRepository() {
        deleteProduct.execute("id1");

        assertNull(repository.findById("id1"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não existe")
    void shouldThrowExceptionWhenProductDoesNotExist() {
        assertThrows(ProductNotFoundException.class,
                () -> deleteProduct.execute("id-inexistente"));
    }
}
