package io.github.configs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class LoadPlayerFiles implements Listener{
	Viesta plugin;
	
	public LoadPlayerFiles(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void loadFiles(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p.getUniqueId());
		if (!p.hasPlayedBefore()) {
			FileConfiguration f = cm.getConfig();
			f.set("name", p.getName());
			f.set("nick", "NONE");
			f.set("uuid", p.getUniqueId().toString());
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
			f.set("join_date", format.format(now));
			f.set("last_join", format.format(now));
			f.set("rank", "NONE");
			f.set("god", "NONE");
			f.set("class", "NONE");
			f.set("trait", "NONE");
			f.set("color", "&7");
			cm.saveConfig();
		} else {
			FileConfiguration f = cm.getConfig();
			f.set("name", p.getName());
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
			f.set("last_join", format.format(now));
			cm.saveConfig();
		}
	}
}
