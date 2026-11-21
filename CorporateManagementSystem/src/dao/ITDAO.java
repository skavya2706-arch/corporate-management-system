package dao;
import database.DBConnection;
import model.PasswordResetRequest;
import java.sql.*;
import java.util.*;
import model.TreeNode;
import java.util.HashMap;
import java.util.Map;
import model.PasswordResetRequest;

import java.sql.PreparedStatement;

public class ITDAO {
    Connection con = DBConnection.getConnection();
    public boolean raisePasswordResetRequest(PasswordResetRequest request) {

        try {

            String query = "SELECT * FROM password_reset_request WHERE emp_id=? AND request_status='Pending'";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, request.getEmpId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nA Password Reset Request is already pending.");

                return false;

            }

            query = "INSERT INTO password_reset_request(emp_id, request_date, request_status) VALUES(?,?,?)";

            ps = con.prepareStatement(query);

            ps.setInt(1, request.getEmpId());
            ps.setDate(2, request.getRequestDate());
            ps.setString(3, request.getRequestStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
    public void viewPendingPasswordRequests() {

        String query = "SELECT * FROM password_reset_request";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n=========== PENDING PASSWORD RESET REQUESTS ===========");

            while (rs.next()) {

                System.out.println("----------------------------------------");
                System.out.println("Request ID    : " + rs.getInt("request_id"));
                System.out.println("Employee ID   : " + rs.getInt("emp_id"));
                System.out.println("Employee Name : " + rs.getString("emp_name"));
                System.out.println("Department    : " + rs.getString("department"));
                System.out.println("Request Date  : " + rs.getDate("request_date"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public boolean processPasswordReset(int requestId, String newPassword) {

        try {

            String query = "SELECT emp_id FROM password_reset_request WHERE request_id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, requestId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {

                return false;

            }

            int empId = rs.getInt("emp_id");

            query = "UPDATE employee SET password=? WHERE emp_id=?";

            ps = con.prepareStatement(query);

            ps.setString(1, newPassword);
            ps.setInt(2, empId);

            ps.executeUpdate();

            query = "UPDATE password_reset_request SET request_status='Completed' WHERE request_id=?";

            ps = con.prepareStatement(query);

            ps.setInt(1, requestId);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
    public void systemInformation() {

        HashMap<String, Integer> systemInfo = new HashMap<>();

        try {

            PreparedStatement ps;
            ResultSet rs;

            ps = con.prepareStatement("SELECT COUNT(*) FROM employee");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Total Employees", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM department");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Total Departments", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM project");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Total Projects", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM project WHERE budget_status='Approved'");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Approved Projects", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM project WHERE budget_status='Pending'");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Pending Projects", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM project WHERE budget_status='Rejected'");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Rejected Projects", rs.getInt(1));

            ps = con.prepareStatement("SELECT COUNT(*) FROM tender");
            rs = ps.executeQuery();
            if (rs.next())
                systemInfo.put("Total Tenders", rs.getInt(1));

            System.out.println("\n=========== SYSTEM INFORMATION ===========");

            System.out.println("Database Name : Corporate Management System");

            System.out.println();

            for (String key : systemInfo.keySet()) {

                System.out.printf("%-25s : %d%n", key, systemInfo.get(key));

            }

            System.out.println();

            System.out.println("Database Status : Connected");
            System.out.println("System Status   : Running");

            System.out.println("==========================================");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void companyHierarchy() {

        TreeNode company = new TreeNode("Corporate Management System");

        HashMap<String, TreeNode> departments = new HashMap<>();

        try {

            String query = "SELECT emp_name, department FROM employee ORDER BY department, emp_name";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String department = rs.getString("department");
                String employeeName = rs.getString("emp_name");

                if (!departments.containsKey(department)) {

                    TreeNode departmentNode = new TreeNode(department);

                    departments.put(department, departmentNode);

                    company.addChild(departmentNode);

                }

                TreeNode employeeNode = new TreeNode(employeeName);

                departments.get(department).addChild(employeeNode);

            }

            System.out.println("\n=========== COMPANY HIERARCHY ===========");

            printTree(company, 0);

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }private void printTree(TreeNode node, int level) {

        for (int i = 0; i < level; i++) {

            System.out.print("   ");

        }

        System.out.println("|-- " + node.getData());

        for (TreeNode child : node.getChildren()) {

            printTree(child, level + 1);

        }

    }
}
