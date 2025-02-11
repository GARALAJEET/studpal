import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        TimetableService timetableService = new TimetableService();
        Scanner scanner = new Scanner(System.in);
        boolean ans = true;

        System.out.println("Welcome to StudPal!");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email (optional): ");
            String email = scanner.nextLine();
            System.out.print("Enter full name (optional): ");
            String fullName = scanner.nextLine();

            User user = new User(username, password);
            user.setEmail(email);
            user.setFullName(fullName);

            authService.signUp(user);
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (authService.login(username, password)) {
                boolean loggedIn = true;
                while (loggedIn) {
                    System.out.println("1. Basic Details Management");
                    System.out.println("2. Manage Expenses");
                    System.out.println("3. Wallet Management");
                    System.out.println("4. Timetable Management:");
                    System.out.println("5. College Timetable ");
                    System.out.println("6. exit");
                    System.out.print("Choose an option: ");
                    int a = scanner.nextInt();
                    switch (a) {
                        case 1:
                            boolean back_B = true;
                            while (back_B) {
                                System.out.println("1. Update Basic Details");
                                System.out.println("2. View Basic Details");
                                System.out.println("3. Back");
                                System.out.print("Choose an option: ");
                                int subChoice = scanner.nextInt();
                                scanner.nextLine(); 

                                switch (subChoice) {
                                    case 2:
                                        authService.viewBasicDetails(username);
                                        break;
                                    case 1:
                                        System.out.print("Enter email: ");
                                        String email = scanner.nextLine();
                                        System.out.print("Enter full name: ");
                                        String fullName = scanner.nextLine();
                                        System.out.print("Enter age: ");
                                        int age = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline
                                        System.out.print("Enter address: ");
                                        String address = scanner.nextLine();
                                        System.out.print("Enter phone number: ");
                                        String phoneNumber = scanner.nextLine();

                                        System.out.print("Enter PAN card number: ");
                                        String panCardNumber = scanner.nextLine();

                                       
                                        while (!authService.isValidPanCardNumber(panCardNumber)) {
                                            System.out.println(
                                                    "Invalid PAN card number. Please enter a valid PAN card number.");
                                            System.out.print("Enter PAN card number: ");
                                            panCardNumber = scanner.nextLine();
                                        }
                                        User updatedUser = new User(username, password);
                                        updatedUser.setEmail(email);
                                        updatedUser.setFullName(fullName);
                                        updatedUser.setAge(age);
                                        updatedUser.setAddress(address);
                                        updatedUser.setPhoneNumber(phoneNumber);
                                        updatedUser.setpanNumber(panCardNumber);
                                        authService.updateBasicDetails(updatedUser);
                                        break;
                                    case 3:
                                        back_B = false;
                                        System.out.println("Back successfully.");
                                        break;
                                    default:
                                        System.out.println("Invalid choice.");
                                }
                            }
                            break;
                        case 2:
                            boolean ans_man = true;
                            while (ans_man) {
                                System.out.println("1. Add Expense");
                                System.out.println("2. View All Expenses");
                                System.out.println("3. View Expenses by Category");
                                System.out.println("4. print all Expense ");
                                System.out.println("5. Back");
                                System.out.print("Choose an option: ");
                                int expenseChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                MyArrayList<Expense> expenses;
                                switch (expenseChoice) {
                                    case 1:
                                        System.out.print("Enter amount: ");
                                        double amount = scanner.nextDouble();
                                        scanner.nextLine(); // Consume newline
                                        System.out.print("Enter category: ");
                                        String category = scanner.nextLine();
                                        System.out.print("Enter date (YYYY-MM-DD): ");
                                        String date = scanner.nextLine();
                                        System.out.print("Enter description: ");
                                        String description = scanner.nextLine();
                                        Expense expense = new Expense(username, amount, category, date, description);
                                        authService.addExpense(expense);
                                        break;
                                    case 2:
                                        expenses = authService.getAllExpenses(username);
                                        for (int i = 0; i < expenses.size(); i++) {
                                            System.out.println(expenses.get(i));
                                        }
                                        break;
                                    case 3:
                                        System.out.print("Enter category: ");
                                        String category1 = scanner.nextLine();
                                        expenses = authService.getExpensesByCategory(username, category1);
                                        for (int i = 0; i < expenses.size(); i++) {
                                            System.out.println(expenses.get(i));
                                        }
                                        break;
                                    case 4:
                                        authService.printExpensesToFile(username);
                                        break;
                                    case 5:
                                        ans_man = false;
                                        System.out.println("Back successfully.");
                                        break;
                                    default:
                                        System.out.println("Invalid choice.");
                                }
                            }
                            break;
                        case 3:
                            boolean walletMenu = true;
                            while (walletMenu) {
                                System.out.println("1. View Wallet Balance");
                                System.out.println("2. Add Funds to Wallet");
                                System.out.println("3. Spend Funds from Wallet");
                                System.out.println("4. Back");
                                System.out.print("Choose an option: ");
                                int walletOption = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                switch (walletOption) {
                                    case 1:
                                        double balance = authService.getWalletBalance(username);
                                        System.out.println("Current Wallet Balance: " + balance);
                                        break;
                                    case 2:
                                        System.out.print("Enter amount to add: ");
                                        double addAmount = scanner.nextDouble();
                                        scanner.nextLine(); 
                                        authService.addFunds(username, addAmount);
                                        break;
                                    case 3:
                                        System.out.print("Enter amount to spend: ");
                                        double spendAmount = scanner.nextDouble();
                                        scanner.nextLine(); 
                                        authService.spendFunds(username, spendAmount);
                                        break;

                                    case 4:
                                        walletMenu = false;
                                        System.out.println("Back to main menu.");
                                        break;
                                    default:
                                        System.out.println("Invalid option.");
                                }
                            }
                            break;
                        case 4:
                            boolean running = true;
                            while (running) {
                                System.out.println("1. Add Timetable Entry");
                                System.out.println("2. Update Timetable Entry");
                                System.out.println("3. Delete Timetable Entry");
                                System.out.println("4. View Timetable");
                                System.out.println("5. Export Timetable to Text File");
                                System.out.println("6. Exit");
                                System.out.print("Choose an option: ");
                                int choice1 = scanner.nextInt();
                                scanner.nextLine();
                                try {
                                    switch (choice1) {
                                        case 1:
                                           

                                            System.out.print("Enter event: ");
                                            String subject = scanner.nextLine();

                                            System.out.print("Enter day of the week: ");
                                            String dayOfWeek = scanner.nextLine();
                                            System.out.print("Enter location: ");
                                            String location = scanner.nextLine();
                                            System.out.print("Enter description: ");
                                            String description = scanner.nextLine();

                                            Timetable newTimetable = new Timetable();

                                            newTimetable.setUsername(username);
                                            newTimetable.setevent_title(subject);
                                            newTimetable.setDayOfWeek(dayOfWeek);
                                            newTimetable.setLocation(location);
                                            newTimetable.setDescription(description);
                                            timetableService.addTimetableEntry(newTimetable);
                                            System.out.println("Timetable entry added.");
                                            break;
                                        case 2:

                                            System.out.print("Enter ID of the entry to update: ");
                                            int updateId = scanner.nextInt();
                                            scanner.nextLine(); 

                                            System.out.print("Enter new event: ");
                                            String updateSubject = scanner.nextLine();

                                            System.out.print("Enter new day of the week: ");
                                            String updateDayOfWeek = scanner.nextLine();
                                            System.out.print("Enter new location: ");
                                            String updateLocation = scanner.nextLine();
                                            System.out.print("Enter new description: ");
                                            String updateDescription = scanner.nextLine();

                                            Timetable updatedTimetable = new Timetable();
                                            updatedTimetable.setId(updateId);
                                            updatedTimetable.setUsername(username);
                                            updatedTimetable.setevent_title(updateSubject);

                                            updatedTimetable.setDayOfWeek(updateDayOfWeek);
                                            updatedTimetable.setLocation(updateLocation);
                                            updatedTimetable.setDescription(updateDescription);
                                            timetableService.updateTimetableEntry(updatedTimetable);
                                            System.out.println("Timetable entry updated.");
                                            break;

                                        case 3:

                                            System.out.print("Enter ID of the entry to delete: ");
                                            int deleteId = scanner.nextInt();
                                            timetableService.deleteTimetableEntry(deleteId, username);
                                            System.out.println("Timetable entry deleted.");
                                            break;

                                        case 4:
                                      

                                            MyArrayList<Timetable> timetableList = timetableService
                                                    .getTimetableByUser(username);
                                            for (int i = 0; i < timetableList.size(); i++) {
                                                Timetable entry = timetableList.get(i);
                                                System.out.println(entry);
                                            }
                                            break;
                                        case 5:

                                            timetableService.exportTimetableToFile(username, "timetable.txt");
                                            System.out.println("Timetable exported to timetable.txt.");
                                            break;

                                        case 6:
                                            running = false;
                                            System.out.println("Exiting...");
                                            break;
                                        default:
                                            System.out.println("Invalid option.");
                                            break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            break;
                        case 5:
                        boolean a1=true;
                            while (a1) {
                                System.out.println("1. Add Timetable");
                                System.out.println("2. Update Timetable");
                                System.out.println("3. Delete Timetable");
                                System.out.println("4. View Timetables");
                                System.out.println("5. Exit");
                                System.out.print("Choose an option: ");
                                int option = scanner.nextInt();
                                scanner.nextLine(); 

                                try {
                                    switch (option) {
                                        case 1:
                                            timetableService.addTimetable(username);
                                            break;
                                        case 2:
                                            timetableService.updateTimetable();
                                            break;
                                        case 3:
                                            timetableService.deleteTimetable();
                                            break;
                                        case 4:
                                            timetableService.viewTimetables();
                                            
                                            
                                            break;
                                        case 5:
                                        a1=false;
                                            System.out.println("Exiting...");
                                            return;
                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                    }
                                } catch (SQLException e) {
                                    System.out.println("An error occurred: " + e.getMessage());
                                }
                                
                            }
                            break;
                           case 6: loggedIn=false;
                           break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
