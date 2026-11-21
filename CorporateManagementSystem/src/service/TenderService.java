package service;

import dao.TenderDAO;
import model.Tender;

import java.sql.Date;
import java.util.ArrayList;
import model.Tender;
import java.util.Scanner;

public class TenderService {

    private TenderDAO tenderDAO = new TenderDAO();
    private Scanner sc = new Scanner(System.in);

    public void sendTender() {

        Tender tender = new Tender();

        int typeChoice = 0;

        System.out.println("\n=========== SEND TENDER ===========");

        System.out.print("Tender Name : ");
        tender.setTenderName(sc.nextLine());

        System.out.print("Company Name : ");
        tender.setCompanyName(sc.nextLine());
        System.out.println("\nSelect Tender Type:");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.print("Enter choice : ");


        int tenderTypeChoice=0;

        while (true) {




            if (sc.hasNextInt()) {

                tenderTypeChoice = sc.nextInt();
                sc.nextLine(); // consume newline

                if (tenderTypeChoice == 1) {

                    tender.setTenderType("Government");
                    break;

                } else if (tenderTypeChoice == 2) {

                    tender.setTenderType("Private");
                    break;

                } else {

                    System.out.println(
                            "Invalid choice. Please enter 1 or 2."
                    );
                }

            } else {

                System.out.println(
                        "Invalid input. Please enter a number (1 or 2)."
                );

                sc.nextLine(); // remove invalid string
            }
        }



        double amount;

        while (true) {

            System.out.print("Enter Tender Amount : ");

            if (sc.hasNextDouble()) {

                amount = sc.nextDouble();

                if (amount >= 0) {

                    tender.setAmount(amount);
                    break;

                } else {

                    System.out.println(
                            "Invalid amount. Amount cannot be negative."
                    );
                }

            } else {

                System.out.println(
                        "Invalid amount. Please enter a numeric value."
                );

                sc.nextLine(); // remove invalid input
            }
        }


        sc.nextLine();


        java.sql.Date issueDate;
        java.sql.Date dueDate;

// ================= ISSUE DATE =================

        while (true) {

            System.out.print("Enter Issue Date (YYYY-MM-DD) : ");

            String issueDateInput = sc.nextLine();

            try {

                issueDate = java.sql.Date.valueOf(issueDateInput);

                tender.setIssueDate(issueDate);
                break;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid date. Please enter date in YYYY-MM-DD format."
                );
            }
        }


// ================= DUE DATE =================

        while (true) {

            System.out.print("Enter Due Date (YYYY-MM-DD) : ");

            String dueDateInput = sc.nextLine();

            try {

                dueDate = java.sql.Date.valueOf(dueDateInput);

                if (dueDate.before(issueDate)) {

                    System.out.println(
                            "Invalid due date. Due date cannot be before issue date."
                    );

                } else {

                    tender.setDueDate(dueDate);
                    break;
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid date. Please enter date in YYYY-MM-DD format."
                );
            }
        }



        boolean result = tenderDAO.receiveTender(tender);

