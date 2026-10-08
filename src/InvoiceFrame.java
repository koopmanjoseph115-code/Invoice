


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceFrame extends JFrame {

    private JTextField streetField, cityField, stateField, zipField;

    private JTextField prodNameField, priceField, qtyField;

    private JTextArea displayArea;
    private JButton addItemButton, generateButton;


    private Invoice activeInvoice;

    public InvoiceFrame() {
        setTitle("Lab 07A: Software Engineering Camp - Invoice");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel addressPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        addressPanel.setBorder(BorderFactory.createTitledBorder("Customer Address Setup"));
        streetField = new JTextField();
        cityField = new JTextField();
        stateField = new JTextField();
        zipField = new JTextField();

        addressPanel.add(new JLabel("Street:"));
        addressPanel.add(streetField);
        addressPanel.add(new JLabel("City:"));
        addressPanel.add(cityField);
        addressPanel.add(new JLabel("State:"));
        addressPanel.add(stateField);
        addressPanel.add(new JLabel("Zip Code:"));
        addressPanel.add(zipField);


        JPanel productPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        productPanel.setBorder(BorderFactory.createTitledBorder("Add Item Details"));
        prodNameField = new JTextField();
        priceField = new JTextField();
        qtyField = new JTextField();

        productPanel.add(new JLabel("Product Name:"));
        productPanel.add(new JLabel("Unit Price ($):"));
        productPanel.add(new JLabel("Quantity:"));
        productPanel.add(prodNameField);
        productPanel.add(priceField);
        productPanel.add(qtyField);

        inputPanel.add(addressPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(productPanel);

        add(inputPanel, BorderLayout.NORTH);


        displayArea = new JTextArea();
        displayArea.setEditable(false);

        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Live Invoice Output Preview"));
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addItemButton = new JButton("Add Line Item");
        generateButton = new JButton("Generate Complete Invoice");

        buttonPanel.add(addItemButton);
        buttonPanel.add(generateButton);
        add(buttonPanel, BorderLayout.SOUTH);



        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (activeInvoice == null) {
                        if (streetField.getText().trim().isEmpty() || cityField.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(InvoiceFrame.this, "Please completely fill out the customer address section first.");
                            return;
                        }
                        Address address = new Address(
                                streetField.getText().trim(),
                                cityField.getText().trim(),
                                stateField.getText().trim(),
                                zipField.getText().trim()
                        );
                        activeInvoice = new Invoice("Invoiced Client Statement", address);


                        streetField.setEditable(false);
                        cityField.setEditable(false);
                        stateField.setEditable(false);
                        zipField.setEditable(false);
                    }


                    String name = prodNameField.getText().trim();
                    double price = Double.parseDouble(priceField.getText().trim());
                    int qty = Integer.parseInt(qtyField.getText().trim());

                    if (name.isEmpty() || qty <= 0 || price < 0) {
                        throw new IllegalArgumentException();
                    }


                    Product product = new Product(name, price);
                    activeInvoice.addLineItem(product, qty);


                    prodNameField.setText("");
                    priceField.setText("");
                    qtyField.setText("");
                    prodNameField.requestFocus();


                    displayArea.setText(activeInvoice.formatInvoice());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InvoiceFrame.this, "Price must be a valid decimal and Quantity must be an integer.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(InvoiceFrame.this, "Please review input values. Do not leave product name blank.");
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeInvoice == null) {
                    JOptionPane.showMessageDialog(InvoiceFrame.this, "No transaction history has been staged yet. Add line items first.");
                    return;
                }
                // Updates text display pane to full compiled model text format
                displayArea.setText(activeInvoice.formatInvoice());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InvoiceFrame().setVisible(true);
        });
    }
}