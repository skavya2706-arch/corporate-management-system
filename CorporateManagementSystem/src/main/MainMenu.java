package main;

import java.util.Scanner;

public class MainMenu {

    Scanner sc = new Scanner(System.in);

    public void displayMainMenu() {

        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("     CORPORATE MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Admin Login");
            System.out.println("2. Employee Login");
            System.out.println("3. About Project");
            System.out.println("4. Exit");
            System.out.println("========================================");

            while (true) {

                System.out.print("Enter your choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 4) {
                        break;
                    }

                    System.out.println("Please enter a choice between 1 and 4.");

                } else {

                    System.out.println("Invalid input. Enter numbers only.");
                    sc.next();

                }

            }

            switch (choice) {

                case 1:
                    AdminLogin adminLogin = new AdminLogin();
                    adminLogin.login();
                    break;

                case 2:

                    EmployeeLogin.employeeLogin();
                    break;

                case 3:

                    ProjectMenu aboutProject = new ProjectMenu();
                    aboutProject.displayProjectMenu();

                    break;
                case 4:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");

            }

        } while (choice != 4);

    }

}