package io.github.blocks;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class CropDrops implements Listener{
	Viesta plugin;
	
	public CropDrops(Viesta instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void blockEvent(BlockBreakEvent event) {
		Block b = event.getBlock();
		Material bt = b.getType();
		Location loc = b.getLocation();
		World w = loc.getWorld();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		cm.getConfig();
		EntityEquipment equipment = p.getEquipment();
		equipment.getItemInMainHand();

		/*
		 * Get drops from crops.
		 */
		switch (bt) {
		case WHEAT:
			if (b.getData() == 7) {
				plugin.dropItem(b, 1, plugin.item(Material.BEETROOT_SOUP, 1, "Mysterious Soup", "Restore hunger.."));
				plugin.dropItem(b, 1, plugin.item(Material.BEETROOT_SEEDS, 2, "Powerful Essence", "Grow for Power"));
				w.spawnParticle(Particle.SLIME, loc, 30);
			}
			break;
		case PUMPKIN:
			plugin.dropItem(b, 1, plugin.item(Material.COOKIE, 3, "Mysterious Cookie", "Restore hunger.."));
			plugin.dropItem(b, 1, plugin.item(Material.PUMPKIN_SEEDS, 2, "Powerful Essence", "Grow for Power"));
			break;
		case MELON:
			plugin.dropItem(b, 1, plugin.item(Material.APPLE, 1, "Mysterious Apple", "Restore hunger.."));
			plugin.dropItem(b, 1, plugin.item(Material.POTATO, 2, "Powerful Essence", "Grow for Power"));
			break;
		case POTATO:
			Crops potato = (Crops) b.getState().getData();
			if (potato.getState().equals(CropState.RIPE)) {
				plugin.dropItem(b, 1, plugin.item(Material.COOKED_COD, 1, "Mysterious Fish", "Restore hunger.."));
				plugin.dropItem(b, 1, plugin.item(Material.CARROT, 2, "Powerful Essence", "Grow for Power"));
			}
			break;
		case CARROT:
			Crops carrot = (Crops) b.getState().getData();
			if (carrot.getState().equals(CropState.RIPE)) {
				plugin.dropItem(b, 1, plugin.item(Material.COOKED_RABBIT, 2, "Mysterious Rabbit", "Restore hunger.."));
				plugin.dropItem(b, 1, plugin.item(Material.NETHER_WART, 2, "Powerful Essence", "Grow for Power"));
			}
			break;
		case NETHER_WART:
			NetherWarts wart = (NetherWarts) b.getState().getData();
			if (wart.getState().equals(NetherWartsState.RIPE)) {
				plugin.dropItem(b, 1, plugin.item(Material.GOLDEN_APPLE, 1, "Mysterious Golden Apple", "Restore hunger.."));
				plugin.dropItem(b, 1, plugin.item(Material.NETHER_WART, 2, "Powerful Essence", "Grow for Power"));
			}
			break;
		default:
			break;
		}
	}
}
