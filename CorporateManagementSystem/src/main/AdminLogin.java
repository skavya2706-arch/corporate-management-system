package main;

import model.Admin;
import service.AdminService;
import service.ITService;
import java.util.Scanner;

public class AdminLogin {

    Scanner sc = new Scanner(System.in);
    ITService itService = new ITService();
    public void login() {

        int attempts = 0;

        while (attempts < 3) {

            String username;

            String password;

            System.out.println("\n========================================");
            System.out.println("              ADMIN LOGIN");
            System.out.println("========================================");

            System.out.print("Username : ");
            username = sc.next();

            System.out.print("Password : ");
            password = sc.next();

            AdminService adminService = new AdminService();

            Admin admin = adminService.login(username, password);

            if (admin != null) {

                System.out.println("\nLogin Successful!");
                System.out.println("Welcome " + admin.getRole());

                if (admin.getRole().equalsIgnoreCase("HR")) {

                    HRMenu hrMenu = new HRMenu();
                    hrMenu.displayHRMenu();

                } else if (admin.getRole().equalsIgnoreCase("SALES")) {

                    SalesMenu salesMenu = new SalesMenu();
                    salesMenu.displaySalesMenu();

                } else if (admin.getRole().equalsIgnoreCase("PROJECT HEAD")) {

                    ProjectMenu projectMenu = new ProjectMenu();
                    projectMenu.displayProjectMenu();

                } else if (admin.getRole().equalsIgnoreCase("ACCOUNTS")) {

                    FinanceMenu financeMenu = new FinanceMenu();
                    financeMenu.displayFinanceMenu();

                } else if (admin.getRole().equalsIgnoreCase("IT")) {

                    ITMenu itMenu = new ITMenu();
                    itMenu.displayITMenu();

                }

                return;

            } else {

                attempts++;

                System.out.println("\nInvalid Username or Password.");

                if (attempts < 3) {

                    System.out.println("Attempts Remaining : " + (3 - attempts));

                } else {

                    System.out.println("\n=========== FORGOT PASSWORD ===========");
                    System.out.println("1. Raise Password Reset Request");
                    System.out.println("2. Back");

                    System.out.print("Enter Choice : ");
                    int choice = sc.nextInt();

                    if (choice == 1) {

                        itService.raisePasswordResetRequest();

                    }

                    return;

                }

            }

        }

    }
}