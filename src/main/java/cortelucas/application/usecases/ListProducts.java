package cortelucas.application.usecases;

import cortelucas.application.dtos.ListProductsDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;

public class ListProducts {

    private ProductRepository repository;

    public ListProducts(ProductRepository repository) {
        this.repository = repository;
    }

    public ListProductsDTO.Output execute() {
        Iterable<Product> products = repository.findAll();

        return new ListProductsDTO.Output() {
            @Override
            public Iterable<Product> products() {
                return products;
            }
        };
    }
}
