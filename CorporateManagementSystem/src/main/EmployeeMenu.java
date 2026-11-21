package main;

import model.Employee;
import service.EmployeeService;

import java.util.Scanner;

public class EmployeeMenu {

    public static void employeeMenu(Employee employee) {

        Scanner sc = new Scanner(System.in);

        EmployeeService employeeService = new EmployeeService();

        int choice;

        do {

            System.out.println("\n=========== EMPLOYEE MENU ===========");
            System.out.println("1. View My Profile");
            System.out.println("2. Apply Leave");
            System.out.println("3. View Leave Status");
            System.out.println("4. Change Password");
            System.out.println("5. Update my details ");
            System.out.println("6. Logout");

            while (true) {

                System.out.print("Enter your choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 6) {
                        break;
                    }

                    System.out.println("Please enter a choice between 1 and 5.");

                } else {

                    System.out.println("Invalid input. Enter numbers only.");
                    sc.next();

                }

            }

            switch (choice) {

                case 1:

                    employeeService.viewMyProfile(employee.getEmpId());
                    break;

                case 2:

                    employeeService.applyLeave(employee.getEmpId());
                    break;

                case 3:

                    employeeService.viewLeaveStatus(employee.getEmpId());
                    break;

                case 4:

                    employeeService.changePassword(employee.getEmpId());
                    break;

                case 5:
                    employeeService.updateMyDetails(employee.getEmpId());
                    break;

                case 6:

                    System.out.println("\nLogged Out Successfully.");
                    break;

                default:

                    System.out.println("\nInvalid Choice.");

            }

        } while (choice != 6);

    }

}