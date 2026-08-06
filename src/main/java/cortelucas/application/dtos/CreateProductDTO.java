package cortelucas.application.dtos;

import cortelucas.domain.entities.Product;

public class CreateProductDTO {

    public interface Input {

        String name();

        String segment();

        String brand();

        double price();

        int quantity();
    }

    public interface Output {
        Product product();
    }
}
