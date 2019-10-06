package io.github.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;

import io.github.valk.Viesta;

public class Particles implements Listener{
	Viesta plugin;
	
	public Particles(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();

			if (event.getEntity() instanceof ItemFrame) {
				return false;
			}

			World world = target.getLocation().getWorld();

			world.spawnParticle(Particle.LAVA, target.getLocation(), 10);
			world.spawnParticle(Particle.VILLAGER_ANGRY, target.getLocation(), 1);
		}

		return false;
	}
	
	@EventHandler
	private void craftEvent(CraftItemEvent event) {
		Location loc = event.getWhoClicked().getLocation();
		event.getWhoClicked().getWorld().spawnParticle(Particle.CRIT, loc, 25);
		event.getWhoClicked().getWorld().spawnParticle(Particle.PORTAL, loc, 50);
	}
}