        if (result) {

            System.out.println("\nTender Received Successfully.");

        } else {

            System.out.println("\nFailed to Receive Tender.");

        }

    }
    public void receiveTender() {

        Tender tender = new Tender();

        int typeChoice = 0;

        System.out.println("\n=========== RECEIVE TENDER ===========");

        System.out.print("Tender Name : ");
        tender.setTenderName(sc.nextLine());

        System.out.print("Company Name : ");
        tender.setCompanyName(sc.nextLine());
        System.out.println("\nSelect Tender Type:");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.print("Enter choice : ");


         int tenderTypeChoice=0;

        while (true) {




            if (sc.hasNextInt()) {

                tenderTypeChoice = sc.nextInt();
                sc.nextLine(); // consume newline

                if (tenderTypeChoice == 1) {

                    tender.setTenderType("Government");
                    break;

                } else if (tenderTypeChoice == 2) {

                    tender.setTenderType("Private");
                    break;

                } else {

                    System.out.println(
                            "Invalid choice. Please enter 1 or 2."
                    );
                }

            } else {

                System.out.println(
                        "Invalid input. Please enter a number (1 or 2)."
                );

                sc.nextLine(); // remove invalid string
            }
        }



        double amount;

        while (true) {

            System.out.print("Enter Tender Amount : ");

            if (sc.hasNextDouble()) {

                amount = sc.nextDouble();

                if (amount >= 0) {

                    tender.setAmount(amount);
                    break;

                } else {

                    System.out.println(
                            "Invalid amount. Amount cannot be negative."
                    );
                }

            } else {

                System.out.println(
                        "Invalid amount. Please enter a numeric value."
                );

                sc.nextLine(); // remove invalid input
            }
        }


        sc.nextLine();


        java.sql.Date issueDate;
        java.sql.Date dueDate;

// ================= ISSUE DATE =================

        while (true) {

            System.out.print("Enter Issue Date (YYYY-MM-DD) : ");

            String issueDateInput = sc.nextLine();

            try {

                issueDate = java.sql.Date.valueOf(issueDateInput);

                tender.setIssueDate(issueDate);
                break;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid date. Please enter date in YYYY-MM-DD format."
                );
            }
        }


// ================= DUE DATE =================

        while (true) {

            System.out.print("Enter Due Date (YYYY-MM-DD) : ");

            String dueDateInput = sc.nextLine();

            try {

                dueDate = java.sql.Date.valueOf(dueDateInput);

                if (dueDate.before(issueDate)) {

                    System.out.println(
                            "Invalid due date. Due date cannot be before issue date."
                    );

                } else {

                    tender.setDueDate(dueDate);
                    break;
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid date. Please enter date in YYYY-MM-DD format."
                );
            }
        }



        boolean result = tenderDAO.receiveTender(tender);

        if (result) {

            System.out.println("\nTender Received Successfully.");

        } else {

            System.out.println("\nFailed to Receive Tender.");

        }

    }
    public void viewTenders() {

        System.out.println("\n========================================");
        System.out.println("             VIEW TENDERS");
        System.out.println("========================================");

        ArrayList<Tender> tenderList = tenderDAO.viewTenders();

        if (tenderList.isEmpty()) {

            System.out.println("\nNo Tenders Found.");
            return;

        }

        System.out.printf("%-5s %-20s %-20s %-15s %-12s %-15s %-15s %-12s\n",
                "ID", "Tender Name", "Company", "Type", "Amount", "Issue Date", "Due Date", "Status");

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (Tender tender : tenderList) {

            System.out.printf("%-5d %-20s %-20s %-15s %-12.2f %-15s %-15s %-12s\n",
                    tender.getTenderId(),
                    tender.getTenderName(),
                    tender.getCompanyName(),
                    tender.getTenderType(),
                    tender.getAmount(),
                    tender.getIssueDate(),
                    tender.getDueDate(),
                    tender.getTenderStatus());

        }

    }
    public void searchTender() {

        System.out.println("\n========================================");
        System.out.println("            SEARCH TENDER");
        System.out.println("========================================");

        System.out.print("Enter Tender ID : ");
        int tenderId = sc.nextInt();

        Tender tender = tenderDAO.searchTender(tenderId);

        if (tender != null) {

            System.out.println("\nTender Found Successfully.\n");

            System.out.println("Tender ID      : " + tender.getTenderId());
            System.out.println("Tender Name    : " + tender.getTenderName());
            System.out.println("Company Name   : " + tender.getCompanyName());
            System.out.println("Tender Type    : " + tender.getTenderType());
            System.out.println("Amount         : " + tender.getAmount());
            System.out.println("Issue Date     : " + tender.getIssueDate());
            System.out.println("Due Date       : " + tender.getDueDate());
            System.out.println("Tender Status  : " + tender.getTenderStatus());

        } else {

            System.out.println("\nTender Not Found.");

        }

    }
}