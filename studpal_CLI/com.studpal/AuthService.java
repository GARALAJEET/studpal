import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AuthService {
    private Connection connection;

    public AuthService() {
        try {
            // Update with your database connection details
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studpal", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp(User user) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return false;
        }

        try {
            // Check if the username already exists
            String checkQuery = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Username already exists.");
                return false;
            }

            // Insert the new user
            String insertQuery = "INSERT INTO Users (username, password, email, fullName) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getPassword());
            insertStmt.setString(3, user.getEmail());
            insertStmt.setString(4, user.getFullName());
            insertStmt.executeUpdate();

            System.out.println("User signed up successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return false;
        }

        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateBasicDetails(User user) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }
    
        try {
            String updateQuery = "UPDATE Users SET email = ?, fullName = ?, age = ?, address = ?, phoneNumber = ?, panNumber = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFullName());
            stmt.setInt(3, user.getAge());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getpanNumber());
            stmt.setString(7, user.getUsername());
            stmt.executeUpdate();
    
            System.out.println("User details updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewBasicDetails(String username) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Full Name: " + rs.getString("fullName"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone Number: " + rs.getString("phoneNumber"));
                System.out.println("Pancard  Number: " + rs.getString("panNumber"));
            } else {
                System.out.println("No user found with the given username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidPanCardNumber(String panCardNumber) {

        String regex = "[A-Z]{5}[0-9]{4}[A-Z]";
        return panCardNumber.matches(regex);
    }


  
    public void addExpense(Expense expense) {
        if (doesUserExist(expense.getUsername())) {
            double currentBalance = getWalletBalance(expense.getUsername()); // Make sure this gets the `currentBalance`
            double expenseAmount = expense.getAmount();
            
            if (currentBalance >= expenseAmount) {
                // Deduct expense amount from current balance
                boolean fundsDeducted = spendFunds(expense.getUsername(), expenseAmount);
                
                if (fundsDeducted) {
                    String query = "INSERT INTO expenses (username, amount, category, date, description) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, expense.getUsername());
                        preparedStatement.setDouble(2, expense.getAmount());
                        preparedStatement.setString(3, expense.getCategory());
                        preparedStatement.setDate(4, Date.valueOf(expense.getDate())); // Ensure date is in correct format
                        preparedStatement.setString(5, expense.getDescription());
                        preparedStatement.executeUpdate();
                        System.out.println("Expense added successfully.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error: Unable to deduct funds from wallet.");
                }
            } else {
                System.out.println("Error: Insufficient funds in wallet.");
            }
        } else {
            System.out.println("Error: Username does not exist.");
        }
    }
    

   
 
    public MyArrayList<Expense> getAllExpenses(String username) {
        MyArrayList<Expense> expenses = new MyArrayList<>();
        // String query = "SELECT * FROM expenses WHERE username = ?";
        String query = "SELECT * FROM expenses WHERE username = ? ORDER BY date DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Expense expense = new Expense(
                        resultSet.getString("username"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getDate("date").toString(),
                        resultSet.getString("description"));
                expense.setId(resultSet.getInt("id"));
                expenses.addE(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return expenses;
    }

  
    public MyArrayList<Expense> getExpensesByCategory(String username, String category) {
        MyArrayList<Expense> expenses = new MyArrayList<>();
        String query = "SELECT * FROM expenses WHERE username = ? AND category = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Expense expense = new Expense(
                        resultSet.getString("username"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getDate("date").toString(),
                        resultSet.getString("description"));
                expense.setId(resultSet.getInt("id"));
                expenses.addE(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public boolean doesUserExist(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if the user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

 
    public double getWalletBalance(String username) {
        double balance = 0.0;
        String query = "SELECT currentBalance FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("currentBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }


    public void addFunds(String username, double amount) {
        String query = "UPDATE Users SET currentBalance = currentBalance + ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            System.out.println("Funds added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
   
    public boolean spendFunds(String username, double amount) {
        double currentBalance = getWalletBalance(username);
        if (currentBalance >= amount) {
            String query = "UPDATE Users SET currentBalance = currentBalance - ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
                System.out.println("Funds spent successfully.");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Insufficient funds.");
        }
        return false;
    }
    public void printExpensesToFile(String username) {
        MyArrayList<Expense> expenses = getAllExpenses(username);
        double currentBalance = getWalletBalance(username);
    
        String fileName = username + "_expenses.txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Expense Report for User: " + username);
            writer.newLine();
            writer.write("-------------------------------------------------------------");
            writer.newLine();
            writer.write(String.format("%-10s %-10s %-15s %-15s %-30s", "ID", "Amount", "Category", "Date", "Description"));
            writer.newLine();
            writer.write("-------------------------------------------------------------");
            writer.newLine();
    
            for (int i = 0; i < expenses.size(); i++) {  // Use index-based loop
                Expense expense = expenses.get(i);  // Assuming MyArrayList has a get() method
                writer.write(String.format("%-10d %-10.2f %-15s %-15s %-30s",
                        expense.getId(), expense.getAmount(), expense.getCategory(), expense.getDate().toString(), expense.getDescription()));
                writer.newLine();
            }
    
            writer.write("-------------------------------------------------------------");
            writer.newLine();
            writer.write("Current Balance: " + currentBalance);
            writer.newLine();
    
            System.out.println("Expenses and current balance have been successfully written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    
}

