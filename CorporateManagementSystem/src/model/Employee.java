package model;

public class Employee {

    private int empId;
    private String empName;
    private String gender;
    private String department;
    private String phone;
    private String email;
    private double salary;
    private int totalLeaves;
    private String employeeStatus;
    private String password;

    // Default Constructor
    public Employee() {

    }

    // Parameterized Constructor
    public Employee(int empId, String empName, String gender, String department,
                    String phone, String email, double salary,
             int totalLeaves,
                    String employeeStatus, String password) {

        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
       // this.pendingLeaves = pendingLeaves;
        this.totalLeaves = totalLeaves;
        this.employeeStatus = employeeStatus;
        this.password = password;
    }

    // Getters

    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

   /* public int getPendingLeaves() {
        return pendingLeaves;
    }*/

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

  /*  public void setPendingLeaves(int pendingLeaves) {
        this.pendingLeaves = pendingLeaves;
    }*/

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                //", pendingLeaves=" + pendingLeaves +
                ", totalLeaves=" + totalLeaves +
                ", employeeStatus='" + employeeStatus + '\'' +
                '}';
    }
}