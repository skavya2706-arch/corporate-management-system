package service;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProjectLogin {

    private Scanner sc = new Scanner(System.in);

    public boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.println("\n========================================");
            System.out.println("          PROJECT HEAD LOGIN");
            System.out.println("========================================");

            System.out.print("Enter Employee ID : ");

            if (!sc.hasNextInt()) {

                System.out.println("Invalid Employee ID. Enter numbers only.");
                sc.next();
                attempts++;
                continue;
            }

            int empId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Password : ");
            String password = sc.nextLine();

            String query =
                    "SELECT e.emp_id " +
                            "FROM employee e " +
                            "WHERE e.emp_id = ? " +
                            "AND e.password = ? " +
                            "AND EXISTS (" +
                            "SELECT 1 FROM project p " +
                            "WHERE p.project_head = e.emp_id" +
                            ")";

            try {

                Connection connection = DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query);

                statement.setInt(1, empId);
                statement.setString(2, password);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    System.out.println(
                            "\nProject Head Login Successful!"
                    );

                    return true;

                } else {

                    attempts++;

                    System.out.println(
                            "\nInvalid Project Head ID or Password."
                    );

                    System.out.println(
                            "Attempts remaining: "
                                    + (3 - attempts)
                    );
                }

            } catch (SQLException e) {

                System.out.println(
                        "Database error during Project Head login."
                );

                e.printStackTrace();

                return false;
            }
        }

        System.out.println(
                "\nToo many failed login attempts."
        );

        return false;
    }
}