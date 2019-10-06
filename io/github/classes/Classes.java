package io.github.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;
import net.md_5.bungee.api.ChatColor;

public class Classes implements Listener{
	Viesta plugin;
	
	public Classes(Viesta instance) {
		plugin = instance;
	}
	
	private void checkBiomeLevel(Player p, int requiredLevel, String biome) {
		if (p.getLevel() < requiredLevel) {
			if (!p.hasPotionEffect(PotionEffectType.WITHER)) {
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.getWorld().strikeLightningEffect(p.getLocation());
				plugin.sendTitle(p, biome, "Level " + requiredLevel + " Required", "red");
				p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 3));
			}
		}
	}
	
	@EventHandler
	private void bedEnter(PlayerBedEnterEvent event) {
		Player p = event.getPlayer();
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
	}

	@EventHandler
	private void bedLeave(PlayerBedLeaveEvent event) {
		Player p = event.getPlayer();
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 5000, 3));
		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5000, 0));
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Location loc = p.getLocation();
		World w = p.getWorld();
		Block b = loc.getBlock();
		Biome biome = b.getBiome();
		
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		
		switch (config.getString("class")) {
		case "Blazer":
			w.spawnParticle(Particle.LAVA, loc, 1);
			break;
		case "Shadow":
			w.spawnParticle(Particle.PORTAL, loc, 1);
			break;
		case "Wizard":
			w.spawnParticle(Particle.SPELL, loc, 1);
			break;
		case "Summoner":
			w.spawnParticle(Particle.CLOUD, loc, 1);
			break;
		case "Xysta":
			w.spawnParticle(Particle.REDSTONE, loc, 1);
			break;
		case "Beserker":
			w.spawnParticle(Particle.CRIT, loc, 1);
			break;
		case "Vishna":
			w.spawnParticle(Particle.FLAME, loc, 1);
			break;
		default:
			break;
		}
		
		int y_level = b.getY();
		if (y_level <= 30) {
			checkBiomeLevel(p, 50, "Caverns");
		} else if (y_level <= 15) {
			checkBiomeLevel(p, 90, "Hell");
		}
		
		if (config.get("class").equals("Xysta") && p.getLevel() >= 10) {

		} else {
			switch (biome) {
			case DESERT:
			case DESERT_HILLS:
				checkBiomeLevel(p, 30, "The Heat");
				break;
			case ICE_SPIKES:
			case TAIGA:
			case TAIGA_HILLS:
			case TAIGA_MOUNTAINS:
				checkBiomeLevel(p, 50, "The Cold");
				break;
			case JUNGLE:
			case JUNGLE_EDGE:
			case JUNGLE_HILLS:
				checkBiomeLevel(p, 40, "The Dark");
				break;
			case GIANT_SPRUCE_TAIGA_HILLS:
			case GIANT_TREE_TAIGA_HILLS:
				checkBiomeLevel(p, 20, "The Bright");
				break;
			default:
				break;
			}
		}
		
		if (config.get("trail") != "NONE") {
			try {
				w.spawnParticle(Particle.valueOf(config.getString("trail")), loc, 30);
			} catch (Exception err) {
				config.set("trail", "NONE");
				p.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + err);
			}
		}

		if (b.getType().equals(Material.NETHER_PORTAL)) {
			p.getWorld().spawnParticle(Particle.LAVA, p.getLocation(), 20);
		}

		Block block = p.getLocation().getBlock();
		switch (block.getType()) {
		case CHORUS_FLOWER:
		case SUNFLOWER:
		case TALL_GRASS:
		case SNOW:
			block.setType(Material.AIR);
			break;
		default:
			break;
		}
		Block block_below = p.getLocation().subtract(0, 1, 0).getBlock();
		switch (block_below.getType()) {
		case OAK_LEAVES:
		case ICE:
			block_below.setType(Material.AIR);
			break;
		case SAND:
			if (config.get("class") != "Xysta" && p.getLevel() >= 5) {
				if (block_below.getBiome().equals(Biome.DESERT)) {
					block_below.setType(Material.AIR);
				}
			}
			break;
		default:
			break;
		}
	}
}
