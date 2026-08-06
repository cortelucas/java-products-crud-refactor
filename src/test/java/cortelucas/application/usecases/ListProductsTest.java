package cortelucas.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import cortelucas.application.dtos.ListProductsDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;

import static org.junit.jupiter.api.Assertions.*;

class ListProductsTest {

    private ProductRepository repository;
    private ListProducts listProducts;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductsRepository();
        listProducts = new ListProducts(repository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos cadastrados")
    void shouldReturnEmptyListWhenNoProducts() {
        ListProductsDTO.Output output = listProducts.execute();

        List<Product> result = toList(output.products());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar todos os produtos cadastrados")
    void shouldReturnAllProducts() {
        repository.create(new Product("id1", "Notebook Gamer", "Eletrônicos", "Dell", 4500.00, 10));
        repository.create(new Product("id2", "Mouse Sem Fio", "Eletrônicos", "Logitech", 150.00, 30));

        ListProductsDTO.Output output = listProducts.execute();

        List<Product> result = toList(output.products());
        assertEquals(2, result.size());
    }

    private List<Product> toList(Iterable<Product> products) {
        List<Product> list = new ArrayList<>();
        products.forEach(list::add);
        return list;
    }
}