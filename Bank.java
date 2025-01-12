package bankSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Bank {
    private int accNo;
    private String name;
    private String accType;
    private int balance;

    public Bank(int accNo, String name, String accType, int balance) {
        this.accNo = accNo;
        this.name = name;
        this.accType = accType;
        this.balance = balance;
    }

    // Save account details to the file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            writer.write(accNo + "," + name + "," + accType + "," + balance);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Static method to view all accounts from the file and display in the table
    public static void viewAllAccounts(DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                model.addRow(parts); // Add each row to the table model
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Static method to view a specific account by account number
    public static void showAccount(int accNo, DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == accNo) {
                    model.setRowCount(0); // Clear existing rows in the table
                    model.addRow(parts); // Add the account row to the table model
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Account not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}
