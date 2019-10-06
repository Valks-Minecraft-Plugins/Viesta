package io.github.classes;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Wizard implements Listener{
	Viesta plugin;
	
	public Wizard(Viesta instance) {
		plugin = instance;
	}
	
	private void shootFireball(Player p) {
		Fireball proj = p.getWorld().spawn(p.getEyeLocation(), Fireball.class);
		proj.setVelocity(p.getLocation().getDirection().multiply(1.5));
		proj.setIsIncendiary(false);
		proj.setShooter(p);
	}

	private void shootArrow(Player p) {
		Arrow proj = p.getWorld().spawn(p.getEyeLocation(), Arrow.class);
		proj.setVelocity(p.getLocation().getDirection().multiply(1.5));
		proj.setShooter(p);
	}

	private void shootSnowball(Player p) {
		Snowball proj = p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
		proj.setVelocity(p.getLocation().getDirection().multiply(1.5));
		proj.setShooter(p);
	}

	private void shootLightning(Player p) {
		double x = p.getTargetBlock((Set<Material>) null, 200).getX();
		double y = p.getTargetBlock((Set<Material>) null, 200).getY();
		double z = p.getTargetBlock((Set<Material>) null, 200).getZ();
		Location loc = new Location(p.getWorld(), x, y + 2, z);
		p.getWorld().strikeLightning(loc);
	}
	
	@EventHandler
	private void playerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Material material = p.getEquipment().getItemInMainHand().getType();
			ItemStack item = p.getEquipment().getItemInMainHand();
			ItemMeta meta = p.getEquipment().getItemInMainHand().getItemMeta();

			if (config.get("class").equals("Wizard")) {
				if (item.hasItemMeta()) {
					if (meta.getDisplayName().contains("Wizard")) {
						switch (material) {
						case BLAZE_ROD:
							if (p.getLevel() >= 100) {
								shootLightning(p);
							} else if (p.getLevel() >= 80) {
								if (plugin.cooldown(p, 6)) {
									shootLightning(p);
								}
							} else if (p.getLevel() >= 70) {
								if (plugin.cooldown(p, 8)) {
									shootLightning(p);
								}
							} else if (p.getLevel() >= 60) {
								if (plugin.cooldown(p, 10)) {
									shootLightning(p);
								}
							}
							break;
						case BONE:
							if (p.getLevel() >= 90) {
								shootFireball(p);
							} else if (p.getLevel() >= 80) {
								if (plugin.cooldown(p, 3)) {
									shootFireball(p);
								}
							} else if (p.getLevel() >= 70) {
								if (plugin.cooldown(p, 4)) {
									shootFireball(p);
								}
							} else if (p.getLevel() >= 40) {
								if (plugin.cooldown(p, 5)) {
									shootFireball(p);
								}
							}
							break;
						case ARROW:
							if (p.getLevel() >= 30) {
								shootArrow(p);
							} else if (p.getLevel() >= 20) {
								if (plugin.cooldown(p, 2)) {
									shootArrow(p);
								}
							}
							break;
						case STICK:
							if (p.getLevel() >= 15) {
								shootSnowball(p);
							} else if (p.getLevel() >= 10) {
								if (plugin.cooldown(p, 1)) {
									shootSnowball(p);
								}
							}
							break;
						default:
							break;

						}
					}
				}
			}
		}
	}
}
