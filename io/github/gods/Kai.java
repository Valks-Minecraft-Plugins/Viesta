package io.github.gods;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Kai implements Listener {
	Viesta plugin;
	
	public Kai(Viesta instance) {
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
			if (config.get("god").equals("Kai")) {
				plugin.sunEffects(p);
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
				return false;
			} else {
				if (event.getDamager() instanceof Player) {
					Player p = (Player) event.getDamager();
					PlayerFiles cm = PlayerFiles.getConfig(p);
					FileConfiguration config = cm.getConfig();
					if (config.get("god").equals("Kai")) {
						if (!p.hasPotionEffect(PotionEffectType.CONFUSION)) {
							target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
						}
					}
				}
			}
		}

		return false;
	}
}
