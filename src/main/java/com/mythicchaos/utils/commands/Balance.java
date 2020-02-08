package com.mythicchaos.utils.commands;

import com.mythicchaos.MythicChaos;
import com.mythicchaos.economy.Economy;
import com.mythicchaos.utils.PrisonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
        if(sender instanceof Player){
            if(cmd.getName().equalsIgnoreCase("balance")){
                PrisonPlayer player = new PrisonPlayer((Player) sender);
                if(args.length == 0){
                    player.sendMessage(MythicChaos.getLanguage().getMessage("yourBalance")
                            .replaceAll("%dollars%", String.valueOf(player.getMoney()))
                            .replaceAll("%drachma%", String.valueOf(Economy.getBalance(player))));
                } else if(args.length == 1){
                    if(Bukkit.getPlayer(args[0]) != null) {
                        PrisonPlayer target = new PrisonPlayer(Bukkit.getPlayer(args[0]));
                        player.sendMessage(MythicChaos.getLanguage().getMessage("othersBalance")
                                .replaceAll("%dollars%", String.valueOf(target.getMoney()))
                                .replaceAll("%drachma%", String.valueOf(Economy.getBalance(target))));
                    } else {
                        player.sendMessage(MythicChaos.getLanguage().getMessage("invalidTarget"));
                    }
                } else {
                    player.sendMessage(MythicChaos.getLanguage().getMessage("invalidArguments"));
                }
            }
        }
        return true;
    }

}
