package service;

import dao.FinanceDAO;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class FinanceService {

    Scanner sc = new Scanner(System.in);

    FinanceDAO financeDAO = new FinanceDAO();

    public void salaryManagement() {

        int choice;

        do {

            System.out.println("\n=========== SALARY MANAGEMENT ===========");

            System.out.println("1. View Employee Salaries");
            System.out.println("2. Update Employee Salary");
            System.out.println("3. Back");

            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    financeDAO.viewEmployeeSalaries();
                    break;
                case 2:
                    updateEmployeeSalary();

                    break;

                case 3:
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 3);

    }public void updateEmployeeSalary() {

        System.out.print("\nEnter Employee ID : ");
        int empId = sc.nextInt();

        System.out.print("Enter New Salary : ");
        double salary = sc.nextDouble();

        if (financeDAO.updateEmployeeSalary(empId, salary)) {

            System.out.println("\nSalary Updated Successfully.");

        } else {

            System.out.println("\nEmployee Not Found.");

        }

    }

    public void budgetApproval() {

        int choice;

        do {

            System.out.println("\n=========== BUDGET APPROVAL ===========");

            System.out.println("1. View Pending Projects");
            System.out.println("2. Approve Budget");
            System.out.println("3. Reject Budget");
            System.out.println("4. Back");

            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    financeDAO.viewPendingProjects();
                    break;

                case 2:
                    approveBudget();
                    break;

                case 3:
                    rejectBudget();
                    break;

                case 4:
                    break;
                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 4);

    }

    public void companyTurnover() {

        financeDAO.companyTurnover();

    }
    public void approveBudget() {

        int projectId;

        while (true) {

            System.out.print("\nEnter Project ID : ");
            projectId = sc.nextInt();

            if (projectId > 0) {

                break;

            }

            System.out.println("Project ID must be greater than 0.");

        }

        if (financeDAO.approveBudget(projectId)) {

            System.out.println("\nBudget Approved Successfully.");

        } else {

            System.out.println("\nProject Not Found.");

        }

    }
    public void rejectBudget() {

        int projectId;

        while (true) {

            System.out.print("\nEnter Project ID : ");
            projectId = sc.nextInt();

            if (projectId > 0) {

                break;

            }

            System.out.println("Project ID must be greater than 0.");

        }

        if (financeDAO.rejectBudget(projectId)) {

            System.out.println("\nBudget Rejected Successfully.");

        } else {

            System.out.println("\nProject Not Found.");

        }

    }

}