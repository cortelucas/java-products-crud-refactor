package cortelucas.presentation.cli;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleReader {

    private final Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lerLinha(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    public String lerTexto(String label) {
        return lerLinha(label).trim();
    }

    public int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double lerDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número (ex: 4500.00).");
            }
        }
    }

    public int lerInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro (ex: 10).");
            }
        }
    }

    public Optional<String> lerTextoOpcional(String label) {
        System.out.print(label + " (Enter para não alterar/filtrar): ");
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? Optional.empty() : Optional.of(valor);
    }

    public Optional<Double> lerDoubleOpcional(String label) {
        while (true) {
            System.out.print(label + " (Enter para não alterar): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return Optional.empty();
            }
            try {
                return Optional.of(Double.parseDouble(valor));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número ou deixe em branco.");
            }
        }
    }

    public Optional<Integer> lerIntOpcional(String label) {
        while (true) {
            System.out.print(label + " (Enter para não alterar): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return Optional.empty();
            }
            try {
                return Optional.of(Integer.parseInt(valor));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro ou deixe em branco.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
