package com.mythicchaos.utils.listeners;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.PrisonPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PrisonPlayer player = new PrisonPlayer(event.getPlayer());
        if(!DBManager.inDatabase(player)){
            DBManager.createPlayer(player);
        }
    }

}
