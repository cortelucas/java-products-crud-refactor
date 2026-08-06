package cortelucas.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.application.dtos.FindProductByIdDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

import static org.junit.jupiter.api.Assertions.*;

class FindProductByIdTest {

    private ProductRepository repository;
    private FindProductById findProductById;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        findProductById = new FindProductById(repository);
    }

    @Test
    @DisplayName("Deve retornar o produto quando o ID existe")
    void shouldReturnProductWhenIdExists() {
        Product product = new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10);
        repository.create(product);

        FindProductByIdDTO.Output output = findProductById.execute("id1");

        assertNotNull(output.product());
        assertEquals("Notebook Gamer", output.product().getName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID não existe")
    void shouldThrowExceptionWhenIdDoesNotExist() {
        assertThrows(ProductNotFoundException.class,
                () -> findProductById.execute("id-inexistente"));
    }
}