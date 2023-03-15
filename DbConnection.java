package com.example.demo11;
import java.sql.*;
public class DbConnection {
    String url="jdbc:mysql://localhost:3306/company";
    String user="root";

    Connection connection;
    Statement myStmt;
    PreparedStatement preparedStatement=null;
    ResultSet myRs;

    public DbConnection() throws SQLException {
        connection=DriverManager.getConnection(url,user,"");
        myStmt=connection.createStatement();
    }
}
