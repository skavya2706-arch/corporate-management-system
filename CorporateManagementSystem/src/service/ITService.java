package service;

import dao.ITDAO;
import model.PasswordResetRequest;

import java.sql.Date;
import java.util.Scanner;

public class ITService {

    Scanner sc = new Scanner(System.in);

    ITDAO itDAO = new ITDAO();

    public void systemInformation() {

        itDAO.systemInformation();

    }

    public void accountManagement() {

        int choice;

        do {

            System.out.println("\n=========== ACCOUNT MANAGEMENT ===========");

            System.out.println("1. View Pending Password Requests");
            System.out.println("2. Process Password Reset");
            System.out.println("3. Back");

            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    itDAO.viewPendingPasswordRequests();
                    break;

                case 2:
                    processPasswordReset();
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 3);

    }

    public void raisePasswordResetRequest() {

        PasswordResetRequest request = new PasswordResetRequest();

        System.out.println("\n=========== PASSWORD RESET REQUEST ===========");

        System.out.print("Enter Employee ID : ");
        request.setEmpId(sc.nextInt());

        request.setRequestDate(new Date(System.currentTimeMillis()));
        request.setRequestStatus("Pending");

        if (itDAO.raisePasswordResetRequest(request)) {

            System.out.println("\nPassword Reset Request Submitted Successfully.");
            System.out.println("Please Contact The IT Department.");

        } else {

            System.out.println("\nFailed To Submit Request.");

        }

    }

    public void processPasswordReset() {

        System.out.print("\nEnter Request ID : ");
        int requestId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Temporary Password : ");
        String password = sc.nextLine();

        if (itDAO.processPasswordReset(requestId, password)) {

            System.out.println("\nPassword Reset Successfully.");

        } else {

            System.out.println("\nInvalid Request ID.");

        }

    }

    public void companyHierarchy() {

        itDAO.companyHierarchy();

    }

}