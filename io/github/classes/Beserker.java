package io.github.classes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Beserker implements Listener{
	Viesta plugin;
	
	public Beserker(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();

			if (target instanceof Player) {
				Player p = (Player) event.getEntity();
				PlayerFiles cm = PlayerFiles.getConfig(p);
				FileConfiguration config = cm.getConfig();
				
				if (config.get("class").equals("Beserker") && p.getLevel() >= 20) {
					if (p.getHealth() < p.getHealth() / 3) {
						if (!p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 3));
						}
					}
				}
				return false;
			} else {
				if (event.getDamager() instanceof Player) {
					Player p = (Player) event.getDamager();
					PlayerFiles cm = PlayerFiles.getConfig(p);
					FileConfiguration config = cm.getConfig();
					if (config.get("class").equals("Beserker") && p.getLevel() >= 10) {
						if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
							target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 0));
						}
					}
				}
			}
		}

		return false;
	}
}
