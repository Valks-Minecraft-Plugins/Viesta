package io.github.classes;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Xysta implements Listener{
	Viesta plugin;
	
	public Xysta (Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean move(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		Block b = loc.getBlock();
		Biome biome = b.getBiome();

		if (config.get("class").equals("Xysta") && p.getLevel() >= 40) {
			switch (biome) {
			case DESERT:
			case DESERT_HILLS:
				if (!p.hasPotionEffect(PotionEffectType.SATURATION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 600, 3));
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 3));
				}
				break;
			default:
				break;
			}
		}
		
		return true;
	}
}
