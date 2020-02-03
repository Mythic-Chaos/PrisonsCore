package com.mythicchaos.utils;

import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PrisonPlayer extends CraftPlayer implements Listener {

    public PrisonPlayer(Player player){
        super((CraftServer) player.getServer(), ((CraftPlayer) player).getHandle());
    }

}
