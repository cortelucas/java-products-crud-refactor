package cortelucas.application.dtos;

import cortelucas.domain.entities.Product;

public class ListProductsDTO {
    public interface Output {
        Iterable<Product> products();
    }
}
