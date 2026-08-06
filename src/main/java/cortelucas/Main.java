package cortelucas;

import cortelucas.application.repositories.ProductRepository;
import cortelucas.application.usecases.CreateProduct;
import cortelucas.application.usecases.DeleteProduct;
import cortelucas.application.usecases.FilterProducts;
import cortelucas.application.usecases.FindProductById;
import cortelucas.application.usecases.ListProducts;
import cortelucas.application.usecases.UpdateProduct;
import cortelucas.infrastructure.repositories.InMemoryProductsRepository;
import cortelucas.presentation.cli.ProductMenu;

public class Main {
    public static void main(String[] args) {

        ProductRepository repository = new InMemoryProductsRepository();

        CreateProduct createProduct = new CreateProduct(repository);
        ListProducts listProducts = new ListProducts(repository);
        FindProductById findProductById = new FindProductById(repository);
        UpdateProduct updateProduct = new UpdateProduct(repository);
        DeleteProduct deleteProduct = new DeleteProduct(repository);
        FilterProducts filterProducts = new FilterProducts(repository);

        ProductMenu menu = new ProductMenu(createProduct, listProducts, findProductById, updateProduct, deleteProduct,
                filterProducts);

        menu.start();

    }
}