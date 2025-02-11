
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class TimetableService {
    private Connection connection;

    public TimetableService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studpal", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTimetableEntry(Timetable timetable) throws SQLException {
        String query = "INSERT INTO Timetable (username, event_title, dayOfWeek, location, description) VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, timetable.getUsername());
            stmt.setString(2, timetable.getevent_title());

            stmt.setString(3, timetable.getDayOfWeek());
            stmt.setString(4, timetable.getLocation());
            stmt.setString(5, timetable.getDescription());
            stmt.executeUpdate();
        }
    }

    public void updateTimetableEntry(Timetable timetable) throws SQLException {
        String query = "UPDATE Timetable SET event_title = ?, dayOfWeek = ?, location = ?, description = ? WHERE id = ? AND username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, timetable.getevent_title());

            stmt.setString(2, timetable.getDayOfWeek());
            stmt.setString(3, timetable.getLocation());
            stmt.setString(4, timetable.getDescription());
            stmt.setInt(5, timetable.getId());
            stmt.setString(6, timetable.getUsername());
            stmt.executeUpdate();
        }
    }

    public void deleteTimetableEntry(int id, String username) throws SQLException {
        String query = "DELETE FROM Timetable WHERE id = ? AND username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    public MyArrayList<Timetable> getTimetableByUser(String username) throws SQLException {
        MyArrayList<Timetable> timetableList = new MyArrayList<>();
        String query = "SELECT * FROM Timetable WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setId(rs.getInt("id"));
                timetable.setUsername(rs.getString("username"));
                timetable.setevent_title(rs.getString("event_title"));
                timetable.setDayOfWeek(rs.getString("dayOfWeek"));
                timetable.setLocation(rs.getString("location"));
                timetable.setDescription(rs.getString("description"));
                timetableList.addT(timetable);
            }
        }
        return timetableList;
    }

    public void exportTimetableToFile(String username, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            MyArrayList<Timetable> timetableList = getTimetableByUser(username);

            // Write the header
            writer.write("Timetable Report for User: " + username + "\n");
            writer.write("----------------------------------------------------------------------------------------\n");
            writer.write(String.format("%-10s %-20s %-15s %-20s %-30s\n",
                    "ID", "Event Title", "Day of Week", "Location", "Description"));
            writer.write("----------------------------------------------------------------------------------------\n");

            // Write the timetable entries
            for (int i = 0; i < timetableList.size(); i++) {
                Timetable entry = timetableList.get(i);
                writer.write(String.format("%-10d %-20s %-15s %-20s %-30s\n",
                        entry.getId(),
                        entry.getevent_title(),
                        entry.getDayOfWeek(),
                        entry.getLocation(),
                        entry.getDescription()));
            }

            writer.write("----------------------------------------------------------------------------------------\n");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error fetching timetable entries: " + e.getMessage());
        }
    }

    
  
    public static void addTimetable(String username) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TimetableDAO timetableDAO = new TimetableDAO();
        System.out.print("Enter day: ");
        String day = scanner.nextLine();
        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter number of lectures: ");
        int numberOfLectures = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        college_Timetable timetable = new college_Timetable(0, day, time, subject, numberOfLectures);
        timetableDAO.addTimetable(timetable,username);
        System.out.println("Timetable added successfully.");
    }
    static void updateTimetable() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TimetableDAO timetableDAO = new TimetableDAO();
        System.out.print("Enter timetable ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new day: ");
        String day = scanner.nextLine();
        System.out.print("Enter new time: ");
        String time = scanner.nextLine();
        System.out.print("Enter new subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter new number of lectures: ");
        int numberOfLectures = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        college_Timetable timetable = new college_Timetable(id, day, time, subject, numberOfLectures);
        timetableDAO.updateTimetable(timetable);
        System.out.println("Timetable updated successfully.");
    }
    public static void deleteTimetable() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TimetableDAO timetableDAO = new TimetableDAO();
        System.out.print("Enter timetable ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        timetableDAO.deleteTimetable(id);
        System.out.println("Timetable deleted successfully.");
    }
     public static void viewTimetables() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TimetableDAO timetableDAO = new TimetableDAO();
        
        MyArrayList<college_Timetable> timetables = timetableDAO.viewTimetables();
        if (timetables.size() == 0) {
            System.out.println("No timetables found.");
        } else {
            for (int i = 0; i < timetables.size(); i++) {
                System.out.println(timetables.get(i));
            }
            
        }
    }
     

   
}
