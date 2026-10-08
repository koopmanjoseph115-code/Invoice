

public class LineItem {
    private Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateTotal() {
        return product.getUnitPrice() * quantity;
    }

    public String formatLineItem() {
        // Formats columns dynamically to keep them clean in a monospaced view
        return String.format("%-20s %5d   $%7.2f   $%7.2f",
                product.getName(),
                quantity,
                product.getUnitPrice(),
                calculateTotal());
    }
}