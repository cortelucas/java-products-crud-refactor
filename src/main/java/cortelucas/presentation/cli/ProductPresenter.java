package cortelucas.presentation.cli;

import cortelucas.domain.entities.Product;

public class ProductPresenter {

    public void imprimirProduto(Product p) {
        System.out.printf("ID: %s | Nome: %s | Segmento: %s | Marca: %s | Valor: R$ %.2f | Qtd: %d%n",
                p.getId(), p.getName(), p.getSegment(), p.getBrand(), p.getPrice(), p.getQuantity());
    }

    public void imprimirLista(Iterable<Product> products, String mensagemVazia) {
        boolean vazio = true;
        for (Product p : products) {
            imprimirProduto(p);
            vazio = false;
        }
        if (vazio) {
            System.out.println(mensagemVazia);
        }
    }
}
