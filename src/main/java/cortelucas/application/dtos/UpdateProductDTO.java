package cortelucas.application.dtos;

import java.util.Optional;

import cortelucas.domain.entities.Product;

public class UpdateProductDTO {

    public interface Input {
        String id();

        Optional<String> name();

        Optional<String> segment();

        Optional<String> brand();

        Optional<Double> price();

        Optional<Integer> quantity();
    }

    public interface Output {
        Product product();
    }
}
