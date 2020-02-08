package com.mythicchaos.utils;

import com.mythicchaos.MythicChaos;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class Language {
    private File file;
    private FileConfiguration configuration;
    private MythicChaos plugin;
    private HashMap<String, String> messages;

    public Language(MythicChaos plugin) {
        this.plugin = plugin;
    }

    public void loadUp() {
        file = new File(plugin.getDataFolder() + File.separator + "lang.yml");
        if (!file.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);

        int amount = 0;
        messages = new HashMap<>();
        for(String identifier : configuration.getConfigurationSection("").getKeys(false)){
            amount++;
            messages.put(identifier, configuration.getString(identifier));
        }
        System.out.println("Successfully Loaded " + amount + " messages from lang.yml");
    }

    public String getMessage(String identifier){
        if(messages != null && messages.containsKey(identifier)){
            return ChatColor.translateAlternateColorCodes('&', messages.get(identifier));
        }
        return ChatColor.translateAlternateColorCodes('&',"&C&lThat is an invalid string identifier!");
    }

    public void shutDown(){
        messages.clear();
    }

}
