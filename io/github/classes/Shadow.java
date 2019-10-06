package io.github.classes;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Shadow implements Listener{
	Viesta plugin;
	
	public Shadow(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void nightEffects (PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Location loc = p.getLocation();
		Block b = loc.getBlock();
		
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		
		if (b.isLiquid()) {
			if (config.get("class").equals("Shadow")) {
				if (p.getLevel() < 5) {
					waterPoisonEffects(p);
				}
			} else {
				waterPoisonEffects(p);
			}
		}
		
		if (b.getLightFromSky() > 5 && b.getLightFromSky() < 15) {
			if (config.get("class").equals("Shadow") && p.getLevel() >= 40) {
				if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 500, 1));
					p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation(), 10);
				}
			}
		}

	}
	
	@EventHandler
	private void sprint(PlayerToggleSprintEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			if (event.isSprinting()) {
				if (config.get("class").equals("Shadow")) {
					int level = p.getLevel();
					if (level >= 50) {
						if (!p.hasPotionEffect(PotionEffectType.JUMP)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 220, 3));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 220, 1));
						}
					} else if (level >= 20) {
						if (!p.hasPotionEffect(PotionEffectType.JUMP)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 220, 2));
						}
					}

				}

			}
		}
	}
	
	@EventHandler
	private void sneak(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			if (event.isSneaking()) {
				if (!p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
				}
				if (config.get("class").equals("Shadow") && p.getLevel() >= 10) {
					if (!p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 2));
					}
				}

			}
		}
	}
	
	private void waterPoisonEffects(Player p) {
		if (!p.hasPotionEffect(PotionEffectType.POISON)) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0));
			p.getWorld().spawnParticle(Particle.CRIT, p.getLocation(), 10);
			plugin.sendActionMessage(p, "You were poisoned by water..", "red");
		}
	}
}
