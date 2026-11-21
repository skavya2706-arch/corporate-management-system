package main;

import service.ITService;

import java.util.Scanner;

public class ITMenu {

    Scanner sc = new Scanner(System.in);

    ITService itService = new ITService();

    public void displayITMenu() {

        int choice;

        do {

            System.out.println("\n=========== IT MENU ===========");

            System.out.println("1. System Information");
            System.out.println("2. Account Management");
            System.out.println("3. Company Hierarchy");
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
                    itService.systemInformation();
                    break;

                case 2:
                    itService.accountManagement();
                    break;

                case 3:
                    itService.companyHierarchy();
                    break;

                case 4:
                    System.out.println("\nLogged Out Successfully.");
                    break;

                default:
                    System.out.println("\nInvalid Choice.");

            }

        } while (choice != 4);

    }

}