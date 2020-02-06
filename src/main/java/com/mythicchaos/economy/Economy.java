package com.mythicchaos.economy;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.PrisonPlayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Economy {
    private static HashMap<UUID, Integer> balance;

    public void startUp() {
        balance = new HashMap<>();
        try {
            while (DBManager.getResults().next()) {
                balance.put(UUID.fromString(DBManager.getResults().getString("UUID")), DBManager.getResults().getInt("Balance"));
            }
            System.out.println("Loaded " + balance.size() + " balance(s) from the database!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void shutDown() {
        String sql = "UPDATE player_data SET Balance = ? WHERE UUID = ?";
        try {
            DBManager.preparedStatement = DBManager.getConnection().prepareStatement(sql);
            for (UUID player : balance.keySet()) {
                DBManager.preparedStatement.setInt(1, balance.get(player));
                DBManager.preparedStatement.setString(2, player.toString());
            }
            System.out.println("Successfully updated " + balance.size() + " balances!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        balance.clear();
    }

    /**
    I have created these methods so that other people can work on other areas which may
    involve using the economy feature. Feel free to change the name of the methods however,
    please make sure this is clearly noted in the commit that you do so that changes can be
    made where we have (maybe) used the old method names.

    The database logic above will stay as that will work perfectly fine, unless. you know
    of a much better way to be doing the statements. Then feel free to change the logic,
    again, just make sure that the changes you make are outlined in your commit log :)

    Happy Programming :D
     **/

    public static int getBalance(PrisonPlayer player){
        return -1;
    }

    public static void giveBalance(PrisonPlayer player, int amount){

    }

    public static void setBalance(PrisonPlayer player, int amount){

    }

    public static boolean takeBalance(PrisonPlayer player, int amount){
        return false;
    }

    public static boolean hasBalance(PrisonPlayer player, int amount){
        return false;
    }


}
