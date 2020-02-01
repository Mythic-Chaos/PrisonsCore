package com.mythicchaos;

import com.mythicchaos.utils.DBManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicChaos extends JavaPlugin {

    public void onEnable(){
        DBManager.openConnection();
    }

    public void onDisable(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> DBManager.closeConnection()));
    }

}
