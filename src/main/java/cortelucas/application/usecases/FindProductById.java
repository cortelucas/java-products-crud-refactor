package cortelucas.application.usecases;

import cortelucas.application.dtos.FindProductByIdDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;

public class FindProductById {

    private final ProductRepository repository;

    public FindProductById(ProductRepository repository) {
        this.repository = repository;
    }

    public FindProductByIdDTO.Output execute(String id) {
        Product product = repository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return new FindProductByIdDTO.Output() {
            @Override
            public Product product() {
                return product;
            }
        };
    }
}