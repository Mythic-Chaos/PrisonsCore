package com.mythicchaos.utils.listeners;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.levelling.PickaxeLevel;
import com.mythicchaos.utils.PrisonPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PrisonPlayer player = new PrisonPlayer(event.getPlayer());
        if(!DBManager.inDatabase(player)){
            DBManager.createPlayer(player);
        }
    }

    @EventHandler void onBreak(BlockBreakEvent e) {
        Player player = (e.getPlayer());
        if(PickaxeLevel.isPickaxe(e.getPlayer().getItemInHand()))
            e.setCancelled(PickaxeLevel.handleBlockBreak(player, e.getBlock()));
    }

    @EventHandler void onBlockDamage(BlockDamageEvent e) {
        Player player = (e.getPlayer());
        Block block = e.getBlock();
        if(PickaxeLevel.isPickaxe(e.getPlayer().getItemInHand()))
            if(!PickaxeLevel.validateBlock(block) || PickaxeLevel.getBlockXP(block) == -1 || !PickaxeLevel.canMine(player.getItemInHand(), block))
                e.setCancelled(true);
    }

}
