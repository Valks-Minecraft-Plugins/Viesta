package io.github.gods;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Kuu implements Listener {
	Viesta plugin;
	
	public Kuu(Viesta instance) {
		plugin = instance;
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
					if (config.get("god").equals("Kuu")) {
						if (!p.hasPotionEffect(PotionEffectType.HARM)) {
							target.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 80, 0));
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
						}
					}
				}
			}
		}

		return false;
	}
}
