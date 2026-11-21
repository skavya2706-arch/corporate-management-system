package service;
import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Employee;
import model.Project;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectService {

    ProjectDAO projectDAO = new ProjectDAO();
    Scanner sc = new Scanner(System.in);


    public void addProject() {

        Project project = new Project();
        project.setProjectStatus("Pending");

        System.out.println("\n=========== ADD PROJECT ===========");

        // ---------------- Project Name ----------------

        while (true) {

            System.out.print("Enter Project Name : ");
            String projectName = sc.nextLine().trim();

            if (projectName.isEmpty()) {

                System.out.println("Project Name cannot be empty.");

            } else if (!projectName.matches("[A-Za-z0-9 ]+")) {

                System.out.println("Project Name can contain only letters, numbers and spaces.");

            } else {

                project.setProjectName(projectName);
                break;

            }

        }

        // ---------------- Client Name ----------------

        while (true) {

            System.out.print("Enter Client Name : ");
            String clientName = sc.nextLine().trim();

            if (clientName.isEmpty()) {

                System.out.println("Client Name cannot be empty.");

            } else if (!clientName.matches("[A-Za-z ]+")) {

                System.out.println("Client Name should contain only alphabets.");

            } else {

                project.setClientName(clientName);
                break;

            }

        }

        // ---------------- Description ----------------

        System.out.print("Enter Description : ");
        project.setDescription(sc.nextLine());

        // ---------------- Start Date ----------------

        Date currentDate = new Date(System.currentTimeMillis());

        Date startDate;

        while (true) {

            try {

                System.out.print("Enter Start Date (yyyy-mm-dd) : ");
                startDate = Date.valueOf(sc.nextLine());

                if (!startDate.after(currentDate)) {

                    System.out.println(
                            "Start Date must be greater than today's date."
                    );

                    continue;
                }

                break;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid Date Format. Use yyyy-mm-dd."
                );

            }
        }

        project.setStartDate(startDate);

// ---------------- End Date ----------------

        Date endDate;

        while (true) {

            try {

                System.out.print("Enter End Date (yyyy-mm-dd) : ");
                endDate = Date.valueOf(sc.nextLine());

                if (!endDate.after(startDate)) {

                    System.out.println(
                            "Invalid! End Date must be after Start Date."
                    );

                    continue;
                }

                break;

            } catch (IllegalArgumentException e) {

                System.out.println("Invalid Date Format. Use yyyy-mm-dd.");

            }
        }

        project.setEndDate(endDate);

        // ---------------- Budget ----------------

        while (true) {

            System.out.print("Enter Budget Required : ");

            if (sc.hasNextDouble()) {

                double budget = sc.nextDouble();
                sc.nextLine();

                if (budget > 0) {

                    project.setBudgetRequired(budget);
                    break;

                }

                System.out.println("Budget must be greater than 0.");

            } else {

                System.out.println("Invalid Budget.");
                sc.next();

            }

        }
// ---------------- Project Head ----------------

        ArrayList<Employee> employeeList = EmployeeDAO.viewEmployees();

        while (true) {

            System.out.print("Enter Project Head ID : ");

            if (sc.hasNextInt()) {

                int headId = sc.nextInt();
                sc.nextLine();

                boolean employeeExists = false;

                for (Employee employee : employeeList) {

                    if (employee.getEmpId() == headId) {

                        employeeExists = true;
                        break;

                    }

                }

                if (employeeExists) {

                    project.setProjectHead(headId);
                    break;

                } else {

                    System.out.println(
                            "Invalid Project Head ID. Employee does not exist."
                    );

                }

            } else {

                System.out.println("Enter numbers only.");
                sc.next();

            }

        }

        // ---------------- Available Employees ----------------


        System.out.println("\n=========== AVAILABLE EMPLOYEES ===========");

        System.out.printf("%-5s %-25s %-15s%n",
                "ID", "Name", "Department");

        System.out.println("-----------------------------------------------------");

        for (Employee employee : employeeList) {

            System.out.printf("%-5d %-25s %-15s%n",
                    employee.getEmpId(),
                    employee.getEmpName(),
                    employee.getDepartment());

        }

        System.out.println("-----------------------------------------------------");

        // ---------------- Assigned Employees ----------------

        while (true) {

            System.out.print("Enter Assigned Employee IDs (comma separated, add minimum 2) : ");

            String assignedEmployees = sc.nextLine().trim();

            if (!assignedEmployees.isEmpty()) {

                project.setAssignedEmployees(assignedEmployees);
                break;

            }

            System.out.println("Assigned Employee IDs cannot be empty.");

        }

        // ---------------- Save Project ----------------

        boolean result = projectDAO.addProject(project);

        if (result) {

            System.out.println("\nProject Added Successfully.");

        } else {

            System.out.println("\nFailed to Add Project.");

        }

    }
    public void viewProjects() {

        System.out.println("\n=========== VIEW PROJECTS ===========");

        projectDAO.viewProjects();

    }
    public void searchProject() {

        System.out.println("\n=========== SEARCH PROJECT ===========");

        System.out.print("Enter Project ID : ");
        int projectId = sc.nextInt();

        projectDAO.searchProject(projectId);

    }
    public void updateProject() {

        Project project = new Project();

        System.out.println("\n=========== UPDATE PROJECT ===========");

        // ---------------- Project ID ----------------

        while (true) {

            System.out.print("Enter Project ID : ");

            if (sc.hasNextInt()) {

                int projectId = sc.nextInt();

                if (projectId > 0) {

                    project.setProjectId(projectId);
                    sc.nextLine();
                    break;

                }

                System.out.println("Project ID must be greater than 0.");

            } else {

                System.out.println("Invalid Project ID.");
                sc.next();
            }

        }

        // ---------------- Client Name ----------------

        while (true) {

            System.out.print("Enter New Client Name : ");

            String clientName = sc.nextLine().trim();

            if (clientName.isEmpty()) {

                System.out.println("Client Name cannot be empty.");

            } else if (!clientName.matches("[A-Za-z ]+")) {

                System.out.println("Client Name should contain only alphabets.");

            } else {

                project.setClientName(clientName);
                break;

            }

        }

        // ---------------- Description ----------------

        while (true) {

            System.out.print("Enter New Description : ");

            String description = sc.nextLine().trim();

            if (description.isEmpty()) {

                System.out.println("Description cannot be empty.");

            } else {

                project.setDescription(description);
                break;

            }

        }

        // ---------------- End Date ----------------

        Date currentDate = new Date(System.currentTimeMillis());

        while (true) {

            try {

                System.out.print("Enter New End Date (yyyy-mm-dd) : ");

                Date endDate = Date.valueOf(sc.nextLine());

                if (!endDate.after(currentDate)) {

                    System.out.println("End Date must be greater than today's date.");
                    continue;

                }

                project.setEndDate(endDate);
                break;

            } catch (IllegalArgumentException e) {

                System.out.println("Invalid Date Format. Use yyyy-mm-dd.");

            }

        }

        // ---------------- Assigned Employees ----------------

        while (true) {

            System.out.print("Enter Assigned Employee IDs (comma separated) : ");

            String assignedEmployees = sc.nextLine().trim();

            if (assignedEmployees.isEmpty()) {

                System.out.println("Assigned Employee IDs cannot be empty.");

            } else {

                project.setAssignedEmployees(assignedEmployees);
                break;

            }

        }

        // ---------------- Project Status ----------------

        while (true) {

            System.out.println("\n=========== PROJECT STATUS ===========");
            System.out.println("1. Planning");
            System.out.println("2. Development");
            System.out.println("3. Testing");
            System.out.println("4. Completed");

            System.out.print("Enter Choice : ");

            if (!sc.hasNextInt()) {

                System.out.println("Invalid Choice.");
                sc.next();
                continue;

            }

            int statusChoice = sc.nextInt();
            sc.nextLine();

            switch (statusChoice) {

                case 1:
                    project.setProjectStatus("Planning");
                    break;

                case 2:
                    project.setProjectStatus("Development");
                    break;

                case 3:
                    project.setProjectStatus("Testing");
                    break;

                case 4:
                    project.setProjectStatus("Completed");
                    break;

                default:
                    System.out.println("Choose between 1 and 4.");
                    continue;

            }

            break;

        }

        // ---------------- Update ----------------

        boolean result = projectDAO.updateProject(project);

        if (result) {

            System.out.println("\nProject Updated Successfully.");

        } else {

            System.out.println("\nProject Not Found or Budget Not Approved.");

        }

    }

    public void viewProjectHistory() {

        projectDAO.viewProjectHistory();

    }

}