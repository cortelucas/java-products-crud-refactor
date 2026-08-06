package cortelucas.application.usecases;

import cortelucas.application.dtos.UpdateProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;

public class UpdateProduct {

    private final ProductRepository repository;

    public UpdateProduct(ProductRepository repository) {
        this.repository = repository;
    }

    public UpdateProductDTO.Output execute(UpdateProductDTO.Input input) {

        Product product = repository.findById(input.id());

        if (product == null) {
            throw new ProductNotFoundException(input.id());
        }

        input.name().ifPresent(product::setName);
        input.segment().ifPresent(product::setSegment);
        input.brand().ifPresent(product::setBrand);
        input.price().ifPresent(product::setPrice);
        input.quantity().ifPresent(product::setQuantity);

        repository.update(product);

        return new UpdateProductDTO.Output() {
            @Override
            public Product product() {
                return product;
            }
        };
    }
}
