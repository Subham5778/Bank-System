import java.util.*;

class BankAccount {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
            System.out.println("Deposit successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public void viewTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction history.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transfer successful. Transferred " + amount + " to account: " + toAccount.getAccountNumber());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }
}

class CheckingAccount extends BankAccount {
    public CheckingAccount(String accountNumber) {
        super(accountNumber);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest);
        System.out.println("Interest added: " + interest);
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": " + amount;
    }
}

public class BankSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Set<String> userNames = new HashSet<>();
    private static String currentUser;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nBanking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    if (currentUser != null) {
                        manageAccount();
                    }
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (userNames.contains(username)) {
            System.out.println("Username already exists.");
        } else {
            userNames.add(username);
            currentUser = username;
            System.out.println("Registration successful. Please login.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (userNames.contains(username)) {
            currentUser = username;
            System.out.println("Login successful. Welcome, " + username + "!");
        } else {
            System.out.println("Username not found. Please register.");
            currentUser = null;
        }
    }

    private static void manageAccount() {
        while (true) {
            System.out.println("\nManage Account");
            System.out.println("1. Create Checking Account");
            System.out.println("2. Create Savings Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. View Balance");
            System.out.println("6. View Transaction History");
            System.out.println("7. Transfer Between Accounts");
            System.out.println("8. Add Interest (Savings Account)");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createCheckingAccount();
                    break;
                case 2:
                    createSavingsAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    viewBalance();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    transfer();
                    break;
                case 8:
                    addInterest();
                    break;
                case 9:
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createCheckingAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        accounts.put(accountNumber, new CheckingAccount(accountNumber));
        System.out.println("Checking account created successfully.");
    }

    private static void createSavingsAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter interest rate: ");
        double interestRate = scanner.nextDouble();
        accounts.put(accountNumber, new SavingsAccount(accountNumber, interestRate));
        System.out.println("Savings account created successfully.");
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Current balance: " + accounts.get(accountNumber).getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).viewTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transfer() {
        System.out.print("Enter source account number: ");
        String sourceAccountNumber = scanner.next();
        System.out.print("Enter destination account number: ");
        String destinationAccountNumber = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        if (accounts.containsKey(sourceAccountNumber) && accounts.containsKey(destinationAccountNumber)) {
            accounts.get(sourceAccountNumber).transfer(accounts.get(destinationAccountNumber), amount);
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static void addInterest() {
        System.out.print("Enter savings account number: ");
        String accountNumber = scanner.next();
        if (accounts.containsKey(accountNumber) && accounts.get(accountNumber) instanceof SavingsAccount) {
            ((SavingsAccount) accounts.get(accountNumber)).addInterest();
        } else {
            System.out.println("Savings account not found.");
        }
    }
}
