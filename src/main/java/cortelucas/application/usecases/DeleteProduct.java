package cortelucas.application.usecases;

import cortelucas.application.dtos.DeleteProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;

public class DeleteProduct {

    private final ProductRepository repository;

    public DeleteProduct(ProductRepository repository) {
        this.repository = repository;
    }

    public DeleteProductDTO.Output execute(String id) {
        Product product = repository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        repository.delete(id);

        return new DeleteProductDTO.Output() {
            @Override
            public Product product() {
                return product;
            }
        };
    }
}
