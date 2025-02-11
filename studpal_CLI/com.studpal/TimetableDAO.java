
import java.sql.*;

public class TimetableDAO {
    private static final String TABLE_NAME = "college_timetable";

    public void addTimetable(college_Timetable timetable,String username) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (day, time, subject, number_of_lectures,username) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, timetable.getDay());
            stmt.setString(2, timetable.getTime());
            stmt.setString(3, timetable.getSubject());
            stmt.setInt(4, timetable.getNumberOfLectures());
            stmt.setString(5, username);
            stmt.executeUpdate();
        }
    }

    public void updateTimetable(college_Timetable timetable) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET day = ?, time = ?, subject = ?, number_of_lectures = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, timetable.getDay());
            stmt.setString(2, timetable.getTime());
            stmt.setString(3, timetable.getSubject());
            stmt.setInt(4, timetable.getNumberOfLectures());
            stmt.setInt(5, timetable.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteTimetable(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public MyArrayList<college_Timetable> viewTimetables() throws SQLException {
        MyArrayList<college_Timetable> timetables = new MyArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                college_Timetable timetable = new college_Timetable(
                        rs.getInt("id"),
                        rs.getString("day"),
                        rs.getString("time"),
                        rs.getString("subject"),
                        rs.getInt("number_of_lectures")
                );
                timetables.add(timetable);
            }
        }
        return timetables ;
    }
 
}
