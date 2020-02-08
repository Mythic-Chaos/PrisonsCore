package com.mythicchaos.economy;

import com.mythicchaos.MythicChaos;
import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.PrisonPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Economy {
    private static HashMap<UUID, Integer> drachma;

    public void startUp() {
        drachma = new HashMap<>();
        try {
            ResultSet results = DBManager.getStatament().executeQuery("SELECT UUID, Drachma FROM player_data");
            while (results.next()) {
                drachma.put(UUID.fromString(results.getString("UUID")), results.getInt("Drachma"));
            }
            System.out.println("Loaded " + drachma.size() + " balance(s) from the database!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void shutDown() {
        String sql = "UPDATE player_data SET Drachma = ? WHERE UUID = ?";
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement(sql);
            for (UUID player : drachma.keySet()) {
                statement.setInt(1, drachma.get(player));
                statement.setString(2, player.toString());
                statement.executeUpdate();
            }
            System.out.println("Successfully updated " + drachma.size() + " balances!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        drachma.clear();
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
        if(drachma != null && drachma.containsKey(player.getUniqueId())){
            return drachma.get(player.getUniqueId());
        }
        return 0;
    }

    public static void giveBalance(PrisonPlayer player, int amount){
        amount += getBalance(player);
        setBalance(player, amount);
        player.sendMessage(MythicChaos.getLanguage().getMessage("receivedDrachma").replaceAll("%amount%", String.valueOf(amount)));
    }

    public static void setBalance(PrisonPlayer player, int amount){
        drachma.remove(player.getUniqueId());
        drachma.put(player.getUniqueId(), amount);
    }

    public static boolean takeBalance(PrisonPlayer player, int amount){
        if(hasBalance(player, amount)){
            setBalance(player, getBalance(player) -  amount);
            player.sendMessage(MythicChaos.getLanguage().getMessage("lostDrachma").replaceAll("%amount%", String.valueOf(amount)));
            return true;
        }
        return false;
    }

    public static boolean hasBalance(PrisonPlayer player, int amount){
        if(getBalance(player) >= amount){
            return true;
        }
        return false;
    }


}
