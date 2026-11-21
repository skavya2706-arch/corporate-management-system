package main;

import java.util.Scanner;import service.EmployeeService;

public class HRMenu {

    Scanner sc = new Scanner(System.in);
    EmployeeService employeeService = new EmployeeService();
    public void displayHRMenu() {

        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("               HR MENU");
            System.out.println("========================================");

            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Sort Employees");
            System.out.println("7. Approve Leave");
            System.out.println("8. Logout");


            while (true) {

                System.out.print("Enter your choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 8) {
                        break;
                    }

                    System.out.println("Please enter a choice between 1 and 8.");

                } else {

                    System.out.println("Invalid input. Enter numbers only.");
                    sc.next();

                }

            }
            switch (choice) {

                case 1:
                    employeeService.addEmployee();
                    break;

                case 2:

                    employeeService.viewEmployees();
                    break;

                case 3:

                    employeeService.searchEmployee();
                    break;

                case 4:

                    employeeService.updateEmployee();
                    break;

                case 5:

                    employeeService.deleteEmployee();
                    break;

                case 6:

                    employeeService.sortEmployees();
                    break;

                case 7:

                    employeeService.approveLeave();
                    break;

                case 8:

                    System.out.println("Logging Out...");
                    break;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while (choice != 8);

    }

}