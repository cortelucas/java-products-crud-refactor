package cortelucas.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cortelucas.application.dtos.CreateProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

class CreateProductTest {

    private ProductRepository repository;
    private CreateProduct createProduct;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        createProduct = new CreateProduct(repository);
    }

    private CreateProductDTO.Input validInput() {
        return new CreateProductDTO.Input() {
            
            @Override
            public String name() {
                return "Notebook Gamer";
            }

            @Override
            public String segment() {
                return "Eletrônicos";
            }

            @Override
            public String brand() {
                return "Dell";
            }

            @Override
            public double price() {
                return 4500.00;
            }

            @Override
            public int quantity() {
                return 10;
            }
        };
    }

    @Test
    @DisplayName("Deve criar um produto e persistir no repositório")
    void shouldCreateAndPersistProduct() {
        CreateProductDTO.Output output = createProduct.execute(validInput());

        assertNotNull(output.product());
        assertNotNull(output.product().getId());
        assertEquals("Notebook Gamer", output.product().getName());

        Product persisted = repository.findById(output.product().getId());
        assertNotNull(persisted);
        assertEquals("Dell", persisted.getBrand());
    }

    @Test
    @DisplayName("Deve gerar um ID diferente para cada produto criado")
    void shouldGenerateDifferentIdsForEachProduct() {
        CreateProductDTO.Output first = createProduct.execute(validInput());
        CreateProductDTO.Output second = createProduct.execute(validInput());

        assertNotEquals(first.product().getId(), second.product().getId());
    }

    @Test
    @DisplayName("Deve propagar erro de validação vindo da entidade Product")
    void shouldPropagateValidationErrorFromEntity() {
        CreateProductDTO.Input invalidInput = new CreateProductDTO.Input() {
            @Override
            public String name() {
                return "";
            } // inválido

            @Override
            public String segment() {
                return "Eletrônicos";
            }

            @Override
            public String brand() {
                return "Dell";
            }

            @Override
            public double price() {
                return 4500.00;
            }

            @Override
            public int quantity() {
                return 10;
            }
        };

        assertThrows(IllegalArgumentException.class, () -> createProduct.execute(invalidInput));
    }
}
