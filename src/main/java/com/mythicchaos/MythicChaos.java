package com.mythicchaos;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.PrisonPlayer;
import jdk.Exported;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicChaos extends JavaPlugin {

    public void onEnable(){
        DBManager.openConnection();
    }

    public void onDisable(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> DBManager.closeConnection()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PrisonPlayer player = (PrisonPlayer) event.getPlayer();
    }

}
