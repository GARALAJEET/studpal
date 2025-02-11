public class college_Timetable {
    private int id;
    private String day;
    private String time;
    private String subject;
    private int numberOfLectures;

    // Constructors, getters, and setters
    public college_Timetable(int id, String day, String time, String subject, int numberOfLectures) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.numberOfLectures = numberOfLectures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumberOfLectures() {
        return numberOfLectures;
    }

    public void setNumberOfLectures(int numberOfLectures) {
        this.numberOfLectures = numberOfLectures;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Day: " + day + ", Time: " + time + ", Subject: " + subject + ", Lectures: " + numberOfLectures;
    }
}
