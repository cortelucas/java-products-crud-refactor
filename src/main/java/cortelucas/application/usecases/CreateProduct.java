package cortelucas.application.usecases;

import java.util.UUID;

import cortelucas.application.dtos.CreateProductDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;

public class CreateProduct {
    private final ProductRepository productRepository;

    public CreateProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CreateProductDTO.Output execute(CreateProductDTO.Input input) {
        String id = UUID.randomUUID().toString().substring(0, 8);

        Product product = new Product(
                id,
                input.name(),
                input.segment(),
                input.brand(),
                input.price(),
                input.quantity());

        Product createdProduct = productRepository.create(product);

        return new CreateProductDTO.Output() {
            @Override
            public Product product() {
                return createdProduct;
            }
        };
    }
}
