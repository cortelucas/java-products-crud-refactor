package cortelucas.application.dtos;

import java.util.Optional;

import cortelucas.domain.entities.Product;

public class FilterProductsDTO {

    public interface Input {
        Optional<String> brand();

        Optional<String> segment();
    }

    public interface Output {
        Iterable<Product> products();
    }
}
