package cortelucas.application.repositories;

import cortelucas.domain.entities.Product;

public interface ProductRepository {
    Product create(Product input);

    Product findById(String id);

    void delete(String id);

    void update(Product product);

    Iterable<Product> findAll();
}
