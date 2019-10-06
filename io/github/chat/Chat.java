package io.github.chat;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Chat implements Listener {
	Viesta plugin;

	public Chat(Viesta instance) {
		plugin = instance;
	}

	@EventHandler
	private void playerChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (config.get("god") == null) {
			config.set("god", "NONE");
		}
		if (config.get("class") == null) {
			config.set("class", "NONE");
		}
		if (config.get("trait") == null) {
			config.set("trait", "NONE");
		}
		Location loc = event.getPlayer().getLocation();

		String name = event.getPlayer().getDisplayName();
		String message = event.getMessage();
		String[] badwords = { "fuck", "nigger", "cunt", "cunts", "bitch", "whore", "slut", "motherfucker", "fucker",
				"blowjob", "dick", "kunt", "faggot", "niglet", "prick" };
		String chatColor = config.getString("color");

		String msg = plugin.getConfig().getString("USER-CHAT-FORMAT");

		String change1 = msg.replace("%player%", name);
		String change2 = change1.replace("%message%", message);
		String change3 = change2.replace("#color#", chatColor);
		String change4 = change3.replaceAll("%", "%%");

		String msgColor = ChatColor.translateAlternateColorCodes('&', change4);

		String prefix = "";

		p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 20);
		
		switch(config.getString("god").toLowerCase()) {
		case "kai":
			prefix = "&8{&bKai&8}";
			break;
		case "kuu":
			prefix = "&8{&bKuu&8}";
			break;
		case "vasa":
			prefix = "&8{&bVasa&8}";
			break;
		case "sakura":
			prefix = "&8{&bSakura&8}";
			break;
		case "valkyrie":
			prefix = "&8{&bValkyrie&8}";
			break;
		default:
			prefix = "&8{&7Godless&8}";
			break;
		}

		switch (config.getString("rank")) {
		case "owner":
			prefix = "&8{&bQueen Ni&8}";
			break;
		case "coowner":
			prefix = "&8{&3Co&8-&3Owner&8}";
			break;
		case "developer":
			prefix = "&8{&3Dev&8}";
			break;
		case "admin":
			prefix = "&8{&3Admin&8}";
			break;
		case "moderator":
			prefix = "&8{&3Mod&8}";
			break;
		case "builder":
			prefix = "&8{&3Builder&8}";
			break;
		case "helper":
			prefix = "&8{&3Helper&8}";
			break;
		case "donator":
			prefix = "&8{&bDonator&8}";
			break;
		case "furry":
			prefix = "&8{&3Furry Kitten&8}";
			break;
		case "senior":
			prefix = "&8{&3Survivor&8}";
			break;
		default:
			break;
		}

		String prefixColor = ChatColor.translateAlternateColorCodes('&', prefix);

		String suffix = "";

		switch (config.getString("class")) {
		case "Shadow":
			suffix = "&8{&7Sh" + p.getLevel() + "&8} ";
			break;
		case "Blazer":
			suffix = "&8{&7Bl" + p.getLevel() + "&8} ";
			break;
		case "Lurker":
			suffix = "&8{&7Lu" + p.getLevel() + "&8} ";
			break;
		case "Wizard":
			suffix = "&8{&7Wi" + p.getLevel() + "&8} ";
			break;
		case "Summoner":
			suffix = "&8{&7Su" + p.getLevel() + "&8} ";
			break;
		case "Xysta":
			suffix = "&8{&7Xy" + p.getLevel() + "&8} ";
			break;
		case "Beserker":
			suffix = "&8{&7Be" + p.getLevel() + "&8} ";
			break;
		default:
			suffix = "&8{&7" + p.getLevel() + "&8} ";
			break;
		}

		String suffixColor = ChatColor.translateAlternateColorCodes('&', suffix);

		for (String s : badwords) {
			String check = s.toLowerCase();
			if (message.toLowerCase().contains(check)) {
				String clean = msgColor.toLowerCase().replaceAll(check, "meoowww");
				event.setFormat(prefixColor + suffixColor + clean);
			} else {
				event.setFormat(prefixColor + suffixColor + msgColor);
			}
		}
	}
}
