package io.github.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import io.github.valk.Viesta;

public class Gravity implements Listener {
	Viesta plugin;
	
	public Gravity (Viesta instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private boolean onBlockPlace(BlockPlaceEvent event) {
		Block b = event.getBlockPlaced();
		Material type = event.getBlockPlaced().getType();

		switch (type) {
		case COBBLESTONE:
		case LADDER:
		case OAK_WOOD:
		case OAK_SLAB:
		case PURPUR_SLAB:
		case CHEST:
		case CRAFTING_TABLE:
		case OAK_FENCE:
		case OAK_FENCE_GATE:
		case REDSTONE_TORCH:
		case OAK_STAIRS:
		case COBBLESTONE_STAIRS:
		case OAK_LOG:
		case BLACK_STAINED_GLASS:
			b.getWorld().spawnFallingBlock(b.getLocation().add(0.5d, 0, 0.5d), b.getType(), b.getData());
			b.setType(Material.AIR);
			break;
		default:
			break;
		}
		return true;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void blockEvent(BlockBreakEvent event) {
		Block b = event.getBlock();
		Material bt = b.getType();
		Location loc = b.getLocation();
		
		switch(bt) {
		case OAK_LOG:
			treeGravity(loc);
			break;
		case COAL_ORE:
		case DIAMOND_ORE:
		case EMERALD_ORE:
		case GOLD_ORE:
		case IRON_ORE:
		case LAPIS_ORE:
		case REDSTONE_ORE:
			oreSoftenStone(loc);
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void treeGravity(Location loc) {
		loc.add(0.5d, 0, 0.5d);
		for (int y = 0; y < 8; y++) {
			loc.setY(loc.getY() + 1);
			if (loc.getBlock().getType().equals(Material.OAK_LOG)) {
				loc.getBlock().setType(Material.AIR);
				loc.getWorld().spawnFallingBlock(loc, Material.OAK_LOG, loc.getBlock().getData());
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void oreSoftenStone(Location loc) {
		loc.setZ(loc.getBlockZ() - 1);
		loc.setY(loc.getBlockY() - 1);
		loc.setX(loc.getBlockX() - 1);
		for (int x = 0; x < 3; x++) {
			loc.add(0.5d, 0, 0.5d);
			for (int z = 0; z < 3; z++) {
				for (int y = 0; y < 3; y++) {
					loc.setY(loc.getBlockY() - 1);
					if (loc.getBlock().getType().equals(Material.AIR)) {
						loc.setY(loc.getBlockY() + 1);
						if (loc.getBlock().getType().equals(Material.STONE)) {
							loc.getBlock().setType(Material.AIR);
							loc.getWorld().spawnFallingBlock(loc, Material.COBBLESTONE, (byte) 0);
						}
					} else {
						loc.setY(loc.getBlockY() + 1);
					}
					loc.setY(loc.getBlockY() + 1);
				}
				loc.setY(loc.getBlockY() - 3);
				loc.setZ(loc.getBlockZ() + 1);
			}
			loc.setZ(loc.getBlockZ() - 3);
			loc.setX(loc.getBlockX() + 1);
		}
	}
}
