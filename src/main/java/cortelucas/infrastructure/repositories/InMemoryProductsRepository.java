package cortelucas.infrastructure.repositories;

import java.util.LinkedHashMap;
import java.util.Map;

import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;

public class InMemoryProductsRepository implements ProductRepository {

    private final Map<String, Product> products = new LinkedHashMap<>();

    @Override
    public Product create(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Product findById(String id) {
        return products.get(id);
    }

    @Override
    public void delete(String id) {
        products.remove(id);
    }

    @Override
    public void update(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Iterable<Product> findAll() {
        return products.values();
    }
}
