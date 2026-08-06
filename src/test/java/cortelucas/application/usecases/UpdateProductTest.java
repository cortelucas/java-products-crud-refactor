package cortelucas.application.usecases;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.application.dtos.UpdateProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProductTest {

    private ProductRepository repository;
    private UpdateProduct updateProduct;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        updateProduct = new UpdateProduct(repository);
        repository.create(new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10));
    }

    private UpdateProductDTO.Input inputWith(String id, Optional<String> name, Optional<String> segment,
            Optional<String> brand, Optional<Double> price, Optional<Integer> quantity) {
        return new UpdateProductDTO.Input() {
            @Override
            public String id() {
                return id;
            }

            @Override
            public Optional<String> name() {
                return name;
            }

            @Override
            public Optional<String> segment() {
                return segment;
            }

            @Override
            public Optional<String> brand() {
                return brand;
            }

            @Override
            public Optional<Double> price() {
                return price;
            }

            @Override
            public Optional<Integer> quantity() {
                return quantity;
            }
        };
    }

    @Test
    @DisplayName("Deve atualizar apenas o preço, mantendo os demais campos")
    void shouldUpdateOnlyPrice() {
        UpdateProductDTO.Input input = inputWith("id1", Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(5000.00), Optional.empty());

        UpdateProductDTO.Output output = updateProduct.execute(input);

        assertEquals(5000.00, output.product().getPrice());
        assertEquals("Notebook Gamer", output.product().getName());
        assertEquals("Dell", output.product().getBrand());
    }

    @Test
    @DisplayName("Deve atualizar múltiplos campos ao mesmo tempo")
    void shouldUpdateMultipleFields() {
        UpdateProductDTO.Input input = inputWith("id1", Optional.of("Notebook Gamer Pro"),
                Optional.empty(), Optional.of("Acer"), Optional.empty(), Optional.empty());

        UpdateProductDTO.Output output = updateProduct.execute(input);

        assertEquals("Notebook Gamer Pro", output.product().getName());
        assertEquals("Acer", output.product().getBrand());
        assertEquals("Eletrônicos", output.product().getSegment());
    }

    @Test
    @DisplayName("Deve persistir a alteração no repositório")
    void shouldPersistChangesInRepository() {
        UpdateProductDTO.Input input = inputWith("id1", Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(5000.00), Optional.empty());

        updateProduct.execute(input);

        Product persisted = repository.findById("id1");
        assertEquals(5000.00, persisted.getPrice());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não existe")
    void shouldThrowExceptionWhenProductDoesNotExist() {
        UpdateProductDTO.Input input = inputWith("id-inexistente", Optional.of("Novo Nome"),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> updateProduct.execute(input));
    }

    @Test
    @DisplayName("Deve propagar erro de validação ao tentar atualizar com valor inválido")
    void shouldPropagateValidationErrorFromEntity() {
        UpdateProductDTO.Input input = inputWith("id1", Optional.of(""), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> updateProduct.execute(input));
    }
}
