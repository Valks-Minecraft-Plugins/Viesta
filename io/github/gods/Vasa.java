package io.github.gods;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Vasa implements Listener {
	Viesta plugin;
	
	public Vasa(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		Material type = event.getBlockPlaced().getType();
		
		if (type.equals(Material.TORCH) || type.equals(Material.REDSTONE_TORCH)) {
			if (config.get("god").equals("Vasa")) {
				event.setCancelled(true);
				plugin.sendActionMessage(p, "The god you chose does not allow you to do that.", "red");
			}
		}

		return true;
	}
}
