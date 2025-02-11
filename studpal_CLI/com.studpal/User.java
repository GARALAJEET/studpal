public class User {
    private String username;
    private String password;
    private String email; 
    private String fullName; 
    private int age; 
    private String address; 
    private String phoneNumber; 
    private long addharNumber;
    private String panNumber;
    private double walletBalance; 
    
    

    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.walletBalance = 0.0;
    }

 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getpanNumber() {
        return panNumber;
    }

    public void setpanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
    public long getaddharNumber() {
        return addharNumber;
    }

    public void setaddharNumber(long  addharNumber) {
        this.addharNumber =  addharNumber;
    }
    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
