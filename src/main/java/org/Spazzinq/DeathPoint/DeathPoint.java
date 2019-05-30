/*
 * This file is part of DeathPoint, which is licensed under the MIT License
 *
 * Copyright (c) 2019 Spazzinq
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
