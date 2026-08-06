package cortelucas.application.dtos;

import cortelucas.domain.entities.Product;

public class FindProductByIdDTO {

    public interface Output {
        Product product();
    }
}
