package com.mythicchaos.levelling;

import com.mythicchaos.MythicChaos;
import com.mythicchaos.utils.inventory.NBTTag;
import com.mythicchaos.utils.inventory.TagType;
import com.mythicchaos.utils.nms.ItemDataUtilHelper;
import com.mythicchaos.utils.nms.NMSMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Copy Right ©
 * This code is private
 * Owner: PerryPlaysMC
 * From: 11/3/19-2200
 * Package: com.mythicchaos.utils
 * Class: PickaxeLevel
 * <p>
 * Path: com.mythicchaos.levelling.PickaxeLevel
 * <p>
 * Any attempts to use these program(s) may result in a penalty of up to $1,000 USD
 **/

@SuppressWarnings("all")
public class PickaxeLevel {
    private static File file;
    private static FileConfiguration configuration;
    private static MythicChaos plugin;

    public static void loadUpPickaxes() {
        plugin = MythicChaos.getPlugin(MythicChaos.class);
        file = new File(plugin.getDataFolder() + File.separator + "pickaxes.yml");
        if(!file.exists()) {
            plugin.saveResource("pickaxes.yml", false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        int amount = 0;
        for(String s : configuration.getKeys(false)) {
            if(s.startsWith("Level-"))
                amount++;
        }

        System.out.println("Successfully Pickaxe.yml with " + amount + " different pickaxe levels");
    }

    private static Material getPickaxeMaterial(String name) {
        if(name.equalsIgnoreCase("wood") ||
                name.equalsIgnoreCase("wood_pickaxe") ||
                name.equalsIgnoreCase("wooden_pickaxe") ||
                name.equalsIgnoreCase("woodpickaxe") ||
                name.equalsIgnoreCase("woodenpickaxe")) return Material.WOOD_PICKAXE;
        if(name.equalsIgnoreCase("stone") ||
                name.equalsIgnoreCase("stone_pickaxe") ||
                name.equalsIgnoreCase("stonepickaxe")) return Material.STONE_PICKAXE;
        if(name.equalsIgnoreCase("gold") ||
                name.equalsIgnoreCase("gold_pickaxe") ||
                name.equalsIgnoreCase("golden_pickaxe") ||
                name.equalsIgnoreCase("goldpickaxe") ||
                name.equalsIgnoreCase("goldenpickaxe")) return Material.GOLD_PICKAXE;
        if(name.equalsIgnoreCase("iron") ||
                name.equalsIgnoreCase("iron_pickaxe") ||
                name.equalsIgnoreCase("ironpickaxe")) return Material.IRON_PICKAXE;
        if(name.equalsIgnoreCase("diamond") ||
                name.equalsIgnoreCase("diamond_pickaxe") ||
                name.equalsIgnoreCase("diamondpickaxe")) return Material.DIAMOND_PICKAXE;
        return Material.WOOD_PICKAXE;
    }

    private static Double calculatePercentage(Double current, Double max) {
        if((max == null || max == 0) || (current == null || current == 0)) {
            return 0d;
        }
        Double bottom = max;
        Double top = current;
        Double percent = (top * 100 / bottom);
        return percent;
    }

    private static int getPickaxeLevel(ItemStack pick) {
        ItemDataUtilHelper helper = NMSMain.getItemDataUtilHelper();
        HashMap<String, NBTTag> data = helper.getData(pick);
        return data.containsKey("level") ? (int) data.get("level").getValue() : -1;
    }

    private static int getPickaxeSubLevel(ItemStack pick) {
        ItemDataUtilHelper helper = NMSMain.getItemDataUtilHelper();
        HashMap<String, NBTTag> data = helper.getData(pick);
        return data != null && data.containsKey("pickaxeLevel") ? (int) data.get("pickaxeLevel").getValue() : -1;
    }

    public static boolean isPickaxe(ItemStack item) {
        return getPickaxeLevel(item) > -1;
    }


    public static ItemStack createPickaxe(int level) {
        if(configuration.getConfigurationSection("Level-" + level) == null) return null;
        ConfigurationSection sec = configuration.getConfigurationSection("Level-" + level);
        ItemStack pick = new ItemStack(getPickaxeMaterial(sec.getString("type")));
        ItemMeta im = pick.getItemMeta();
        ItemDataUtilHelper helper = NMSMain.getItemDataUtilHelper();
        HashMap<String, NBTTag> data = helper.getData(pick);
        List<String> lore = new ArrayList<>();
        for(String s : sec.getStringList("lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s)
                    .replace("%currentxp%", "0")
                    .replace("%maxxp%", sec.getDouble("maxXP") + "")
                    .replace("%progress%", "" + calculatePercentage(0d, sec.getDouble("maxXP")))
            );
        }
        data.put("maxXP", new NBTTag(TagType.DOUBLE, sec.getDouble("maxXP")));
        data.put("currentXP", new NBTTag(TagType.DOUBLE, 0));
        data.put("level", new NBTTag(TagType.INT, level));
        data.put("pickaxeLevel", new NBTTag(TagType.INT, 1));
        data.put("maxLevel", new NBTTag(TagType.INT, sec.getInt("maxLevel")));
        im.setLore(lore);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                sec.getString("displayName").replace("%level%", level + "")));
        pick.setItemMeta(im);
        helper.setData(data);
        pick = helper.finish(pick);
        pick.setDurability((short) 0);
        return pick;
    }

    public static boolean canMine(ItemStack item, Block block) {
        int level = getPickaxeLevel(item);
        ConfigurationSection blocks = configuration.getConfigurationSection("Level-" + level);
        for(String s : blocks.getStringList("canMine"))
            if(s.toLowerCase().equalsIgnoreCase(block.getType().name().toLowerCase())) return true;
        return false;
    }


