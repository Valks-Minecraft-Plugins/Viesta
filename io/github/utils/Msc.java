package io.github.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Msc implements Listener {
	Viesta plugin;

	public Msc(Viesta instance) {
		plugin = instance;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@EventHandler
	private void entityShoot(EntityShootBowEvent event) {
		Player p = (Player) event.getEntity();
		Arrow a = (Arrow) event.getProjectile();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		if (p.getLevel() <= 10) {
			Item i = p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.TNT));
			a.setPassenger(i);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			if (e.getEntity().getPassenger() != null && ((e.getEntity().getPassenger() instanceof Item))) {
				e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.PRIMED_TNT);
				e.getEntity().getPassenger().remove();
				e.getEntity().remove();
			} else {
				e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
			}
		}
	}

	@EventHandler
	private void blockDamageEvent(BlockDamageEvent event) {
		Location loc = event.getBlock().getLocation();
		event.getBlock().getWorld().spawnParticle(Particle.PORTAL, loc, 10);
	}

	@EventHandler
	private boolean onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		Material type = event.getBlockPlaced().getType();

		switch (type) {
		case OBSIDIAN:
			if (p.getLocation().getBlockY() > 10) {
				event.setCancelled(true);
				plugin.sendActionMessage(p,
						"You must be below y level 10 to do that. Current y level: " + p.getLocation().getBlockY(),
						"red");
			}
			break;
		default:
			break;
		}

		if (event.getBlockPlaced().getType().equals(Material.BUCKET)) {
			event.setCancelled(true);
		}

		return true;
	}

	@SuppressWarnings("unused")
	private boolean underRequiredLevel(BlockBreakEvent event, Player player, int level) {
		if (player.getLevel() < level) {
			plugin.sendActionMessage(player, "Required Level: " + level + ", Your Level: " + player.getLevel(),
					"green");
			event.setCancelled(true);
			return true;
		}
		return false;
	}

	@EventHandler
	private boolean entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			if (event.getDamager() instanceof Player) {
				Player p = (Player) event.getDamager();
				p.giveExp(2);
				plugin.updateHealth(p);
			}
		}

		return false;
	}

	@EventHandler
	private void expChange(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		plugin.updateHealth(player);
	}

	@EventHandler
	private void levelChange(PlayerLevelChangeEvent event) {
		if (event.getNewLevel() > 100) {
			event.getPlayer().setLevel(100);
		} else {
			plugin.sendActionMessage(event.getPlayer(), "You're now level " + event.getNewLevel(), "gold");
		}
	}

	@EventHandler
	private void leafDecay(LeavesDecayEvent event) {
		event.getBlock().getDrops().clear();
	}

	@EventHandler
	private void fishing(PlayerFishEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (config.get("class").equals("Vishna") && p.getLevel() >= 20) {
			event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(),
					plugin.item(Material.COOKED_COD, 1, "Squid", "Tastes yummy."));
		}
	}	

	@EventHandler
	private void clickEntity(PlayerInteractEntityEvent event) {
		LivingEntity entity = (LivingEntity) event.getRightClicked();
		if (entity.getType().equals(EntityType.OCELOT)) {
			event.getPlayer().openInventory(event.getPlayer().getEnderChest());
		}
	}

	@EventHandler
	private void explosion(EntityExplodeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void playerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (p.getEquipment().getItemInMainHand().getType() == Material.FEATHER) {
				Vector center = p.getLocation().toVector();
				Vector toThrow = p.getLocation().toVector();

				double x = toThrow.getX() - center.getX();
				double z = toThrow.getZ() - center.getZ();

				Vector v = new Vector(x, 1, z).normalize().multiply(1.02);

				p.setVelocity(v);
			}
		}
	}
}
