package com.mythicchaos.utils;

import com.mythicchaos.MythicChaos;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Language {
    private MythicChaos plugin;
    private File file;
    private static FileConfiguration configuration;

    public Language(MythicChaos plugin){
        this.plugin = plugin;
    }

    public void load(){
        file = new File(plugin.getDataFolder() + "language.yml");
        if(!file.exists()){
            plugin.saveResource("language.yml", false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        System.out.println("Successfully Loaded: language.yml");
    }

    public void save(){
        try {
            configuration.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Returns any string with translated colour codes
    public static String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
    }

}
