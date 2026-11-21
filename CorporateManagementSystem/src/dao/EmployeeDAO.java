package dao;

import database.DBConnection;
import model.Employee;
import model.LeaveRequest;
import model.LeaveRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
public class EmployeeDAO {

    public boolean addEmployee(Employee employee) {

        String query = "INSERT INTO employee(emp_name, gender, department, phone, email, salary,total_leaves, employee_status, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, employee.getEmpName());
            preparedStatement.setString(2, employee.getGender());
            preparedStatement.setString(3, employee.getDepartment());
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setDouble(6, employee.getSalary());
            //  preparedStatement.setInt(7, employee.getPendingLeaves());
            preparedStatement.setInt(7, employee.getTotalLeaves());
            preparedStatement.setString(8, employee.getEmployeeStatus());
            preparedStatement.setString(9, employee.getPassword());

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    public static ArrayList<Employee> viewEmployees() {

        ArrayList<Employee> employeeList = new ArrayList<>();

        String query = "SELECT * FROM employee";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Employee employee = new Employee();

                employee.setEmpId(resultSet.getInt("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                // employee.setPendingLeaves(resultSet.getInt("pending_leaves"));
                employee.setTotalLeaves(resultSet.getInt("total_leaves"));
                employee.setEmployeeStatus(resultSet.getString("employee_status"));
                employee.setPassword(resultSet.getString("password"));

                employeeList.add(employee);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return employeeList;

    }

    public static Employee searchEmployee(int empId) {

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, empId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Employee employee = new Employee();

                employee.setEmpId(resultSet.getInt("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                // employee.setPendingLeaves(resultSet.getInt("pending_leaves"));
                employee.setTotalLeaves(resultSet.getInt("total_leaves"));
                employee.setEmployeeStatus(resultSet.getString("employee_status"));
                employee.setPassword(resultSet.getString("password"));

                return employee;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    public boolean updateEmployee(Employee employee) {

        String query = "UPDATE employee SET emp_name=?, gender=?, department=?, phone=?, email=?, salary=?, employee_status=?, password=? WHERE emp_id=?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, employee.getDepartment());
            preparedStatement.setString(2, employee.getEmployeeStatus());
            preparedStatement.setInt(3, employee.getEmpId());

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    public boolean deleteEmployee(int empId) {

        Connection connection = null;

        try {

            connection = DBConnection.getConnection();

            /*
             * 1. Remove employee reference from ADMIN
             */
            String deleteAdmin =
                    "DELETE FROM admin WHERE emp_id = ?";

            PreparedStatement adminStatement =
                    connection.prepareStatement(deleteAdmin);

            adminStatement.setInt(1, empId);

            adminStatement.executeUpdate();


            /*
             * 2. Remove employee reference from DEPARTMENT
             *    because department_head references employee.emp_id
             */
            String updateDepartment =
                    "UPDATE department " +
                            "SET department_head = NULL " +
                            "WHERE department_head = ?";

            PreparedStatement departmentStatement =
                    connection.prepareStatement(updateDepartment);

            departmentStatement.setInt(1, empId);

            departmentStatement.executeUpdate();


            /*
             * 3. Remove employee reference from PROJECT
             *    because project_head references employee.emp_id
             */
            String updateProject =
                    "UPDATE project " +
                            "SET project_head = NULL " +
                            "WHERE project_head = ?";

            PreparedStatement projectStatement =
                    connection.prepareStatement(updateProject);

            projectStatement.setInt(1, empId);

            projectStatement.executeUpdate();


            /*
             * 4. Now delete the employee
             */
            String deleteEmployee =
                    "DELETE FROM employee WHERE emp_id = ?";

            PreparedStatement employeeStatement =
                    connection.prepareStatement(deleteEmployee);

            employeeStatement.setInt(1, empId);

            int rows =
                    employeeStatement.executeUpdate();


            /*
             * 5. Return whether employee was deleted
             */
            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting employee: " + e.getMessage()
            );

            e.printStackTrace();

        }

        return false;
    }
    public ArrayList<Employee> sortEmployees(int choice) {

        ArrayList<Employee> employeeList = new ArrayList<>();

        String query;

        if (choice == 1) {

            query = "SELECT * FROM employee ORDER BY emp_id";

        } else {

            query = "SELECT * FROM employee ORDER BY emp_name";

        }

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Employee employee = new Employee();

                employee.setEmpId(resultSet.getInt("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                //employee.setPendingLeaves(resultSet.getInt("pending_leaves"));
                employee.setTotalLeaves(resultSet.getInt("total_leaves"));
                employee.setEmployeeStatus(resultSet.getString("employee_status"));
                employee.setPassword(resultSet.getString("password"));

                employeeList.add(employee);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return employeeList;

    }

    public ArrayList<LeaveRequest> getPendingLeaves() {

        ArrayList<LeaveRequest> leaveList = new ArrayList<>();

        String query = "SELECT * FROM leave_request WHERE leave_status='Pending'";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                LeaveRequest leave = new LeaveRequest();

                leave.setLeaveId(resultSet.getInt("leave_id"));
                leave.setEmpId(resultSet.getInt("emp_id"));
                leave.setFromDate(resultSet.getDate("from_date"));
                leave.setToDate(resultSet.getDate("to_date"));
                leave.setLeaveDays(resultSet.getInt("leave_days"));
                leave.setReason(resultSet.getString("reason"));
                leave.setLeaveStatus(resultSet.getString("leave_status"));

                leaveList.add(leave);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return leaveList;

    }

    public boolean updateLeaveStatus(int leaveId, String status) {

        String query = "UPDATE leave_request SET leave_status = ? WHERE leave_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, leaveId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public Employee employeeLogin(int empId, String password) {

        String query = "SELECT * FROM employee WHERE emp_id = ? AND password = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, empId);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Employee employee = new Employee();

                employee.setEmpId(resultSet.getInt("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setEmployeeStatus(resultSet.getString("employee_status"));

                return employee;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public boolean applyLeave(LeaveRequest leaveRequest) {

        String query = "INSERT INTO leave_request(emp_id, from_date, to_date, leave_days, reason) VALUES (?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            // Validation: Maximum 20 leave days allowed
            if (leaveRequest.getLeaveDays() > 20) {

                System.out.println("\nMaximum leave allowed is 20 days.");
                System.out.println("Leave application rejected.");

                return false;

            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, leaveRequest.getEmpId());
            preparedStatement.setDate(2, leaveRequest.getFromDate());
            preparedStatement.setDate(3, leaveRequest.getToDate());
            preparedStatement.setInt(4, leaveRequest.getLeaveDays());
            preparedStatement.setString(5, leaveRequest.getReason());

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean updateLeaveBalance(int empId, int leaveDays) {

        String query = "UPDATE employee SET total_leaves = total_leaves - ? WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, leaveDays);
            preparedStatement.setInt(2, empId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public ArrayList<LeaveRequest> viewLeaveStatus(int empId) {

        ArrayList<LeaveRequest> leaveList = new ArrayList<>();

        String query = "SELECT * FROM leave_request WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, empId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                LeaveRequest leave = new LeaveRequest();

                leave.setLeaveId(resultSet.getInt("leave_id"));
                leave.setEmpId(resultSet.getInt("emp_id"));
                leave.setFromDate(resultSet.getDate("from_date"));
                leave.setToDate(resultSet.getDate("to_date"));
                leave.setLeaveDays(resultSet.getInt("leave_days"));
                leave.setReason(resultSet.getString("reason"));
                leave.setLeaveStatus(resultSet.getString("leave_status"));

                leaveList.add(leave);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return leaveList;

    }

    public boolean changePassword(int empId, String newPassword) {

        try {

            Connection connection = DBConnection.getConnection();

            // Update Employee Table
            String employeeQuery = "UPDATE employee SET password=? WHERE emp_id=?";

            PreparedStatement ps1 = connection.prepareStatement(employeeQuery);

            ps1.setString(1, newPassword);
            ps1.setInt(2, empId);

            int rows = ps1.executeUpdate();

            // Update Admin Table (if the employee is an admin)
            String adminQuery = "UPDATE admin SET password=? WHERE emp_id=?";

            PreparedStatement ps2 = connection.prepareStatement(adminQuery);

            ps2.setString(1, newPassword);
            ps2.setInt(2, empId);

            ps2.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean updateEmployeeName(int empId, String name) {

        String query = "UPDATE employee SET emp_name = ? WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, empId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }


    public boolean updateEmployeeGender(int empId, String gender) {

        String query = "UPDATE employee SET gender = ? WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, gender);
            preparedStatement.setInt(2, empId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }


    public boolean updateEmployeePhone(int empId, String phone) {

        String query = "UPDATE employee SET phone = ? WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, empId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }


    public boolean updateEmployeeEmail(int empId, String email) {

        String query = "UPDATE employee SET email = ? WHERE emp_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, empId);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    public int getApprovedLeaveDays(int empId) {

        String query = "SELECT COALESCE(SUM(leave_days), 0) " +
                "FROM leave_request " +
                "WHERE emp_id = ? " +
                "AND LOWER(leave_status) IN ('approved', 'pending')";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setInt(1, empId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(1);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return 0;
    }
}