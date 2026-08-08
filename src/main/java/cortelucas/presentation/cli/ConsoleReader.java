package cortelucas.presentation.cli;

import java.util.Optional;
import java.util.Scanner;

import cortelucas.domain.validation.NumberValidator;
import cortelucas.domain.validation.TextValidator;

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

    public double lerDouble(String label, String fieldLabel) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            try {
                double parsed = Double.parseDouble(valor);
                NumberValidator.validateNonNegative(parsed, fieldLabel);
                return parsed;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número (ex: 4500.00).");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int lerInt(String label, String fieldLabel) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            try {
                int parsed = Integer.parseInt(valor);
                NumberValidator.validateNonNegative(parsed, fieldLabel);
                return parsed;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro (ex: 10).");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Optional<String> lerTextoOpcional(String label) {
        System.out.print(label + " (Enter para não alterar/filtrar): ");
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? Optional.empty() : Optional.of(valor);
    }

    public Optional<Double> lerDoubleOpcional(String label, String fieldLabel) {
        while (true) {
            System.out.print(label + " (Enter para não alterar): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return Optional.empty();
            }
            try {
                double parsed = Double.parseDouble(valor);
                NumberValidator.validateNonNegative(parsed, fieldLabel);
                return Optional.of(parsed);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número ou deixe em branco.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Optional<Integer> lerIntOpcional(String label, String fieldLabel) {
        while (true) {
            System.out.print(label + " (Enter para não alterar): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return Optional.empty();
            }
            try {
                int parsed = Integer.parseInt(valor);
                NumberValidator.validateNonNegative(parsed, fieldLabel);
                return Optional.of(parsed);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro ou deixe em branco.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String lerTextoValidado(String label, String fieldLabel, int minLength, int maxLength) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            try {
                TextValidator.validate(valor, fieldLabel, minLength, maxLength);
                return valor;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Optional<String> lerTextoOpcionalValidado(String label, String fieldLabel, int minLength, int maxLength) {
        while (true) {
            System.out.print(label + " (Enter para não alterar): ");
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return Optional.empty();
            }
            try {
                TextValidator.validate(valor, fieldLabel, minLength, maxLength);
                return Optional.of(valor);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
