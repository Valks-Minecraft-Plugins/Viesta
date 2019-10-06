package io.github.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Blazer implements Listener{
	Viesta plugin;
	
	public Blazer(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		Block b = loc.getBlock();
		
		long time = b.getWorld().getTime();
		if (b.getLightFromSky() >= 15 && time > 0 && time < 12300) {
			if (config.get("class").equals("Blazer")) {
				int level = p.getLevel();
				if (level >= 60) {
					if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 500, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 500, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 500, 3));
					}
				} else if (level >= 50) {
					if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 500, 3));
					}
				} else if (level >= 40) {
					if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 2));
					}
				} else if (level >= 30) {
					if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 1));
					}
				}
			}
		}
	}
	
	@EventHandler
	private boolean onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		if (config.get("class").equals("Blazer")) {
			if (p.getLevel() < 20) {
				if (event.getBlockPlaced().getType().equals(Material.WHEAT_SEEDS)) {
					if (p.getLocation().getBlockY() > 40) {
						event.setCancelled(true);
						plugin.sendActionMessage(p, "You must be below y level 40 to do that. Current y level: "
								+ p.getLocation().getBlockY(), "red");
					}
				}
			}
		} else {
			if (event.getBlockPlaced().getType().equals(Material.WHEAT_SEEDS)) {
				if (p.getLocation().getBlockY() > 40) {
					event.setCancelled(true);
					plugin.sendActionMessage(p,
							"You must be below y level 40 to do that. Current y level: " + p.getLocation().getBlockY(),
							"red");
				}
			}
		}

		return true;
	}
}
