package main;

import model.Employee;
import service.EmployeeService;

import java.util.Scanner;

public class EmployeeLogin {

    public static void employeeLogin() {

        Scanner sc = new Scanner(System.in);

        EmployeeService employeeService = new EmployeeService();

        System.out.println("\n=========== EMPLOYEE LOGIN ===========");

        int empId;

        while (true) {

            System.out.print("Employee ID : ");

            if (sc.hasNextInt()) {

                empId = sc.nextInt();

                if (empId > 0) {

                    break;

                }

                System.out.println("Employee ID must be greater than 0.");

            } else {

                System.out.println("Invalid Employee ID. Enter numbers only.");
                sc.next(); // Clear invalid input

            }

        }

        System.out.print("Password : ");
        String password = sc.next();

        Employee employee = employeeService.employeeLogin(empId, password);

        if (employee != null) {

            if (employee.getEmployeeStatus().equalsIgnoreCase("Active")) {

                System.out.println("\nLogin Successful.");
                System.out.println("Welcome " + employee.getEmpName());

                EmployeeMenu.employeeMenu(employee);

            } else {

                System.out.println("\nYour Account is Inactive.");

            }

        } else {

            System.out.println("\nInvalid Employee ID or Password.");

        }

    }

}