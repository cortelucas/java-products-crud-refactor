package cortelucas.presentation.cli;

import java.util.Optional;
import java.util.Scanner;

import cortelucas.application.dtos.CreateProductDTO;
import cortelucas.application.dtos.DeleteProductDTO;
import cortelucas.application.dtos.FilterProductsDTO;
import cortelucas.application.dtos.FindProductByIdDTO;
import cortelucas.application.dtos.ListProductsDTO;
import cortelucas.application.dtos.UpdateProductDTO;
import cortelucas.application.usecases.CreateProduct;
import cortelucas.application.usecases.DeleteProduct;
import cortelucas.application.usecases.FilterProducts;
import cortelucas.application.usecases.FindProductById;
import cortelucas.application.usecases.ListProducts;
import cortelucas.application.usecases.UpdateProduct;
import cortelucas.domain.entities.Product;
import cortelucas.domain.exceptions.ProductNotFoundException;

public class ProductMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final CreateProduct createProduct;
    private final ListProducts listProducts;
    private final FindProductById findProductById;
    private final UpdateProduct updateProduct;
    private final DeleteProduct deleteProduct;
    private final FilterProducts filterProducts;

    public ProductMenu(CreateProduct createProduct, ListProducts listProducts,
            FindProductById findProductById, UpdateProduct updateProduct,
            DeleteProduct deleteProduct, FilterProducts filterProducts) {
        this.createProduct = createProduct;
        this.listProducts = listProducts;
        this.findProductById = findProductById;
        this.updateProduct = updateProduct;
        this.deleteProduct = deleteProduct;
        this.filterProducts = filterProducts;
    }

    public void start() {
        System.out.println("=== Sistema de Gerenciamento de Produtos ===");
        System.out.print("Insira seu nome: ");
        String nomeUsuario = scanner.nextLine();
        System.out.println("Bem-vindo(a), " + nomeUsuario + "!");

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1 -> executarCadastro();
                    case 2 -> executarListagem();
                    case 3 -> executarBuscaPorId();
                    case 4 -> executarAlteracao();
                    case 5 -> executarRemocao();
                    case 6 -> executarFiltro();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (ProductNotFoundException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Cadastrar produto");
        System.out.println("2. Listar produtos");
        System.out.println("3. Buscar produto por ID");
        System.out.println("4. Alterar produto");
        System.out.println("5. Remover produto");
        System.out.println("6. Filtrar produtos por marca/segmento");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String lerId() {
        System.out.print("Insira o ID do produto: ");
        return scanner.nextLine().trim();
    }

    private Optional<String> lerCampoOpcionalTexto(String label) {
        System.out.print(label + " (Enter para não alterar/filtrar): ");
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? Optional.empty() : Optional.of(valor);
    }

    private void executarCadastro() {
        System.out.println("\n=== Cadastro de Produto ===");
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Segmento: ");
        String segment = scanner.nextLine();
        System.out.print("Marca: ");
        String brand = scanner.nextLine();
        System.out.print("Valor: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Quantidade: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        CreateProductDTO.Input input = new CreateProductDTO.Input() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String segment() {
                return segment;
            }

            @Override
            public String brand() {
                return brand;
            }

            @Override
            public double price() {
                return price;
            }

            @Override
            public int quantity() {
                return quantity;
            }
        };

        CreateProductDTO.Output output = createProduct.execute(input);
        System.out.println("Produto cadastrado com sucesso! ID: " + output.product().getId());
    }

    private void executarListagem() {
        System.out.println("\n=== Listagem de Produtos ===");
        ListProductsDTO.Output output = listProducts.execute();
        imprimirLista(output.products(), "Nenhum produto cadastrado.");
    }

    private void executarBuscaPorId() {
        System.out.println("\n=== Buscar Produto por ID ===");
        String id = lerId();
        FindProductByIdDTO.Output output = findProductById.execute(id);
        imprimirProduto(output.product());
    }

    private void executarAlteracao() {
        System.out.println("\n=== Alteração de Produto ===");
        String id = lerId();

        Optional<String> name = lerCampoOpcionalTexto("Novo nome");
        Optional<String> segment = lerCampoOpcionalTexto("Novo segmento");
        Optional<String> brand = lerCampoOpcionalTexto("Nova marca");

        System.out.print("Novo valor (Enter para não alterar): ");
        String precoStr = scanner.nextLine().trim();
        Optional<Double> price = precoStr.isEmpty() ? Optional.empty() : Optional.of(Double.parseDouble(precoStr));

        System.out.print("Nova quantidade (Enter para não alterar): ");
        String qtdStr = scanner.nextLine().trim();
        Optional<Integer> quantity = qtdStr.isEmpty() ? Optional.empty() : Optional.of(Integer.parseInt(qtdStr));

        UpdateProductDTO.Input input = new UpdateProductDTO.Input() {
            @Override
            public String id() {
                return id;
            }

            @Override
            public Optional<String> name() {
                return name;
            }

            @Override
            public Optional<String> segment() {
                return segment;
            }

            @Override
            public Optional<String> brand() {
                return brand;
            }

            @Override
            public Optional<Double> price() {
                return price;
            }

            @Override
            public Optional<Integer> quantity() {
                return quantity;
            }
        };

        UpdateProductDTO.Output output = updateProduct.execute(input);
        System.out.println("Produto alterado com sucesso!");
        imprimirProduto(output.product());
    }

    private void executarRemocao() {
        System.out.println("\n=== Remoção de Produto ===");
        String id = lerId();
        DeleteProductDTO.Output output = deleteProduct.execute(id);
        System.out.println("Produto removido com sucesso: " + output.product().getName());
    }

    private void executarFiltro() {
        System.out.println("\n=== Filtrar Produtos ===");
        Optional<String> brand = lerCampoOpcionalTexto("Marca");
        Optional<String> segment = lerCampoOpcionalTexto("Segmento");

        FilterProductsDTO.Input input = new FilterProductsDTO.Input() {
            @Override
            public Optional<String> brand() {
                return brand;
            }

            @Override
            public Optional<String> segment() {
                return segment;
            }
        };

        FilterProductsDTO.Output output = filterProducts.execute(input);
        imprimirLista(output.products(), "Nenhum produto encontrado com esses critérios.");
    }

    private void imprimirLista(Iterable<Product> products, String mensagemVazia) {
        boolean vazio = true;
        for (Product p : products) {
            imprimirProduto(p);
            vazio = false;
        }
        if (vazio) {
            System.out.println(mensagemVazia);
        }
    }

    private void imprimirProduto(Product p) {
        System.out.printf("ID: %s | Nome: %s | Segmento: %s | Marca: %s | Valor: R$ %.2f | Qtd: %d%n",
                p.getId(), p.getName(), p.getSegment(), p.getBrand(), p.getPrice(), p.getQuantity());
    }
}