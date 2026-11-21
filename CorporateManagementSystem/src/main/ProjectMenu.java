package main;

import service.ProjectLogin;
import service.ProjectService;

import java.util.Scanner;

public class ProjectMenu {

    Scanner sc = new Scanner(System.in);
    ProjectService projectService = new ProjectService();

    public void displayProjectMenu() {
        ProjectLogin projectLogin = new ProjectLogin();

        if (!projectLogin.login()) {
            return;
        }
        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("            PROJECT MENU");
            System.out.println("========================================");
            System.out.println("1. Add Project");
            System.out.println("2. View Projects");
            System.out.println("3. Search Project");
            System.out.println("4. Update Project");
            System.out.println("5. View Project History");
            System.out.println("6. Logout");

            while (true) {

                System.out.print("Enter your choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 6) {
                        break;
                    }

                    System.out.println("Please enter a choice between 1 and 6.");

                } else {

                    System.out.println("Invalid input. Enter numbers only.");
                    sc.next();

                }

            }

            switch (choice) {

                case 1:
                    projectService.addProject();
                    break;

                case 2:
                    projectService.viewProjects();
                    break;

                case 3:

                    projectService.searchProject();
                    break;

                case 4:

                    projectService.updateProject();
                    break;

                case 5:
                    projectService.viewProjectHistory();
                    break;

                case 6:
                    System.out.println("Logged Out Successfully.");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 6);
    }
}