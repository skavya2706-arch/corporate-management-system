package dao;

import database.DBConnection;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin login(String username, String password) {
        System.out.println("Inside AdminDAO");
        String query = "SELECT * FROM admin WHERE username=? AND password=?";

        try {

            System.out.println("Before Connection");
            Connection connection = DBConnection.getConnection();
            System.out.println("After Connection");

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                Admin admin = new Admin();

                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setRole(resultSet.getString("role"));
                admin.setEmpId(resultSet.getInt("emp_id"));

                return admin;

            }

        }
        catch (Exception e){

            e.printStackTrace();

        }

        return null;

    }

}