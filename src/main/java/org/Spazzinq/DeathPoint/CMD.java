package org.Spazzinq.DeathPoint;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class CMD implements CommandExecutor, TabCompleter {
    private DeathPoint pl;
    CMD(DeathPoint pl) { this.pl = pl; }
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s.hasPermission("deathpoint.admin")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                pl.reloadConf();
                s.sendMessage(pl.cc("&d&lDeathPoint &7» &dConfiguration successfully reloaded!"));
            } else s.sendMessage(pl.cc("&d&lDeathPoint &7» &d/deathpoint reload"));
        } else s.sendMessage(pl.noPerms);
        return false;
    }

    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        return Collections.singletonList("reload");
    }
}
