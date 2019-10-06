package io.github.classes;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Summoner implements Listener{
	Viesta plugin;
	
	public Summoner(Viesta instance) {
		plugin = instance;
	}

	private void summonWolf(Player p, int amount) {
		p.sendMessage(plugin.prefix("d") + "You summoned " + amount + " wolfs!");
		for (int n = 0; n < amount; n++) {
			Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
			wolf.setOwner(p);
			wolf.setTamed(true);
			wolf.setAdult();
			wolf.setAngry(true);
			wolf.setCollarColor(DyeColor.LIGHT_BLUE);
			wolf.setCustomName(ChatColor.translateAlternateColorCodes('&', "&eWolf Helper"));
			wolf.setSilent(true);
			wolf.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6000, 0));
		}
	}

	private void summonGolem(Player p, int amount) {
		p.sendMessage(plugin.prefix("d") + "You summoned " + amount + " golems!");
		for (int n = 0; n < amount; n++) {
			Golem golem = (Golem) p.getWorld().spawnEntity(p.getLocation(), EntityType.IRON_GOLEM);
			golem.setSilent(true);
			golem.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6000, 0));
		}
	}

	private void summonChestedHorse(Player p, Material armor) {
		Horse horse = (Horse) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
		horse.setAdult();
		horse.setOwner(p);
		horse.setLeashHolder(p);
		horse.setTamed(true);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.getInventory().setArmor(new ItemStack(armor));
		p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.LEGACY_LEASH));
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

			if (config.get("class").equals("Summoner")) {
				if (item.hasItemMeta()) {
					if (meta.getDisplayName().contains("Summoner")) {
						switch (material) {
						case ARROW:
							if (p.getLevel() >= 80) {
								if (plugin.cooldown(p, 60)) {
									summonGolem(p, 3);
								}
							} else if (p.getLevel() >= 70) {
								if (plugin.cooldown(p, 60)) {
									summonGolem(p, 2);
								}
							} else if (p.getLevel() >= 60) {
								if (plugin.cooldown(p, 60)) {
									summonGolem(p, 1);
								}
							}
							break;
						case BLAZE_ROD:
							if (p.getLevel() >= 50) {
								if (plugin.cooldown(p, 180)) {
									summonChestedHorse(p, Material.LEGACY_GOLD_BARDING);
								}
							} else if (p.getLevel() >= 40) {
								if (plugin.cooldown(p, 180)) {
									summonChestedHorse(p, Material.LEGACY_IRON_BARDING);
								}
							}
							break;
						case BONE:
							if (p.getLevel() >= 30) {
								if (plugin.cooldown(p, 15)) {
									summonWolf(p, 3);
								}
							} else if (p.getLevel() >= 20) {
								if (plugin.cooldown(p, 15)) {
									summonWolf(p, 2);
								}
							} else if (p.getLevel() >= 10) {
								if (plugin.cooldown(p, 15)) {
									summonWolf(p, 1);
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
