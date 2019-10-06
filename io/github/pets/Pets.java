package io.github.pets;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.github.valk.Viesta;

public class Pets implements Listener {
	Viesta plugin;

	public Pets(Viesta instance) {
		plugin = instance;
	}

	HashMap<UUID, UUID> cats = new HashMap<UUID, UUID>();
	HashMap<UUID, UUID> wolfs = new HashMap<UUID, UUID>();
	
	@EventHandler
	private void entityDamaged(EntityDamageByEntityEvent event) {
		switch (event.getEntityType()) {
		case WOLF:
			Wolf wolf = (Wolf) event.getEntity();
			if (!wolf.isAdult()) {
				event.setCancelled(true);
			}
			break;
		case OCELOT:
			Ocelot cat = (Ocelot) event.getEntity();
			if (!cat.isAdult()) {
				event.setCancelled(true);
			}
			break;
		default:
			break;
		}
	}
	
	@EventHandler
	public void teleport(PlayerTeleportEvent event) {
		if (cats.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity e = (LivingEntity) Bukkit.getEntity(cats.get(event.getPlayer().getUniqueId()));
			e.teleport(event.getTo());
		}
		if (wolfs.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity wolf = (LivingEntity) Bukkit.getEntity(wolfs.get(event.getPlayer().getUniqueId()));
			wolf.teleport(event.getTo());
		}

	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		if (cats.containsKey(event.getPlayer().getUniqueId())) {
			Bukkit.getEntity(cats.get(event.getPlayer().getUniqueId())).remove();
			cats.remove(event.getPlayer().getUniqueId());
			spawnCatPet(event.getPlayer());
		}

		if (wolfs.containsKey(event.getPlayer().getUniqueId())) {
			Bukkit.getEntity(wolfs.get(event.getPlayer().getUniqueId())).remove();
			wolfs.remove(event.getPlayer().getUniqueId());
			spawnWolfPet(event.getPlayer());
		}
	}

	@EventHandler
	private void playerDeath(PlayerDeathEvent event) {
		if (cats.containsKey(event.getEntity().getUniqueId())) {
			Bukkit.getEntity(cats.get(event.getEntity().getUniqueId())).remove();
			cats.remove(event.getEntity().getUniqueId());
			spawnCatPet(event.getEntity());
		}

		if (wolfs.containsKey(event.getEntity().getUniqueId())) {
			Bukkit.getEntity(wolfs.get(event.getEntity().getUniqueId())).remove();
			wolfs.remove(event.getEntity().getUniqueId());
			spawnWolfPet(event.getEntity());
		}
	}

	@EventHandler
	private void playerSpawn(PlayerRespawnEvent event) {
		spawnCatPet(event.getPlayer());

		spawnWolfPet(event.getPlayer());
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		/*if (cats.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity cat = (LivingEntity) Bukkit.getEntity(cats.get(event.getPlayer().getUniqueId()));
			Player player = event.getPlayer();
			Location playerl = player.getLocation();
			Location catl = cat.getLocation();
			if (playerl.distance(catl) >= 20) {
				cat.teleport(player);
			}
		}
		if (wolfs.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity wolf = (LivingEntity) Bukkit.getEntity(wolfs.get(event.getPlayer().getUniqueId()));
			Player player = event.getPlayer();
			Location playerl = player.getLocation();
			Location wolfl = wolf.getLocation();
			if (playerl.distance(wolfl) >= 20) {
				wolf.teleport(player);
			}
		}*/
	}
	
	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent event) {
		if (cats.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity e = (LivingEntity) Bukkit.getEntity(cats.get(event.getPlayer().getUniqueId()));
			e.setHealth(0);
			cats.remove(event.getPlayer().getUniqueId());
		}

		if (wolfs.containsKey(event.getPlayer().getUniqueId())) {
			LivingEntity wolf = (LivingEntity) Bukkit.getEntity(wolfs.get(event.getPlayer().getUniqueId()));
			wolf.setHealth(0);
			wolfs.remove(event.getPlayer().getUniqueId());
		}
	}

	private void spawnCatPet(Player p) {
		Ocelot cat = (Ocelot) p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
		cat.setCatType(Type.BLACK_CAT);
		cat.setBaby();
		cat.setInvulnerable(true);
		cat.setCustomNameVisible(false);
		cat.setAgeLock(false);
		cat.setOwner(p);
		cat.setTamed(true);
		cats.put(p.getUniqueId(), cat.getUniqueId());
	}

	private void spawnWolfPet(Player p) {
		Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
		wolf.setOwner(p);
		wolf.setTamed(true);
		wolf.setInvulnerable(true);
		wolf.setAgeLock(true);
		wolf.setBaby();
		wolf.setCollarColor(DyeColor.LIGHT_BLUE);
		wolfs.put(p.getUniqueId(), wolf.getUniqueId());
	}

	
}