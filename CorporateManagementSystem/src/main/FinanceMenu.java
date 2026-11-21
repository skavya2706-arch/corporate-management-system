package main;

import service.FinanceService;

import java.util.Scanner;

public class FinanceMenu {

    Scanner sc = new Scanner(System.in);

    FinanceService financeService = new FinanceService();

    public void displayFinanceMenu() {

        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("            FINANCE MENU");
            System.out.println("========================================");

            System.out.println("1. Salary Management");
            System.out.println("2. Budget Approval");
            System.out.println("3. Company Turnover");
            System.out.println("4. Logout");

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
                    financeService.salaryManagement();
                    break;

                case 2:
                    financeService.budgetApproval();
                    break;

                case 3:
                    financeService.companyTurnover();
                    break;

                case 4:
                    System.out.println("\nLogging Out...");
                    break;

                default:
                    System.out.println("\nInvalid Choice.");

            }

        } while (choice != 4);

    }

}