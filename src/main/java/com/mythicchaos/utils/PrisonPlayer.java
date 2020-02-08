package com.mythicchaos.utils;

import com.mythicchaos.MythicChaos;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PrisonPlayer extends CraftPlayer implements Listener {

    public PrisonPlayer(Player player){
        super((CraftServer) player.getServer(), ((CraftPlayer) player).getHandle());
    }

    /**
     * @param message -> The message you want to send the player, can have colour codes
     *                -> Overrides default method and adds the prefix before the message
     *                -> Automatically translates colour codes so no need for ChatColor
     */
    @Override
    public void sendMessage(String message){
        super.sendMessage(MythicChaos.getLanguage().getMessage("prefix") + ChatColor.translateAlternateColorCodes('&', message));
    }

    public double getMoney(){
        return VaultHook.getEconomy().getBalance(this);
    }

    public void giveMoney(double amount){
        VaultHook.getEconomy().depositPlayer(this, amount);
        sendMessage(MythicChaos.getLanguage().getMessage("receivedMoneyFromServer").replaceAll("%amount%", String.valueOf(amount)));
    }

    public void giveMoney(int amount){
        VaultHook.getEconomy().depositPlayer(this, amount);
        sendMessage(MythicChaos.getLanguage().getMessage("receivedMoneyFromServer").replaceAll("%amount%", String.valueOf(amount)));
    }

    public boolean hasMoney(double amount){
        if(getMoney() >= amount){
            return true;
        }
        return false;
    }

    public boolean hasMoney(int amount){
        if(getMoney() >= amount){
            return true;
        }
        return false;
    }

    public boolean takeMoney(double amount){
        if(hasMoney(amount)){
            EconomyResponse response = VaultHook.getEconomy().withdrawPlayer(this, amount);
            if(response.transactionSuccess()){
                sendMessage(MythicChaos.getLanguage().getMessage("moneyTakenByServer").replaceAll("%amount%", String.valueOf(amount)));
                return true;
            }
        }
        return false;
    }

    public boolean takeMoney(int amount){
        if(hasMoney(amount)){
            EconomyResponse response = VaultHook.getEconomy().withdrawPlayer(this, amount);
            if(response.transactionSuccess()){
                sendMessage(MythicChaos.getLanguage().getMessage("moneyTakenByServer").replaceAll("%amount%", String.valueOf(amount)));
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(ItemStack stack){
        if(getInventory().contains(stack)){
            return true;
        }
        return false;
    }

    public boolean takeItem(ItemStack stack){
        if(hasItem(stack)){
            getInventory().remove(stack);
            return true;
        }
        return false;
    }

    public boolean hasItems(List<ItemStack> stackList){
        int amount = 0;
        for(ItemStack stack : stackList){
            if(hasItem(stack)){
                amount++;
            }
        }
        if(amount == stackList.size()){
            return true;
        }
        return false;
    }

    public boolean takeItems(List<ItemStack> stackList){
        if(hasItems(stackList)){
            for(ItemStack stack : stackList){
                getInventory().remove(stack);
            }
            return true;
        }
        return false;
    }

    public boolean inDatabase(){
        if(DBManager.inDatabase(this)){
            return true;
        }
        return false;
    }

}
