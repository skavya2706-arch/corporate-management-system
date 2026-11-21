package dao;
import database.DBConnection;
import model.Admin;
import model.Project;

import java.util.*;
import java.sql.*;
import java.io.*;

public class FinanceDAO {
    Connection con = DBConnection.getConnection();
    public void viewPendingProjects() {

        String query = "SELECT * FROM project WHERE budget_status='Pending'";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=========== PENDING PROJECTS ===========");
            Queue<Project> pendingProjects = new LinkedList<>();
            while (rs.next()) {

                Project project = new Project();

                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setClientName(rs.getString("client_name"));
                project.setBudgetRequired(rs.getDouble("budget_required"));

                pendingProjects.offer(project);

            }while (!pendingProjects.isEmpty()) {

                Project project = pendingProjects.poll();

                System.out.println("----------------------------------------");
                System.out.println("Project ID      : " + project.getProjectId());
                System.out.println("Project Name    : " + project.getProjectName());
                System.out.println("Client Name     : " + project.getClientName());
                System.out.println("Budget Required : " + project.getBudgetRequired());
                System.out.println("----------------------------------------");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean updateEmployeeSalary(int empId, double salary) {

        String query = "UPDATE employee SET salary=? WHERE emp_id=?";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, salary);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }


    public boolean approveBudget(int projectId) {

        String query = "UPDATE project SET budget_status='Approved', project_status='Planning' WHERE project_id=?";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, projectId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean rejectBudget(int projectId) {

        String query = "UPDATE project SET budget_status='Rejected', project_status='Rejected' WHERE project_id=?";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, projectId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public void companyTurnover() {

        String query = "SELECT project_id, project_name, client_name, budget_required FROM project WHERE budget_status='Approved'";

        double turnover = 0;
        int totalProjects = 0;

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=========== COMPANY TURNOVER ===========");

            while (rs.next()) {

                System.out.println("----------------------------------------");
                System.out.println("Project ID      : " + rs.getInt("project_id"));
                System.out.println("Project Name    : " + rs.getString("project_name"));
                System.out.println("Client Name     : " + rs.getString("client_name"));
                System.out.println("Approved Budget : ₹" + rs.getDouble("budget_required"));

                turnover += rs.getDouble("budget_required");
                totalProjects++;

            }

            System.out.println("========================================");
            System.out.println("Total Approved Projects : " + totalProjects);
            System.out.println("Total Company Turnover  : ₹" + turnover);
            System.out.println("========================================");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void viewEmployeeSalaries() {

        String query = "SELECT emp_id, emp_name, department, salary FROM employee";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=========== EMPLOYEE SALARIES ===========");

            while (rs.next()) {

                System.out.println("----------------------------------------");
                System.out.println("Employee ID : " + rs.getInt("emp_id"));
                System.out.println("Name        : " + rs.getString("emp_name"));
                System.out.println("Department  : " + rs.getString("department"));
                System.out.println("Salary      : ₹" + rs.getDouble("salary"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}