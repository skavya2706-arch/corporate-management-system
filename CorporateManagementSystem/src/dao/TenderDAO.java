package dao;
import database.DBConnection;
import model.Tender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class TenderDAO {

    public boolean sendTender(Tender tender) {

        String query = "INSERT INTO tender(tender_name, company_name, tender_type, amount, issue_date, due_date, tender_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, tender.getTenderName());
            preparedStatement.setString(2, tender.getCompanyName());
            preparedStatement.setString(3, tender.getTenderType());
            preparedStatement.setDouble(4, tender.getAmount());
            preparedStatement.setDate(5, tender.getIssueDate());
            preparedStatement.setDate(6, tender.getDueDate());
            preparedStatement.setString(7, "Sent");

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean receiveTender(Tender tender) {

        String query = "INSERT INTO tender(tender_name, company_name, tender_type, amount, issue_date, due_date, tender_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, tender.getTenderName());
            preparedStatement.setString(2, tender.getCompanyName());
            preparedStatement.setString(3, tender.getTenderType());
            preparedStatement.setDouble(4, tender.getAmount());
            preparedStatement.setDate(5, tender.getIssueDate());
            preparedStatement.setDate(6, tender.getDueDate());
            preparedStatement.setString(7, "Received");

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }
    public ArrayList<Tender> viewTenders() {

        ArrayList<Tender> tenderList = new ArrayList<>();

        String query = "SELECT * FROM tender";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Tender tender = new Tender();

                tender.setTenderId(resultSet.getInt("tender_id"));
                tender.setTenderName(resultSet.getString("tender_name"));
                tender.setCompanyName(resultSet.getString("company_name"));
                tender.setTenderType(resultSet.getString("tender_type"));
                tender.setAmount(resultSet.getDouble("amount"));
                tender.setIssueDate(resultSet.getDate("issue_date"));
                tender.setDueDate(resultSet.getDate("due_date"));
                tender.setTenderStatus(resultSet.getString("tender_status"));

                tenderList.add(tender);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return tenderList;

    }

    public Tender searchTender(int tenderId) {

        Tender tender = null;

        String query = "SELECT * FROM tender WHERE tender_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, tenderId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                tender = new Tender();

                tender.setTenderId(resultSet.getInt("tender_id"));
                tender.setTenderName(resultSet.getString("tender_name"));
                tender.setCompanyName(resultSet.getString("company_name"));
                tender.setTenderType(resultSet.getString("tender_type"));
                tender.setAmount(resultSet.getDouble("amount"));
                tender.setIssueDate(resultSet.getDate("issue_date"));
                tender.setDueDate(resultSet.getDate("due_date"));
                tender.setTenderStatus(resultSet.getString("tender_status"));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return tender;

    }
}