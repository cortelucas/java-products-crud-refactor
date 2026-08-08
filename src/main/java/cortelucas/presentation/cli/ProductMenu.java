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
import cortelucas.domain.exceptions.ProductNotFoundException;

public class ProductMenu {

    private final ConsoleReader reader = new ConsoleReader(new Scanner(System.in));
    private final ProductPresenter presenter = new ProductPresenter();

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
        String nomeUsuario = reader.lerLinha("Insira seu nome");
        System.out.println("Bem-vindo(a), " + nomeUsuario + "!");

        int opcao;
        do {
            exibirMenu();
            opcao = reader.lerOpcao();

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

        reader.close();
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

    private void executarCadastro() {
        System.out.println("\n=== Cadastro de Produto ===");
        String name = reader.lerTextoValidado("Nome", 3, 255);
        String segment = reader.lerTextoValidado("Segmento", 3, 255);
        String brand = reader.lerTextoValidado("Marca", 3, 255);
        double price = reader.lerDouble("Valor");
        int quantity = reader.lerInt("Quantidade");

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
        presenter.imprimirLista(output.products(), "Nenhum produto cadastrado.");
    }

    private void executarBuscaPorId() {
        System.out.println("\n=== Buscar Produto por ID ===");
        String id = reader.lerTexto("Insira o ID do produto");
        FindProductByIdDTO.Output output = findProductById.execute(id);
        presenter.imprimirProduto(output.product());
    }

    private void executarAlteracao() {
        System.out.println("\n=== Alteração de Produto ===");
        String id = reader.lerTexto("Insira o ID do produto");

        Optional<String> name = reader.lerTextoOpcionalValidado("Novo nome", 3, 255);
        Optional<String> segment = reader.lerTextoOpcionalValidado("Novo segmento", 3, 255);
        Optional<String> brand = reader.lerTextoOpcionalValidado("Nova marca", 3, 255);
        Optional<Double> price = reader.lerDoubleOpcional("Novo valor");
        Optional<Integer> quantity = reader.lerIntOpcional("Nova quantidade");

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
        presenter.imprimirProduto(output.product());
    }

    private void executarRemocao() {
        System.out.println("\n=== Remoção de Produto ===");
        String id = reader.lerTexto("Insira o ID do produto");
        DeleteProductDTO.Output output = deleteProduct.execute(id);
        System.out.println("Produto removido com sucesso: " + output.product().getName());
    }

    private void executarFiltro() {
        System.out.println("\n=== Filtrar Produtos ===");
        Optional<String> brand = reader.lerTextoOpcional("Marca");
        Optional<String> segment = reader.lerTextoOpcional("Segmento");

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
        presenter.imprimirLista(output.products(), "Nenhum produto encontrado com esses critérios.");
    }
}
