package org.Spazzinq.DeathPoint;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

@SuppressWarnings("ConstantConditions")
public class DeathPoint extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    String noPerms;
    private String hover;

    public void onEnable() {
        saveDefaultConfig();
        reloadConf();
        getCommand("deathpoint").setExecutor(new CMD(this));
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    void reloadConf() {
        reloadConfig();
        noPerms = cc(getConfig().getString("no_permission"));
        hover = "";
        for (String line : getConfig().getStringList("hover"))
            hover = hover.concat(line).concat("\n");
        hover = cc(hover);
    }
    @EventHandler private void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (!p.hasPermission("deathpoint.disable")) {
            TextComponent death = new TextComponent(e.getDeathMessage());
            Location l = p.getLocation();
            death.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover
                    .replaceAll("%x%", l.getBlockX() + "")
                    .replaceAll("%y%", l.getBlockY() + "")
                    .replaceAll("%z%", l.getBlockZ() + "")
                    .replaceAll("%world%", l.getWorld().getName())
                    .replaceAll("%player%", p.getName())).create()));
            Bukkit.getServer().spigot().broadcast(death);
            e.setDeathMessage(null);
        }
    }

    String cc(String s) { return ChatColor.translateAlternateColorCodes('&', s); }
}
