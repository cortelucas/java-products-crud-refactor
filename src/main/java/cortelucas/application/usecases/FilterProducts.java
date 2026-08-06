package cortelucas.application.usecases;

import java.util.ArrayList;
import java.util.List;

import cortelucas.application.dtos.FilterProductsDTO;
import cortelucas.application.repositories.ProductRepository;
import cortelucas.domain.entities.Product;

public class FilterProducts {

    private final ProductRepository repository;

    public FilterProducts(ProductRepository repository) {
        this.repository = repository;
    }

    public FilterProductsDTO.Output execute(FilterProductsDTO.Input input) {

        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : repository.findAll()) {
            boolean matchesBrand = input.brand().map(b -> b.equals(product.getBrand())).orElse(true);
            boolean matchesSegment = input.segment().map(s -> s.equals(product.getSegment())).orElse(true);

            if (matchesBrand && matchesSegment) {
                filteredProducts.add(product);
            }
        }

        return new FilterProductsDTO.Output() {
            @Override
            public Iterable<Product> products() {
                return filteredProducts;
            }
        };
    }
}
