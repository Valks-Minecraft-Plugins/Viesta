package io.github.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.EntityEquipment;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class BlockDrops implements Listener {
	Viesta plugin;
	
	public BlockDrops(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private void furnace(FurnaceSmeltEvent event) {
		Block b = event.getBlock();
		double random = Math.random();
		if (random < 0.0001) {
			plugin.dropItem(b, 1, plugin.item(Material.BLACK_SHULKER_BOX, 1, "???", "???"));
		} else {
			plugin.dropItem(b, 1, plugin.item(Material.COAL, 1, "???", "???"));
		}

		event.getBlock().getWorld().spawnParticle(Particle.LAVA, event.getBlock().getLocation(), 1);
	}
	
	@EventHandler
	private boolean blockEvent(BlockBreakEvent event) {
		Block b = event.getBlock();
		Material bt = b.getType();
		Location loc = b.getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		cm.getConfig();
		EntityEquipment equipment = p.getEquipment();
		Material tool_type = equipment.getItemInMainHand().getType();
		equipment.getItemInMainHand();

		/*
		 * Get certain blocks back on mine.
		 */
		switch (bt) {
		case CHEST:
		case OAK_WOOD:
		case OAK_SLAB:
		case RED_BED:
		case HOPPER:
		case CRAFTING_TABLE:
		case FURNACE:
		case ENDER_CHEST:
			plugin.dropItem(b, 1, plugin.item(b.getType(), 1, b.getType().name(), "???"));
			break;
		default:
			break;
		}

		/*
		 * Msc
		 */
		switch (bt) {
		case DIRT:
		case GRASS:
			switch (tool_type) {
			case WOODEN_SHOVEL:
				plugin.dropItem(b, 1, plugin.item(Material.DIRT, 1, "Mud", "Perhaps combine it?"));
				break;
			case STONE_SHOVEL:
				plugin.dropItem(b, 1, plugin.item(Material.DIRT, 2, "Mud", "Perhaps combine it?"));
				break;
			case IRON_SHOVEL:
				plugin.dropItem(b, 1, plugin.item(Material.DIRT, 3, "Mud", "Perhaps combine it?"));
				break;
			case GOLDEN_SHOVEL:
				plugin.dropItem(b, 1, plugin.item(Material.DIRT, 4, "Mud", "Perhaps combine it?"));
				break;
			case DIAMOND_SHOVEL:
				plugin.dropItem(b, 1, plugin.item(Material.DIRT, 5, "Mud", "Perhaps combine it?"));
				break;
			default:
				break;
			}
			break;
		case ACACIA_LEAVES:
		case BIRCH_LEAVES:
		case DARK_OAK_LEAVES:
		case JUNGLE_LEAVES:
		case OAK_LEAVES:
		case SPRUCE_LEAVES:
			plugin.dropItem(b, 1, plugin.item(Material.STICK, 2, "Twig", "Perhaps combine it?"));
			break;
		case TALL_GRASS:
			switch (tool_type) {
			case WOODEN_HOE:
			case STONE_HOE:
			case IRON_HOE:
			case GOLDEN_HOE:
			case DIAMOND_HOE:
				plugin.dropItem(b, 1, plugin.item(Material.STICK, 4, "Twig", "Perhaps combine it?"));
				break;
			default:
				plugin.dropItem(b, 0.3, plugin.item(Material.STICK, 1, "Twig", "Perhaps combine it?"));
				break;
			}
			plugin.moreBlocks(loc, 4, 4, Material.GRASS, Material.TALL_GRASS);
			break;
		case DIAMOND_ORE:
			plugin.dropItem(b, 1, plugin.item(Material.DIAMOND, 8, "Impure Crystal", "Try to make tools / weapons?"));
			break;
		case GOLD_ORE:
			b.setType(Material.LAVA);
			if (p.getInventory().firstEmpty() != -1) {
				p.getInventory().addItem(plugin.item(Material.GOLD_INGOT, 12, "Shiny Stuff", "Try to make tools / weapons?"));
			} else {
				p.sendMessage(plugin.prefix("e" + "Failed to harvest ore, your inventory is full."));
			}
			break;
		case IRON_ORE:
			b.setType(Material.WATER);
			switch(tool_type) {
			default:
				plugin.dropItem(b, 1, plugin.item(Material.GLASS, 3, "Crystal", "Perhaps burn it?"));
				break;
			}
			break;
		case COAL_ORE:
			switch (tool_type) {
			default:
				plugin.dropItem(b, 1, plugin.item(Material.FLINT, 1, "Dark Rock", "Its a dark rock"));
				break;
			}
			break;
		case STONE:
			switch (tool_type) {
			case STONE_PICKAXE:
				plugin.dropItem(b, 1, plugin.item(Material.INK_SAC, 2, (byte) 8, "Pebble", "It's smooth!"));
				break;
			case IRON_PICKAXE:
				plugin.dropItem(b, 1, plugin.item(Material.INK_SAC, 3, (byte) 8, "Pebble", "It's smooth!"));
				break;
			case GOLDEN_PICKAXE:
				plugin.dropItem(b, 1, plugin.item(Material.INK_SAC, 4, (byte) 8, "Pebble", "It's smooth!"));
				break;
			case DIAMOND_PICKAXE:
				plugin.dropItem(b, 1, plugin.item(Material.INK_SAC, 5, (byte) 8, "Pebble", "It's smooth!"));
				break;
			default:
				plugin.dropItem(b, 1, plugin.item(Material.INK_SAC, 1, (byte) 8, "Pebble", "It's smooth!"));
				break;
			}
			break;
		case ACACIA_LOG:
		case BIRCH_LOG:
		case DARK_OAK_LOG:
		case JUNGLE_LOG:
		case OAK_LOG:
		case SPRUCE_LOG:
			switch (tool_type) {
			case WOODEN_AXE:
				plugin.dropItem(b, 1, plugin.item(Material.RABBIT_FOOT, 4, "Chunk of Wood", "Perhaps combine it?"));
				break;
			case STONE_AXE:
				plugin.dropItem(b, 1, plugin.item(Material.RABBIT_FOOT, 8, "Chunk of Wood", "Perhaps combine it?"));
				break;
			case IRON_AXE:
				plugin.dropItem(b, 1, plugin.item(Material.OAK_SLAB, 4, "Wood Slab", "Perhaps combine it?"));
				break;
			case GOLDEN_AXE:
				plugin.dropItem(b, 1, plugin.item(Material.OAK_SLAB, 8, "Wood Slab", "Perhaps combine it?"));
				break;
			case DIAMOND_AXE:
				plugin.dropItem(b, 1, plugin.item(Material.OAK_WOOD, 4, "Plank", "Try to make tools / weapons?"));
				break;
			default:
				plugin.dropItem(b, 1, plugin.item(Material.STICK, 6, "Twig", "Perhaps combine it?"));
				break;
			}
			break;
		default:
			break;
		}

		if (!bt.equals(Material.TALL_GRASS)) {
			p.giveExp(1);
			plugin.updateHealth(p);
		}
		event.getBlock().getDrops().clear();
		event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
		event.setExpToDrop(0);

		return false;
	}
	
	
}
