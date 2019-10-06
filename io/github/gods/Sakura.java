package io.github.gods;

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

public class Sakura implements Listener {
	Viesta plugin;
	
	public Sakura(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		Block b = loc.getBlock();
		Biome biome = b.getBiome();
		
		if (b.getLightFromSky() > 5 && b.getLightFromSky() < 15) {
			if (config.get("god").equals("Sakura")) {
				if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 3));
				}
			}
		}
		
		if (config.get("god").equals("Sakura")) {
			switch (biome) {
			case ICE_SPIKES:
			case DESERT:
			case DESERT_HILLS:
				if (!p.hasPotionEffect(PotionEffectType.SLOW)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 0));
				}
				break;
			default:
				break;
			}
		}
	}
}
