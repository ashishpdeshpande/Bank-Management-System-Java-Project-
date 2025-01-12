package bankSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

class BankSystemGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, typeField, balanceField;
    private JButton createButton, showButton, showAllButton, submitButton;
    private JPanel formPanel, buttonPanel, createAccountPanel;
    private JScrollPane tableScroll;

    public BankSystemGUI() {
        // Initialize the JFrame
        frame = new JFrame("Bank Management System");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a table with columns for Account No, Name, Type, and Balance
        model = new DefaultTableModel(new String[]{"Account No", "Name", "Account Type", "Balance"}, 0);
        table = new JTable(model);
        tableScroll = new JScrollPane(table);

        // Create a panel for form inputs (Create Account Form), initially hidden
        createAccountPanel = new JPanel(new GridLayout(4, 2));
        createAccountPanel.setVisible(false); // Initially hidden

        createAccountPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        createAccountPanel.add(nameField);

        createAccountPanel.add(new JLabel("Account Type:"));
        typeField = new JTextField();
        createAccountPanel.add(typeField);

        createAccountPanel.add(new JLabel("Initial Balance:"));
        balanceField = new JTextField();
        createAccountPanel.add(balanceField);

        // Buttons Panel
        buttonPanel = new JPanel();
        createButton = new JButton("Create Account");
        showButton = new JButton("Show Account");
        showAllButton = new JButton("Show All Accounts");

        buttonPanel.add(createButton);
        buttonPanel.add(showButton);
        buttonPanel.add(showAllButton);

        // Action listener for "Create Account"
        createButton.addActionListener(e -> {
            // Show the form for creating an account (bottom part)
            createAccountPanel.setVisible(true); // Make form visible
        });

        // Action listener for "Show Account"
        showButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Account Number:");
            if (input != null && !input.isEmpty()) {
                int accNo = Integer.parseInt(input);
                model.setRowCount(0); // Clear existing rows in the table
                Bank.showAccount(accNo, model); // Show specific account by account number
            }
        });

        // Action listener for "Show All Accounts"
        showAllButton.addActionListener(e -> {
            model.setRowCount(0); // Clear existing rows in the table
            Bank.viewAllAccounts(model); // Display all accounts
        });

        // Action listener for form submission
        submitButton = new JButton("Submit");
        createAccountPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String type = typeField.getText();
            int balance = Integer.parseInt(balanceField.getText());
            int accNo = new Random().nextInt(9000) + 1000;
            Bank bank = new Bank(accNo, name, type, balance);
            bank.saveToFile();
            JOptionPane.showMessageDialog(frame, "Account Created Successfully!");
            nameField.setText("");
            typeField.setText("");
            balanceField.setText("");
            createAccountPanel.setVisible(false); // Hide form again after submission
            model.setRowCount(0); // Clear table
            Bank.viewAllAccounts(model); // Refresh the table to show all accounts
        });

        // Add components to the frame
        frame.add(tableScroll, BorderLayout.CENTER);
        frame.add(createAccountPanel, BorderLayout.SOUTH); // Add the form panel at the bottom
        frame.add(buttonPanel, BorderLayout.NORTH); // Add the buttons panel to the top

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Initialize the BankSystemGUI
        new BankSystemGUI();
    }
}
