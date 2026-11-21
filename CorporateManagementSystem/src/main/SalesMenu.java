package main;

import service.TenderService;
import util.TenderReportGenerator;

import java.io.File;
import java.util.Scanner;
import util.FileIOUploader;

public class SalesMenu {

    Scanner sc = new Scanner(System.in);

    TenderService tenderService = new TenderService();

    public void displaySalesMenu() {

        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("              SALES MENU");
            System.out.println("========================================");

            System.out.println("1. Tender Operations");
            System.out.println("2. View Tenders");
            System.out.println("3. Search Tender");
            System.out.println("4. Generate Company Tender Report");
            System.out.println("5. Logout");

            while (true) {

                System.out.print("Enter your choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();

                    if (choice >= 1 && choice <= 5) {
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

                    tenderOperations();
                    break;

                case 2:

                    tenderService.viewTenders();
                    break;

                case 3:

                    tenderService.searchTender();
                    break;


                case 4:

                    File reportFile = TenderReportGenerator.generateReport();

                    if (reportFile != null) {

                        System.out.println("\nCompany Tender Report generated successfully.");
                        System.out.println("File: " + reportFile.getAbsolutePath());

                        String fileUrl = FileIOUploader.uploadFile(reportFile);

                        if (fileUrl != null) {

                            System.out.println("\nReport uploaded successfully.");

                        } else {

                            System.out.println("\nReport generated, but upload failed.");

                        }

                    } else {

                        System.out.println("Failed to generate company tender report.");

                    }

                    break;

                case 5:

                    System.out.println("\nLogging Out...");
                    break;

                default:

                    System.out.println("\nInvalid Choice.");

            }

        } while (choice != 5);

    }

    public void tenderOperations() {

        int choice;

        do {

            System.out.println("\n========================================");
            System.out.println("          TENDER OPERATIONS");
            System.out.println("========================================");

            System.out.println("1. Send Tender");
            System.out.println("2. Receive Tender");
            System.out.println("3. Back");

            System.out.print("\nEnter Choice : ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    tenderService.sendTender();
                    break;

                case 2:

                    tenderService.receiveTender();
                    break;

                case 3:

                    break;

                default:

                    System.out.println("\nInvalid Choice.");

            }

        } while (choice != 3);

    }

}