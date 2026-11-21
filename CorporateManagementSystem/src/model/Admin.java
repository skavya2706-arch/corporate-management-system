package model;

public class Admin {

    private int adminId;
    private String username;
    private String password;
    private String role;
    private int empId;

    // Default Constructor
    public Admin() {

    }

    // Parameterized Constructor
    public Admin(int adminId, String username, String password, String role, int empId) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.empId = empId;
    }

    // Getters

    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getEmpId() {
        return empId;
    }

    // Setters

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", empId=" + empId +
                '}';
    }
}