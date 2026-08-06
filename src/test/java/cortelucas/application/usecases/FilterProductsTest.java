package cortelucas.application.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.application.dtos.FilterProductsDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

import static org.junit.jupiter.api.Assertions.*;

class FilterProductsTest {

    private ProductRepository repository;
    private FilterProducts filterProducts;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        filterProducts = new FilterProducts(repository);

        repository.create(new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10));
        repository.create(new Product("id2", "Mouse Sem Fio", "Eletrônicos", "Logitech", 150.00, 30));
        repository.create(new Product("id3", "Camiseta Polo", "Vestuário", "Dell", 89.90, 20));
    }

    private FilterProductsDTO.Input inputWith(Optional<String> brand, Optional<String> segment) {
        return new FilterProductsDTO.Input() {
            @Override
            public Optional<String> brand() {
                return brand;
            }

            @Override
            public Optional<String> segment() {
                return segment;
            }
        };
    }

    private List<Product> toList(Iterable<Product> products) {
        List<Product> list = new ArrayList<>();
        products.forEach(list::add);
        return list;
    }

    @Test
    @DisplayName("Deve filtrar produtos apenas por marca")
    void shouldFilterByBrandOnly() {
        FilterProductsDTO.Input input = inputWith(Optional.of("Dell"), Optional.empty());

        List<Product> result = toList(filterProducts.execute(input).products());

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getBrand().equals("Dell")));
    }

    @Test
    @DisplayName("Deve filtrar produtos apenas por segmento")
    void shouldFilterBySegmentOnly() {
        FilterProductsDTO.Input input = inputWith(Optional.empty(), Optional.of("Eletrônicos"));

        List<Product> result = toList(filterProducts.execute(input).products());

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getSegment().equals("Eletrônicos")));
    }

    @Test
    @DisplayName("Deve filtrar produtos combinando marca e segmento")
    void shouldFilterByBrandAndSegmentCombined() {
        FilterProductsDTO.Input input = inputWith(Optional.of("Dell"), Optional.of("Eletrônicos"));

        List<Product> result = toList(filterProducts.execute(input).products());

        assertEquals(1, result.size());
        assertEquals("id1", result.get(0).getId());
    }

    @Test
    @DisplayName("Deve retornar todos os produtos quando nenhum filtro é informado")
    void shouldReturnAllProductsWhenNoFilterProvided() {
        FilterProductsDTO.Input input = inputWith(Optional.empty(), Optional.empty());

        List<Product> result = toList(filterProducts.execute(input).products());

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum produto atende ao filtro")
    void shouldReturnEmptyListWhenNoProductMatches() {
        FilterProductsDTO.Input input = inputWith(Optional.of("Samsung"), Optional.empty());

        List<Product> result = toList(filterProducts.execute(input).products());

        assertTrue(result.isEmpty());
    }
}
