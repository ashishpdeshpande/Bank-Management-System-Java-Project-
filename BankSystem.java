package bankSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BankSystem {
    private Map<Integer, Bank> accounts = new HashMap<>();

    // Load all accounts from the file
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int accNo = Integer.parseInt(parts[0]);
                String name = parts[1];
                String accType = parts[2];
                int balance = Integer.parseInt(parts[3]);
                Bank account = new Bank(accNo, name, accType, balance);
                accounts.put(accNo, account);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Open a new account
    public void openAccount(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Account Type: ");
        String accType = sc.next();
        System.out.print("Enter Initial Balance: ");
        int balance = sc.nextInt();
        int accNo = new Random().nextInt(9000) + 1000;

        Bank account = new Bank(accNo, name, accType, balance);
        account.saveToFile();
        accounts.put(accNo, account);
        System.out.println("Account created successfully. Account No: " + accNo);
    }

    // Show account details
    public void showAccount(Scanner sc) {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();
        Bank account = accounts.get(accNo);
        if (account != null) {
            account.display();
        } else {
            System.out.println("Account not found.");
        }
    }

    // Display all stored accounts
    public void viewAllAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            if ((line = reader.readLine()) == null) {
                System.out.println("No accounts stored.");
                return;
            }
            reader.close(); // Close the file after checking if it is empty

            System.out.println("\nStored Accounts:");
            // Re-open the file to read and display data
            try (BufferedReader reader2 = new BufferedReader(new FileReader("accounts.txt"))) {
                while ((line = reader2.readLine()) != null) {
                    String[] parts = line.split(",");
                    int accNo = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String accType = parts[2];
                    int balance = Integer.parseInt(parts[3]);
                    System.out.println("Account No: " + accNo + ", Name: " + name + ", Type: " + accType + ", Balance: " + balance);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankSystem bankSystem = new BankSystem();
        bankSystem.loadFromFile();

        int choice;
        do {
            System.out.println("\n1) Open Account");
            System.out.println("2) Show Account");
            System.out.println("3) View All Accounts");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bankSystem.openAccount(sc);
                    break;
                case 2:
                    bankSystem.showAccount(sc);
                    break;
                case 3:
                    bankSystem.viewAllAccounts();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }
}
