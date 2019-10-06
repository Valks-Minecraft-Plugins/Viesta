package io.github.sounds;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.valk.Viesta;

public class Sounds implements Listener{
	Viesta plugin;
	
	public Sounds(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();

			World world = target.getLocation().getWorld();
			Location loc = target.getLocation();

			switch (event.getEntityType()) {
			case SPIDER:
				world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 100, 0);
				break;
			default:
				break;
			}
		}

		return false;
	}
}
