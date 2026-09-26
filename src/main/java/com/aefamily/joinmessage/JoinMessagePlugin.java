// src/main/java/com/example/joinmessage/JoinMessagePlugin.java
package com.aefamily.joinmessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class JoinMessagePlugin extends JavaPlugin implements Listener {

    private static final int TITLE_FADE_IN = 10;
    private static final int TITLE_STAY = 100;
    private static final int TITLE_FADE_OUT = 10;

    @Override
    public void onEnable() {
        // Save default config if not present
        saveDefaultConfig();
        // Register this class as listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("JoinMessagePlugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("JoinMessagePlugin disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        handleJoinBroadcast(event, player);
        sendWelcomeTitle(player);
        sendSectionLines(player, "welcome-message", Arrays.asList(
                "&aChào mừng quay trở lại, &e%player%&a!",
                "&7Chúc bạn có những trải nghiệm tuyệt vời!"
        ));
        sendSectionLines(player, "server-news", Arrays.asList(
                "&6★ &eTin tức mới nhất của máy chủ:",
                "&7- &fSử dụng &b/kit &fđể nhận quà hàng ngày.",
                "&7- &fTham gia Discord: &bdiscord.gg/example"
        ));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        boolean enabled = getConfig().getBoolean("leave-broadcast.enabled", true);
        if (!enabled) {
            event.setQuitMessage(null);
            return;
        }

        String message = formatMessage("leave-broadcast.message",
                "&c[-] &e%player% &cđã rời khỏi máy chủ.",
                player);
        event.setQuitMessage(message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("joinmessage")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("joinmessage.reload")) {
                    sender.sendMessage(ChatColor.RED + "Bạn không có quyền sử dụng lệnh này.");
                    return true;
                }
                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "JoinMessagePlugin configuration reloaded.");
                return true;
            }
            sender.sendMessage(ChatColor.YELLOW + "Usage: /joinmessage reload");
            return true;
        }
        return false;
    }

    private void handleJoinBroadcast(PlayerJoinEvent event, Player player) {
        boolean enabled = getConfig().getBoolean("join-broadcast.enabled", true);
        if (!enabled) {
            event.setJoinMessage(null);
            return;
        }

        String message = formatMessage("join-broadcast.message",
                "&a[+] &e%player% &ađã tham gia máy chủ!",
                player);
        event.setJoinMessage(message);
    }

    private void sendWelcomeTitle(Player player) {
        boolean enabled = getConfig().getBoolean("welcome-title.enabled", true);
        if (!enabled) {
            return;
        }

        String title = formatMessage("welcome-title.title", "&aChào mừng!", player);
        String subtitle = formatMessage("welcome-title.subtitle", "&e%player% &avừa tham gia.", player);
        player.sendTitle(title, subtitle, TITLE_FADE_IN, TITLE_STAY, TITLE_FADE_OUT);
    }

    private void sendSectionLines(Player player, String section, List<String> fallbackLines) {
        boolean enabled = getConfig().getBoolean(section + ".enabled", true);
        if (!enabled) {
            return;
        }

        List<String> lines = getConfig().getStringList(section + ".lines");
        if (lines == null || lines.isEmpty()) {
            lines = fallbackLines;
        }

        for (String line : lines) {
            if (line == null) {
                continue;
            }
            if (line.trim().isEmpty()) {
                player.sendMessage("");
            } else {
                player.sendMessage(applyColors(replacePlaceholders(line, player)));
            }
        }
    }

    private String formatMessage(String path, String defaultValue, Player player) {
        String raw = getConfig().getString(path, defaultValue);
        if (raw == null || raw.isEmpty()) {
            raw = defaultValue;
        }
        return applyColors(replacePlaceholders(raw, player));
    }

    private String replacePlaceholders(String input, Player player) {
        return input.replace("%player%", player.getName());
    }

    private String applyColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
