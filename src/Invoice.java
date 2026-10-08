

import java.util.ArrayList;

public class Invoice {
    private String title;
    private Address billingAddress;
    private ArrayList<LineItem> lineItems;

    public Invoice(String title, Address billingAddress) {
        this.title = title;
        this.billingAddress = billingAddress;
        this.lineItems = new ArrayList<>();
    }

    public void addLineItem(Product product, int quantity) {
        lineItems.add(new LineItem(product, quantity));
    }

    public double calculateTotalDue() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.calculateTotal();
        }
        return total;
    }

    public String formatInvoice() {
        StringBuilder sb = new StringBuilder();

        // Title and Header info
        sb.append("===============================================\n");
        sb.append(String.format("               %s               \n", title.toUpperCase()));
        sb.append("===============================================\n\n");

        // Address block
        sb.append("CUSTOMER BILLING ADDRESS:\n");
        sb.append(billingAddress.formatAddress()).append("\n");
        sb.append("-----------------------------------------------\n\n");

        // Table Headers
        sb.append(String.format("%-20s %5s   %8s   %8s\n", "Item", "Qty", "Price", "Total"));
        sb.append("-----------------------------------------------\n");

        // Line Items
        for (LineItem item : lineItems) {
            sb.append(item.formatLineItem()).append("\n");
        }

        // Grand Total Amount Due
        sb.append("-----------------------------------------------\n");
        sb.append(String.format("%-36s $%7.2f\n", "AMOUNT DUE:", calculateTotalDue()));
        sb.append("===============================================\n");

        return sb.toString();
    }
}