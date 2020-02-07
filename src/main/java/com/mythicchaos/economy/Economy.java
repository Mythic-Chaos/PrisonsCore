package com.mythicchaos.economy;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.Language;
import com.mythicchaos.utils.PrisonPlayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Economy {
    private static HashMap<UUID, Integer> drachma;

    public void startUp() {
        drachma = new HashMap<>();
        try {
            while (DBManager.getResults().next()) {
                drachma.put(UUID.fromString(DBManager.getResults().getString("UUID")), DBManager.getResults().getInt("Drachma"));
            }
            System.out.println("Loaded " + drachma.size() + " balance(s) from the database!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void shutDown() {
        String sql = "UPDATE player_data SET Balance = ? WHERE UUID = ?";
        try {
            DBManager.preparedStatement = DBManager.getConnection().prepareStatement(sql);
            for (UUID player : drachma.keySet()) {
                DBManager.preparedStatement.setInt(1, drachma.get(player));
                DBManager.preparedStatement.setString(2, player.toString());
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
        player.sendMessage(Language.getMessage("receivedDrachma").replaceAll("%amount%", String.valueOf(amount)));
    }

    public static void setBalance(PrisonPlayer player, int amount){
        drachma.remove(player.getUniqueId());
        drachma.put(player.getUniqueId(), amount);
    }

    public static boolean takeBalance(PrisonPlayer player, int amount){
        if(hasBalance(player, amount)){
            setBalance(player, getBalance(player) -  amount);
            player.sendMessage(Language.getMessage("lostDrachma").replaceAll("%amount%", String.valueOf(amount)));
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
