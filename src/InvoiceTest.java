import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {
    private Address address;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        address = new Address("123 Main St", "Cincinnati", "OH", "45202");
        product1 = new Product("Toaster", 29.99);
        product2 = new Product("Blender", 49.95);
    }

    @Test
    public void testCalculateTotalDue() {
        Invoice invoice = new Invoice("Test Invoice", address);

        // Add 2 Toasters ($29.99 * 2 = $59.98)
        invoice.addLineItem(product1, 2);
        // Add 1 Blender ($49.95 * 1 = $49.95)
        invoice.addLineItem(product2, 1);

        // Expected total = $59.98 + $49.95 = $109.93
        assertEquals(109.93, invoice.calculateTotalDue(), 0.001);
    }
}