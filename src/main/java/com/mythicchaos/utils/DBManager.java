package com.mythicchaos.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static Connection connection;
    private static Statement statement;
    private static final String host = "multicraft.pandaserv.net", database = "test", username = "1197", password = "8c9b7bba37";
    private static final int port = 3306;

    public static void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            statement = connection.createStatement();
            System.out.println("Connection to database: Successful!");
        } catch (SQLException e){
            System.out.println("Connection to database: Unsuccessful!");
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            if(connection != null){
                connection.close();
                System.out.println("Connection to database: Closed!");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Testing

}
