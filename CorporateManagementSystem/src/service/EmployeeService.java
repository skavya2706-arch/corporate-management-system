package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.EmployeeDAO;
import database.DBConnection;
import gui.LeaveApplicationForm;
import model.Employee;
import model.LeaveRequest;
import util.DatePicker;

import javax.swing.*;
import java.util.Date;
import java.util.Scanner;

public class EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private Scanner sc = new Scanner(System.in);

    public void addEmployee() {

        Employee employee = new Employee();

        int genderChoice;
        int departmentChoice;

        System.out.println("\n=========== ADD EMPLOYEE ===========");

        String name;

        while (true) {

            System.out.print("Enter Employee Name : ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println("Name cannot be empty.");

            } else if (!name.matches("[A-Za-z ]+")) {

                System.out.println("Name should contain only alphabets.");

            } else {

                employee.setEmpName(name);
                break;

            }

        }

        System.out.println("\nSelect Gender");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other");

        while (true) {

            System.out.print("Enter your choice : ");

            if (sc.hasNextInt()) {

                genderChoice = sc.nextInt();

                if (genderChoice >= 1 && genderChoice <= 3) {
                    break;
                }

                System.out.println("Please enter a choice between 1 and 3.");

            } else {

                System.out.println("Invalid input. Enter numbers only.");
                sc.next();

            }

        }

        switch (genderChoice) {

            case 1:
                employee.setGender("Male");
                break;

            case 2:
                employee.setGender("Female");
                break;

            case 3:
                employee.setGender("Other");
                break;

            default:
                System.out.println("Invalid Gender!");
                return;
        }

        System.out.println("\nSelect Department");
        System.out.println("1. HR");
        System.out.println("2. IT");
        System.out.println("3. Sales");
        System.out.println("4. Accounts");
        System.out.print("Enter Choice : ");
        while (true) {

            System.out.print("Enter your choice : ");

            if (sc.hasNextInt()) {

                departmentChoice = sc.nextInt();

                if (departmentChoice >= 1 && departmentChoice <= 4) {
                    break;
                }

                System.out.println("Please enter a choice between 1 and 4.");

            } else {

                System.out.println("Invalid input. Enter numbers only.");
                sc.next();

            }

        }

        switch (departmentChoice) {

            case 1:
                employee.setDepartment("HR");
                break;

            case 2:
                employee.setDepartment("IT");
                break;

            case 3:
                employee.setDepartment("Sales");
                break;

            case 4:
                employee.setDepartment("Accounts");
                break;

            default:
                System.out.println("Invalid Department!");
                return;
        }

        sc.nextLine();

        String phone;

        while (true) {

            System.out.print("Enter Phone Number : ");
            phone = sc.nextLine();

            if (phone.matches("\\d{10}")) {

                employee.setPhone(phone);
                break;

            }

            System.out.println("Phone number must contain exactly 10 digits.");

        }

        String email;

        while (true) {

            System.out.print("Enter Employee Email : ");
            email = sc.next();

            if (email.endsWith("@gmail.com")) {
                break;
            }

            System.out.println("Invalid Email! Email must end with @gmail.com");

        }

        employee.setEmail(email);

        double salary;

        while (true) {

            System.out.print("Enter Employee Salary : ");

            if (sc.hasNextDouble()) {

                salary = sc.nextDouble();

                if (salary > 0) {

                    employee.setSalary(salary);
                    break;

                } else {

                    System.out.println("Salary must be greater than 0.");

                }

            } else {

                System.out.println("Invalid input! Please enter a valid salary.");
                sc.next(); // Clear invalid input

            }

        }
        sc.nextLine();

        String password;

        while (true) {

            System.out.print("Enter Password : ");
            password = sc.nextLine();

            if (password.length() >= 6) {

                employee.setPassword(password);
                break;

            }

            System.out.println("Password must contain at least 6 characters.");

        }

        employee.setTotalLeaves(20);
        employee.setEmployeeStatus("Active");

        boolean result = employeeDAO.addEmployee(employee);

        if (result) {

            System.out.println("\nEmployee Added Successfully.");

        } else {

            System.out.println("\nFailed to Add Employee.");

        }

    }
    public void viewEmployees() {

        ArrayList<Employee> employeeList = employeeDAO.viewEmployees();

        if (employeeList.isEmpty()) {

            System.out.println("\nNo Employees Found.");
            return;

        }

        System.out.println("\n========================================================================================================");
        System.out.printf("%-5s %-20s %-10s %-12s %-15s %-25s %-10s %-10s%n",
                "ID", "Name", "Gender", "Department", "Phone", "Email", "Salary", "Status");
        System.out.println("========================================================================================================");

        for (Employee employee : employeeList) {

            System.out.printf("%-5d %-20s %-10s %-12s %-15s %-25s %-10.2f %-10s%n",
                    employee.getEmpId(),
                    employee.getEmpName(),
                    employee.getGender(),
                    employee.getDepartment(),
                    employee.getPhone(),
                    employee.getEmail(),
                    employee.getSalary(),
                    employee.getEmployeeStatus());

        }

        System.out.println("========================================================================================================");

    }
    public void searchEmployee() {

        System.out.print("\nEnter Employee ID : ");
        int empId = sc.nextInt();

        Employee employee = employeeDAO.searchEmployee(empId);

        if (employee == null) {

            System.out.println("\nEmployee Not Found.");
            return;

        }

        System.out.println("\n========== EMPLOYEE DETAILS ==========");

        System.out.println("Employee ID      : " + employee.getEmpId());
        System.out.println("Name             : " + employee.getEmpName());
        System.out.println("Gender           : " + employee.getGender());
        System.out.println("Department       : " + employee.getDepartment());
        System.out.println("Phone            : " + employee.getPhone());
        System.out.println("Email            : " + employee.getEmail());
        System.out.println("Salary           : " + employee.getSalary());
        System.out.println("Available Leaves : " + employee.getTotalLeaves());
        System.out.println("Total Leaves     : " + employee.getTotalLeaves());
        System.out.println("Status           : " + employee.getEmployeeStatus());

    }
    public void updateEmployee() {

        System.out.print("\nEnter Employee ID : ");
        int empId = sc.nextInt();
        sc.nextLine();

        Employee employee = employeeDAO.searchEmployee(empId);

        if (employee == null) {

            System.out.println("\nEmployee Not Found.");
            return;

        }

        System.out.println("\nCurrent Employee Details");


        System.out.println("Department      : " + employee.getDepartment());
        System.out.println("Status          : " + employee.getEmployeeStatus());

        System.out.println("\nEnter New Details");




        System.out.println("\nSelect Department");
        System.out.println("1. HR");
        System.out.println("2. IT");
        System.out.println("3. Sales");
        System.out.println("4. Accounts");

        int choice;

        while (true) {

            System.out.print("Enter your choice: ");

            if (sc.hasNextInt()) {

                choice = sc.nextInt();
                break;

            } else {

                System.out.println("Invalid input! Please enter numbers only.");
                sc.next(); // Discard invalid input

            }

        }
        switch (choice) {

            case 1:
                employee.setDepartment("HR");
                break;

            case 2:
                employee.setDepartment("IT");
                break;

            case 3:
                employee.setDepartment("Sales");
                break;

            case 4:
                employee.setDepartment("Accounts");
                break;

            default:
                System.out.println("Invalid Department!");
                return;

        }





        System.out.println("\nEmployee Status");
        System.out.println("1. Active");
        System.out.println("2. Inactive");
        System.out.print("Enter Choice : ");

        int Choice;

        while (true) {

            System.out.print("Enter your choice: ");

            if (sc.hasNextInt()) {

                Choice = sc.nextInt();
                break;

            } else {

                System.out.println("Invalid input! Please enter numbers only.");
                sc.next(); // Discard invalid input

            }

        }

        switch (Choice) {

            case 1:
                employee.setEmployeeStatus("Active");
                break;

            case 2:
                employee.setEmployeeStatus("Inactive");
                break;

            default:
                System.out.println("Invalid Status!");
                return;

        }



    }
    public void deleteEmployee() {

        System.out.print("\nEnter Employee ID : ");
        int empId = sc.nextInt();

        Employee employee = employeeDAO.searchEmployee(empId);

        if (employee == null) {

            System.out.println("\nEmployee Not Found.");
            return;

        }

        System.out.println("\nEmployee Found");
        System.out.println("ID         : " + employee.getEmpId());
        System.out.println("Name       : " + employee.getEmpName());
        System.out.println("Department : " + employee.getDepartment());

        System.out.print("\nAre you sure you want to delete this employee? (Y/N) : ");
        char choice = sc.next().charAt(0);

        if (choice == 'Y' || choice == 'y') {

            boolean result = employeeDAO.deleteEmployee(empId);

            if (result) {

                System.out.println("\nEmployee Deleted Successfully.");

            } else {

                System.out.println("\nFailed to Delete Employee.");

            }

        } else {

            System.out.println("\nDelete Operation Cancelled.");

        }

    }

    public void sortEmployees() {

        System.out.println("\n=========== SORT EMPLOYEES ===========");
        System.out.println("1. Sort by Employee ID");
        System.out.println("2. Sort by Employee Name");
        System.out.println("3. Back");

        System.out.print("\nEnter Choice : ");
        int choice = sc.nextInt();

        if (choice == 3) {
            return;
        }

        if (choice != 1 && choice != 2) {

            System.out.println("\nInvalid Choice.");
            return;

        }

        ArrayList<Employee> employeeList = employeeDAO.sortEmployees(choice);

        if (employeeList.isEmpty()) {

            System.out.println("\nNo Employees Found.");
            return;

        }

        System.out.println("\n========================================================================================================");
        System.out.printf("%-5s %-20s %-10s %-12s %-15s %-25s %-10s %-10s%n",
                "ID", "Name", "Gender", "Department", "Phone", "Email", "Salary", "Status");
        System.out.println("========================================================================================================");

        for (Employee employee : employeeList) {

            System.out.printf("%-5d %-20s %-10s %-12s %-15s %-25s %-10.2f %-10s%n",
                    employee.getEmpId(),
                    employee.getEmpName(),
                    employee.getGender(),
                    employee.getDepartment(),
                    employee.getPhone(),
                    employee.getEmail(),
                    employee.getSalary(),
                    employee.getEmployeeStatus());

        }

        System.out.println("========================================================================================================");

    }
    public void approveLeave() {

        ArrayList<LeaveRequest> leaveList = employeeDAO.getPendingLeaves();

        if (leaveList.isEmpty()) {

            System.out.println("\nNo Pending Leave Requests.");
            return;

        }

        System.out.println("\n================ PENDING LEAVE REQUESTS ================");

        System.out.printf("%-8s %-8s %-15s %-15s %-8s %-25s %-10s%n",
                "LeaveID", "EmpID", "From", "To", "Days", "Reason", "Status");

        System.out.println("===========================================================================");

        for (LeaveRequest leave : leaveList) {

            System.out.printf("%-8d %-8d %-15s %-15s %-8d %-25s %-10s%n",
                    leave.getLeaveId(),
                    leave.getEmpId(),
                    leave.getFromDate(),
                    leave.getToDate(),
                    leave.getLeaveDays(),
                    leave.getReason(),
                    leave.getLeaveStatus());

        }

        System.out.print("\nEnter Leave ID : ");
        int leaveId = sc.nextInt();

        LeaveRequest selectedLeave = null;

        for (LeaveRequest leave : leaveList) {

            if (leave.getLeaveId() == leaveId) {

                selectedLeave = leave;
                break;

            }

        }

        if (selectedLeave == null) {

            System.out.println("\nInvalid Leave ID.");
            return;

        }

        System.out.println("\n1. Approve");
        System.out.println("2. Reject");

        System.out.print("Enter Choice : ");
        int choice = sc.nextInt();

        boolean result = false;

        if (choice == 1) {

            result = employeeDAO.updateLeaveStatus(leaveId, "Approved");

            if (result) {

                employeeDAO.updateLeaveBalance(
                        selectedLeave.getEmpId(),
                        selectedLeave.getLeaveDays());

            }

        } else if (choice == 2) {

            result = employeeDAO.updateLeaveStatus(leaveId, "Rejected");
            if (choice == 2) {

                result = employeeDAO.updateLeaveStatus(leaveId, "Rejected");

            }

        } else {

            System.out.println("\nInvalid Choice.");
            return;

        }

        if (result) {

            System.out.println("\nLeave Request Updated Successfully.");

        } else {

            System.out.println("\nFailed to Update Leave Request.");

        }

    }
    public Employee employeeLogin(int empId, String password) {

        return employeeDAO.employeeLogin(empId, password);

    }
    public void viewMyProfile(int empId) {

        Employee employee = employeeDAO.searchEmployee(empId);

        if (employee == null) {

            System.out.println("\nEmployee Not Found.");
            return;

        }

        System.out.println("\n=========== MY PROFILE ===========");

        System.out.println("Employee ID    : " + employee.getEmpId());
        System.out.println("Name           : " + employee.getEmpName());
        System.out.println("Gender         : " + employee.getGender());
        System.out.println("Department     : " + employee.getDepartment());
        System.out.println("Phone          : " + employee.getPhone());
        System.out.println("Email          : " + employee.getEmail());
        System.out.println("Salary         : " + employee.getSalary());
        System.out.println("Available Leaves : " + employee.getTotalLeaves());
        System.out.println("Status         : " + employee.getEmployeeStatus());

    }
    public void applyLeave(int empId) {

        LeaveApplicationForm form = new LeaveApplicationForm(empId);

        form.setVisible(true);

    }
    public void viewLeaveStatus(int empId) {

        ArrayList<LeaveRequest> leaveList = employeeDAO.viewLeaveStatus(empId);

        if (leaveList.isEmpty()) {

            System.out.println("\nNo Leave Requests Found.");
            return;

        }

        System.out.println("\n===================== LEAVE STATUS =====================");

        System.out.printf("%-8s %-15s %-15s %-8s %-25s %-12s%n",
                "ID", "From", "To", "Days", "Reason", "Status");

        System.out.println("===========================================================================");

        for (LeaveRequest leave : leaveList) {

            System.out.printf("%-8d %-15s %-15s %-8d %-25s %-12s%n",
                    leave.getLeaveId(),
                    leave.getFromDate(),
                    leave.getToDate(),
                    leave.getLeaveDays(),
                    leave.getReason(),
                    leave.getLeaveStatus());

        }

    }
    public void changePassword(int empId) {

        System.out.print("\nEnter New Password : ");
        String newPassword = sc.next();

        boolean result = employeeDAO.changePassword(empId, newPassword);

        if (result) {

            System.out.println("\nPassword Changed Successfully.");

        } else {

            System.out.println("\nFailed to Change Password.");

        }

    }
    public void updateMyDetails(int empId) {

        while (true) {

            System.out.println("\n========== UPDATE MY DETAILS ==========");
            System.out.println("1. Update Name");
            System.out.println("2. Update Gender");
            System.out.println("3. Update Phone Number");
            System.out.println("4. Update Email");
            System.out.println("5. Back");

            int choice;

            while (true) {

                System.out.print("Enter your choice: ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 5)
                        break;

                    System.out.println("Please enter a number between 1 and 5.");

                } else {

                    System.out.println("Invalid input! Numbers only.");
                    sc.next();

                }

            }

            sc.nextLine();

            switch (choice) {

                case 1:

                    String name;

                    while (true) {

                        System.out.print("Enter New Name: ");
                        name = sc.nextLine();

                        if (name.matches("[A-Za-z ]+")) {
                            break;
                        }

                        System.out.println("Name should contain only alphabets.");

                    }

                    employeeDAO.updateEmployeeName(empId, name);

                    break;

                case 2:

                    String gender;

                    while (true) {

                        System.out.print("Enter Gender (Male/Female/Other): ");
                        gender = sc.nextLine();

                        if (gender.equalsIgnoreCase("Male") ||
                                gender.equalsIgnoreCase("Female") ||
                                gender.equalsIgnoreCase("Other")) {

                            break;

                        }

                        System.out.println("Invalid Gender.");

                    }

                    employeeDAO.updateEmployeeGender(empId, gender);

                    break;

                case 3:

                    String phone;

                    while (true) {

                        System.out.print("Enter Phone Number: ");
                        phone = sc.nextLine();

                        if (phone.matches("\\d{10}"))
                            break;

                        System.out.println("Phone number must contain exactly 10 digits.");

                    }

                    employeeDAO.updateEmployeePhone(empId, phone);

                    break;

                case 4:

                    String email;

                    while (true) {

                        System.out.print("Enter Email: ");
                        email = sc.nextLine();

                        if (email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$"))
                            break;

                        System.out.println("Enter a valid Gmail address.");

                    }

                    employeeDAO.updateEmployeeEmail(empId, email);

                    break;

                case 5:

                    return;

            }

        }

    }







}