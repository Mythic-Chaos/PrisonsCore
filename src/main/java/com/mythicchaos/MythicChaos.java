package com.mythicchaos;

import com.mythicchaos.economy.EcoCommand;
import com.mythicchaos.economy.Economy;
import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.Language;
import com.mythicchaos.levelling.PickaxeLevel;
import com.mythicchaos.utils.VaultHook;
import com.mythicchaos.utils.commands.Balance;
import com.mythicchaos.utils.commands.PickaxeTest;
import com.mythicchaos.utils.listeners.OnJoin;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicChaos extends JavaPlugin {
    private VaultHook vaultHook;
    private Economy economy;
    private static Language language;

    public void onEnable(){
        DBManager.openConnection();

        //Hook into vault and start services
        vaultHook = new VaultHook(this);
        vaultHook.setupEconomy();

        //Load the language file into memory
        language = new Language(this);
        language.loadUp();
        PickaxeLevel.loadUpPickaxes();

        //Load and startup economy
        economy = new Economy();
        economy.startUp();

        // Register Commands
        getCommand("drachma").setExecutor(new EcoCommand());
        getCommand("balance").setExecutor(new Balance());
        getCommand("pickaxetest").setExecutor(new PickaxeTest());

        // Register Events
        getServer().getPluginManager().registerEvents(new OnJoin(), this);

    }

    public void onDisable() {
        // Put all closing/clean up methods before connection close
        economy.shutDown();
        language.shutDown();

        Runtime.getRuntime().addShutdownHook(new Thread(DBManager::closeConnection));
    }
}
