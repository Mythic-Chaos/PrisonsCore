package com.mythicchaos.utils.listeners;

import com.mythicchaos.utils.DBManager;
import com.mythicchaos.utils.PickaxeLevel;
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
        PickaxeLevel p = new PickaxeLevel(player, player.getItemInHand());
        if(PickaxeLevel.isPickaxe(e.getPlayer().getItemInHand()))
            e.setCancelled(p.breakBlockCancelled(e.getBlock()));
    }

    @EventHandler void onBlockDamage(BlockDamageEvent e) {
        Player player = (e.getPlayer());
        PickaxeLevel p = new PickaxeLevel(player, player.getItemInHand());
        Block block = e.getBlock();
        if(PickaxeLevel.isPickaxe(e.getPlayer().getItemInHand()))
            if(!p.blockExists(block) || p.getXP(block) == -1 || !p.canBreak(block))
                e.setCancelled(true);
    }

}
