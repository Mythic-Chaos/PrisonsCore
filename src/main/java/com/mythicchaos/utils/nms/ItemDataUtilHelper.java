package com.mythicchaos.utils.nms;

import com.mythicchaos.utils.inventory.NBTTag;
import org.bukkit.entity.Player;

import java.util.HashMap;

public interface ItemDataUtilHelper {

    HashMap<String, NBTTag> getData(org.bukkit.inventory.ItemStack stack);
    
    void setData(HashMap<String, NBTTag> newData);
    
    org.bukkit.inventory.ItemStack clearData(org.bukkit.inventory.ItemStack stack);
    
    org.bukkit.inventory.ItemStack finish(org.bukkit.inventory.ItemStack i);
    
    org.bukkit.inventory.ItemStack damage(Player player, org.bukkit.inventory.ItemStack itemStack, int amount);

}
