package com.mythicchaos;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.Language;
import com.mythicchaos.utils.VaultHook;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicChaos extends JavaPlugin {
    private VaultHook vaultHook;
    private Language language;

    public void onEnable(){
        DBManager.openConnection();

        //Hook into vault and start services
        vaultHook = new VaultHook(this);
        vaultHook.setupEconomy();

        //Load the language file into memory
        language = new Language(this);
        language.load();

    }

    public void onDisable(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> DBManager.closeConnection()));
    }

}
