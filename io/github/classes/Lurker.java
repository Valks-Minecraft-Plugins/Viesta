package io.github.classes;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Lurker implements Listener{
	Viesta plugin;
	
	public Lurker(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private void sprint(PlayerToggleSprintEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			if (event.isSprinting()) {
				if (config.get("class").equals("Lurker") && p.getLevel() >= 20) {
					if (!p.hasPotionEffect(PotionEffectType.JUMP)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 6));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 6));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();

		Block b = loc.getBlock();
		
		if (b.isLiquid()) {
			if (config.get("class").equals("Lurker")) {
				if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3));
				}
			}
		}
		
		long time = b.getWorld().getTime();
		if (b.getLightFromSky() >= 15 && time > 0 && time < 12300) {
			if (config.get("class").equals("Lurker")) {
				if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3));
				}
			}
		}
		if (b.getLightFromSky() > 5 && b.getLightFromSky() < 15) {
			if (config.get("class").equals("Lurker") && p.getLevel() >= 10) {
				if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 3));
				}
			}
		}
		if (b.getLightFromSky() < 5) {
			if (config.get("class").equals("Lurker") && p.getLevel() >= 10) {
				if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3));
				}
			}
			if (config.get("class").equals("Lurker") && p.getLevel() >= 10) {
				if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 3));
				}
			}
		}
	}
	
	@EventHandler
	private boolean entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();

			if (event.getEntity() instanceof ItemFrame) {
				return false;
			}

			if (target instanceof Player) {
				Player p = (Player) event.getEntity();
				PlayerFiles cm = PlayerFiles.getConfig(p);
				FileConfiguration config = cm.getConfig();
				
				if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
					if (config.get("class").equals("Lurker")) {
						if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3));
						}
					}
				}
				return false;
			}
		}
		return false;
	}
}
