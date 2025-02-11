

public class Timetable {
    private int timetable_id;
    private String username;
    private String subject;

    private String dayOfWeek;
    private String location;
    private String description;

    public Timetable(){
        
    }
    public Timetable(int id, String username, String subject,   String dayOfWeek, String location, String description) {
        timetable_id = id;
        this.username = username;
        this.subject = subject;
       
        this.dayOfWeek = dayOfWeek;
        this.location = location;
        this.description = description;
    }

 
    public int getId() {
        return timetable_id;
    }

    public void setId(int id) {
        timetable_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getevent_title() {
        return subject;
    }

    public void setevent_title(String subject) {
        this.subject = subject;
    }

   

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String toString() {
        return "Timetable{" +
                "id=" + timetable_id +
                ", username='" + username + '\'' +
                ",event title =" +  subject+
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
