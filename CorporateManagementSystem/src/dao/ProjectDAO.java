package dao;

import database.DBConnection;

import model.Project;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.*;


public class ProjectDAO {

    Connection con = DBConnection.getConnection();

    public boolean addProject(Project project) {

        String query = "INSERT INTO project(project_name, client_name, description, start_date, end_date, budget_required, budget_status, project_status, project_head, assigned_employees) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getClientName());
            ps.setString(3, project.getDescription());
            ps.setDate(4, project.getStartDate());
            ps.setDate(5, project.getEndDate());
            ps.setDouble(6, project.getBudgetRequired());
            ps.setString(7, project.getBudgetStatus());
            ps.setString(8, project.getProjectStatus());
            ps.setInt(9, project.getProjectHead());
            ps.setString(10, project.getAssignedEmployees());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public void viewProjects() {

        String query = "SELECT * FROM project ";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            PriorityQueue<Project> projectQueue = new PriorityQueue<>(new Comparator<Project>() {

                @Override
                public int compare(Project p1, Project p2) {

                    return Integer.compare(p1.getPriority(), p2.getPriority());

                }

            });
            System.out.println("\n==================== PROJECT LIST ====================");

            while (rs.next()) {

                Project project = new Project();

                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setClientName(rs.getString("client_name"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                project.setBudgetRequired(rs.getDouble("budget_required"));
                project.setBudgetStatus(rs.getString("budget_status"));
                project.setProjectStatus(rs.getString("project_status"));
                project.setProjectHead(rs.getInt("project_head"));
                project.setAssignedEmployees(rs.getString("assigned_employees"));

                String status = project.getProjectStatus();

                if (status == null) {
                    status = "Pending";
                }

                switch (status) {

                    case "Testing":
                        project.setPriority(1);
                        break;

                    case "Development":
                        project.setPriority(2);
                        break;

                    case "Planning":
                        project.setPriority(3);
                        break;

                    case "Completed":
                        project.setPriority(4);
                        break;

                    default:
                        project.setPriority(5);

                }

                projectQueue.offer(project);

            }
            while (!projectQueue.isEmpty()) {

                Project project = projectQueue.poll();

                String priority;

                switch (project.getPriority()) {

                    case 1:
                        priority = "High";
                        break;

                    case 2:
                        priority = "Medium";
                        break;

                    case 3:
                        priority = "Low";
                        break;

                    case 4:
                        priority = "Completed";
                        break;

                    default:
                        priority = "-";

                }

                System.out.println("------------------------------------------------------");
                System.out.println("Project ID          : " + project.getProjectId());
                System.out.println("Project Name        : " + project.getProjectName());
                System.out.println("Client Name         : " + project.getClientName());
                System.out.println("Description         : " + project.getDescription());
                System.out.println("Start Date          : " + project.getStartDate());
                System.out.println("End Date            : " + project.getEndDate());
                System.out.println("Budget Required     : " + project.getBudgetRequired());
                System.out.println("Budget Status       : " + project.getBudgetStatus());
                System.out.println("Project Status      : " + project.getProjectStatus());
                System.out.println("Priority            : " + priority);
                System.out.println("Project Head ID     : " + project.getProjectHead());
                System.out.println("Assigned Employees  : " + project.getAssignedEmployees());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void searchProject(int projectId) {

        String query = "SELECT * FROM project\n" +
                "WHERE project_id=? AND budget_status='Approved'";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n=========== PROJECT DETAILS ===========");

                System.out.println("Project ID          : " + rs.getInt("project_id"));
                System.out.println("Project Name        : " + rs.getString("project_name"));
                System.out.println("Client Name         : " + rs.getString("client_name"));
                System.out.println("Description         : " + rs.getString("description"));
                System.out.println("Start Date          : " + rs.getDate("start_date"));
                System.out.println("End Date            : " + rs.getDate("end_date"));
                System.out.println("Budget Required     : " + rs.getDouble("budget_required"));
                System.out.println("Budget Status       : " + rs.getString("budget_status"));
                System.out.println("Project Status      : " + rs.getString("project_status"));
                System.out.println("Project Head ID     : " + rs.getInt("project_head"));
                System.out.println("Assigned Employees  : " + rs.getString("assigned_employees"));

            } else {

                System.out.println("\nProject Not Found.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean updateProject(Project project) {

        String query = "UPDATE project\n" +
                "SET client_name=?,\n" +
                "description=?,\n" +
                "end_date=?,\n" +
                "assigned_employees=?,\n" +
                "project_status=?\n" +
                "WHERE project_id=?\n" +
                "AND budget_status='Approved'";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, project.getClientName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, project.getEndDate());
            ps.setString(4, project.getAssignedEmployees());
            ps.setString(5, project.getProjectStatus());
            ps.setInt(6, project.getProjectId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public void addToProjectHistory(int projectId) {

        try {

            String query = "SELECT project_id, project_name, client_name, budget_required FROM project WHERE project_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                query = "INSERT INTO project_history(project_id, project_name, client_name, budget_required, completion_date) VALUES(?,?,?,?,?)";

                ps = con.prepareStatement(query);

                ps.setInt(1, rs.getInt("project_id"));
                ps.setString(2, rs.getString("project_name"));
                ps.setString(3, rs.getString("client_name"));
                ps.setDouble(4, rs.getDouble("budget_required"));
                ps.setDate(5, new Date(System.currentTimeMillis()));

                ps.executeUpdate();

            }

            query = "SELECT COUNT(*) FROM project_history";

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 3) {

                query = "DELETE FROM project_history ORDER BY history_id ASC LIMIT 1";

                ps = con.prepareStatement(query);

                ps.executeUpdate();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void viewProjectHistory() {

        Stack<Project> historyStack = new Stack<>();

        try {

            String query = "SELECT * FROM project_history ORDER BY history_id ASC";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Project project = new Project();

                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setClientName(rs.getString("client_name"));
                project.setBudgetRequired(rs.getDouble("budget_required"));

                historyStack.push(project);

            }

            System.out.println("\n=========== PROJECT HISTORY ===========");

            while (!historyStack.isEmpty()) {

                Project project = historyStack.pop();

                System.out.println("----------------------------------------");
                System.out.println("Project ID      : " + project.getProjectId());
                System.out.println("Project Name    : " + project.getProjectName());
                System.out.println("Client Name     : " + project.getClientName());
                System.out.println("Budget Required : " + project.getBudgetRequired());
                System.out.println("----------------------------------------");

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }
}