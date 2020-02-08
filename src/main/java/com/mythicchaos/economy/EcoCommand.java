package com.mythicchaos.economy;

import com.mythicchaos.MythicChaos;
import com.mythicchaos.utils.PrisonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            if(cmd.getName().equalsIgnoreCase("drachma")){
                PrisonPlayer player = new PrisonPlayer((Player) sender);
                if(player.hasPermission("drachma.admin")){
                    if(args.length == 3) {
                        PrisonPlayer target = new PrisonPlayer(Bukkit.getPlayer(args[1]));
                        if(target != null && target.isOnline()) {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (isInt(args[2])) {
                                    Economy.giveBalance(target, Integer.parseInt(args[2]));
                                    player.sendMessage(MythicChaos.getLanguage().getMessage("givenDrachma").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2]));
                                } else player.sendMessage(MythicChaos.getLanguage().getMessage("notANumber"));
                            } else if(args[0].equalsIgnoreCase("take")){
                                if (isInt(args[2])) {
                                    if(Economy.takeBalance(target, Integer.parseInt(args[2]))) {
                                        player.sendMessage(MythicChaos.getLanguage().getMessage("takenDrachmaSuccessful").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2]));
                                    } else {
                                        player.sendMessage(MythicChaos.getLanguage().getMessage("takenDrachmaUnsuccessful").replaceAll("%player%", target.getName()));
                                    }
                                } else player.sendMessage(MythicChaos.getLanguage().getMessage("notANumber"));
                            } else if(args[0].equalsIgnoreCase("set")){
                                if (isInt(args[2])) {
                                    Economy.setBalance(target, Integer.parseInt(args[2]));
                                    player.sendMessage(MythicChaos.getLanguage().getMessage("setDrachma").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2]));
                                    player.sendMessage(MythicChaos.getLanguage().getMessage("newDrachma").replaceAll("%amount%", args[2]));
                                } else player.sendMessage(MythicChaos.getLanguage().getMessage("notANumber"));
                            }
                        } else player.sendMessage(MythicChaos.getLanguage().getMessage("invalidTarget"));
                    } else player.sendMessage(MythicChaos.getLanguage().getMessage("invalidArguments"));
                } else player.sendMessage(MythicChaos.getLanguage().getMessage("noPermission"));
            }
        }
        return true;
    }

    private boolean isInt(String string){
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
