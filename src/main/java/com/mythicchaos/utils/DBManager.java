package com.mythicchaos.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBManager {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static final String host = "multicraft.pandaserv.net", database = "1197", username = "1197", password = "8c9b7bba37";
    private static final int port = 3306;
    private static List<UUID> validPlayers;

    public static void openConnection() {
        validPlayers = new ArrayList<UUID>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            statement = connection.createStatement();
            System.out.println("Connection to database: Successful!");
        } catch (SQLException e){
            System.out.println("Connection to database: Unsuccessful!");
            e.printStackTrace();
        }

        try {
            ResultSet results = statement.executeQuery("SELECT * FROM player_data");
            System.out.println("Pulled all data from the player_data table!");
            while(results.next()){
                validPlayers.add(UUID.fromString(results.getString("UUID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
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

    public static Connection getConnection(){ return connection; }
    public static Statement getStatament(){
        return statement;
    }

    public static boolean inDatabase(PrisonPlayer player){
        if(validPlayers.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static void createPlayer(PrisonPlayer player){
        try {
            statement.execute("INSERT INTO player_data(UUID, Drachma, Prestige_Tokens) VALUES('" + player.getUniqueId().toString() + "', '0', '0')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
