package me.sirgregg.jellylegs;

import me.sirgregg.jellylegs.util.file.LangFileUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class JellyLegs extends JavaPlugin implements Listener {
	private List<Player> jellyEnabled;
	private LangFileUtil lang;

	@Override
	public void onEnable() {
		lang = LangFileUtil.getLang();
		jellyEnabled = new ArrayList<>();

		getServer().getPluginManager().registerEvents(this, this);
		getCommand("jellylegs").setExecutor(this);
	}

	@Override
	public void onDisable() {
		for (Player player : jellyEnabled) {
			player.sendMessage(format(lang.getString("toggled-off-because-of-reload")));
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFallDamageTaken(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && jellyEnabled.contains(player)) {
			e.setCancelled(true);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(format(lang.getString("player-only")));
			return false;
		}
		Player player = (Player) sender;

		if (!player.hasPermission("jellylegs.toggle")) {
			player.sendMessage(format(lang.getString("no-permission")));
			return false;
		}
		toggleJellyLegs(player);

		return false;
	}

	public void toggleJellyLegs(Player player) {
		if (jellyEnabled.contains(player)) { // if it's enabled
			jellyEnabled.remove(player);
			player.sendMessage(format(lang.getString("toggled-off")));
		} else { // if it's not enabled
			jellyEnabled.add(player);
			player.sendMessage(format(lang.getString("toggled-on")));
		}
	}

	private String format(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}
