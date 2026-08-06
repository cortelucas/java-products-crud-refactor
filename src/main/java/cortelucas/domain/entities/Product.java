package cortelucas.domain.entities;

// Domain Entity Products

public class Product {
    public String id;
    public String name;
    public String segment;
    public String brand;
    public double price;
    public int quantity;

    public Product(String id, String name, String segment, String brand, double price, int quantity) {

        validateName(name);
        validateSegment(segment);
        validateBrand(brand);
        validatePrice(price);
        validateQuantity(quantity);

        this.id = id;
        this.name = name;
        this.segment = segment;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        validateSegment(segment);
        this.segment = segment;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        validateBrand(brand);
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateName(String name) {
        validateText(name, "nome", 3, 255);
    }

    private void validateSegment(String segment) {
        validateText(segment, "segmento", 3, 255);
    }

    private void validateBrand(String brand) {
        validateText(brand, "marca", 3, 255);
    }

    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("A quantidade do produto não pode ser negativa.");
        }
    }

    private void validateText(String value, String fieldLabel, int minLength, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O " + fieldLabel + " do produto não pode ser vazio.");
        }
        if (value.trim().length() < minLength) {
            throw new IllegalArgumentException(
                    "O " + fieldLabel + " do produto precisa ter pelo menos " + minLength + " caracteres.");
        }
        if (value.trim().length() > maxLength) {
            throw new IllegalArgumentException(
                    "O " + fieldLabel + " do produto não pode ter mais de " + maxLength + " caracteres.");
        }
    }
}