    public static boolean validateBlock(Block block) {
        ConfigurationSection blocks = configuration.getConfigurationSection("Blocks");
        for(String s : blocks.getKeys(false)) {
            if(Material.matchMaterial(s) == null || !Material.matchMaterial(s).isBlock()) {
                System.out.println("Material: " + s + " is not a valid Block material");
                continue;
            }
            if(s.toLowerCase().equalsIgnoreCase(block.getType().name().toLowerCase())) return true;
        }
        return false;
    }

    public static double getBlockXP(Block block) {
        ConfigurationSection blocks = configuration.getConfigurationSection("Blocks");
        for(String s : blocks.getKeys(false)) {
            if(Material.matchMaterial(s) == null || !Material.matchMaterial(s).isBlock()) {
                System.out.println("Material: " + s + " is not a valid Block material");
                continue;
            }
            if(s.toLowerCase().equalsIgnoreCase(block.getType().name().toLowerCase())) return blocks.getDouble(s);
        }
        return -1;
    }

    public static void forceGiveXP(Player player, double forceXP) {
        if(!isPickaxe(player.getItemInHand())) return;
        ItemStack item = player.getItemInHand();
        int level = getPickaxeLevel(item);
        ConfigurationSection sec = configuration.getConfigurationSection("Level-" + level);
        ItemStack pick = player.getItemInHand();
        ItemMeta im = pick.getItemMeta();
        ItemDataUtilHelper helper = NMSMain.getItemDataUtilHelper();
        HashMap<String, NBTTag> data = helper.getData(pick);
        double xp = ((double) data.get("currentXP").getValue()) + forceXP;
        double maxXp = (double) data.get("maxXP").getValue();
        double maxLevel = (int) data.get("maxLevel").getValue();
        if(xp >= maxXp) {
            if((getPickaxeSubLevel(pick) + 1) > maxLevel) {
                if(createPickaxe(level + 1) != null) {
                    pick = createPickaxe(level + 1);
                    im = pick.getItemMeta();
                    data = helper.getData(pick);
                    sec = configuration.getConfigurationSection("Level-" + (level + 1));
                }
            } else
                data.put("pickaxeLevel", new NBTTag(TagType.INT, (getPickaxeSubLevel(pick) + 1)));
            xp = 0;
        }
        data.put("currentXP", new NBTTag(TagType.DOUBLE, xp));
        List<String> lore = new ArrayList<>();
        for(String s : sec.getStringList("lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s)
                    .replace("%currentxp%", new DecimalFormat("###.##").format(xp))
                    .replace("%maxxp%", sec.getDouble("maxXP") + "")
                    .replace("%progress%", new DecimalFormat("###.##").format(calculatePercentage(xp, sec.getDouble("maxXP"))))
                    .replace("%progressleft%", new DecimalFormat("###.##").format(100 - calculatePercentage(xp, sec.getDouble("maxXP"))))
            );
        }
        im.setLore(lore);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                sec.getString("displayName").replace("%level%", data.get("pickaxeLevel").getValue() + "")));
        pick.setItemMeta(im);
        helper.setData(data);
        pick = helper.finish(pick);
        pick.setDurability((short) 0);
        player.setItemInHand(pick);
    }

    public static boolean handleBlockBreak(Player player, Block block) {
        if(!isPickaxe(player.getItemInHand())) return true;
        ItemStack item = player.getItemInHand();
        int level = getPickaxeLevel(item);
        ConfigurationSection blocks = configuration.getConfigurationSection("Blocks");
        if(!validateBlock(block) || getBlockXP(block) == -1 || !canMine(item, block)) return true;
        ConfigurationSection sec = configuration.getConfigurationSection("Level-" + level);

        ItemStack pick = player.getItemInHand();
        ItemMeta im = pick.getItemMeta();
        ItemDataUtilHelper helper = NMSMain.getItemDataUtilHelper();
        HashMap<String, NBTTag> data = helper.getData(pick);
        double xp = ((double) data.get("currentXP").getValue()) + getBlockXP(block);
        double maxXp = (double) data.get("maxXP").getValue();
        double maxLevel = (int) data.get("maxLevel").getValue();
        if(xp >= maxXp) {
            if((getPickaxeSubLevel(pick) + 1) > maxLevel) {
                if(createPickaxe(level + 1) != null) {
                    pick = createPickaxe(level + 1);
                    im = pick.getItemMeta();
                    data = helper.getData(pick);
                    sec = configuration.getConfigurationSection("Level-" + (level + 1));
                }
            } else
                data.put("pickaxeLevel", new NBTTag(TagType.INT, (getPickaxeSubLevel(pick) + 1)));
            xp = 0;
        }
        data.put("currentXP", new NBTTag(TagType.DOUBLE, xp));
        List<String> lore = new ArrayList<>();
        for(String s : sec.getStringList("lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s)
                    .replace("%currentxp%", new DecimalFormat("###.##").format(xp))
                    .replace("%maxxp%", sec.getDouble("maxXP") + "")
                    .replace("%progress%", new DecimalFormat("###.##").format(calculatePercentage(xp, sec.getDouble("maxXP"))))
                    .replace("%progressleft%", new DecimalFormat("###.##").format(100 - calculatePercentage(xp, sec.getDouble("maxXP"))))
            );
        }
        im.setLore(lore);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                sec.getString("displayName").replace("%level%", data.get("pickaxeLevel").getValue() + "")));
        pick.setItemMeta(im);
        helper.setData(data);
        pick = helper.finish(pick);
        pick.setDurability((short) 0);
        player.setItemInHand(pick);
        return false;
    }
}