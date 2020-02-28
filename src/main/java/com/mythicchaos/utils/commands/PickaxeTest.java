package com.mythicchaos.utils.commands;

import com.mythicchaos.levelling.PickaxeLevel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copy Right ©
 * This code is private
 * Owner: PerryPlaysMC
 * From: 11/3/19-2200
 * Package: com.mythicchaos.utils.commands
 * Class: PickaxeText
 * <p>
 * Path: com.mythicchaos.utils.commands.PickaxeText
 * <p>
 * Any attempts to use these program(s) may result in a penalty of up to $1,000 USD
 **/

@SuppressWarnings("all")
public class PickaxeTest implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command command, String cl, String[] args) {
        if(s.isOp() && s instanceof Player) {
            Player p = (Player) s;
            if(args.length == 1) {
                if(PickaxeLevel.isPickaxe(p.getItemInHand())) {
                    PickaxeLevel.forceGiveXP(p, Double.parseDouble(args[0]));
                    s.sendMessage("Updated");
                }
                return true;
            }
            p.getInventory().addItem(PickaxeLevel.createPickaxe(1));
            s.sendMessage("Item obtained");
        }
        return true;
    }
}
