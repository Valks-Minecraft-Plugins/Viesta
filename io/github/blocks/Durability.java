package io.github.blocks;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class Durability implements Listener {
	Viesta plugin;
	
	public Durability(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean blockEvent(BlockBreakEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		EntityEquipment equipment = p.getEquipment();
		Material tool_type = equipment.getItemInMainHand().getType();
		ItemStack hand_item = equipment.getItemInMainHand();
		
		if (config.get("class").equals("Beserker") && p.getLevel() >= 30) {

		} else {
			switch (tool_type) {
			case WOODEN_AXE:
			case WOODEN_SHOVEL:
			case WOODEN_PICKAXE:
			case WOODEN_HOE:
			case WOODEN_SWORD:
				hand_item.setDurability((short) (hand_item.getDurability() + 6));
				if (hand_item.getDurability() >= tool_type.getMaxDurability()) {
					equipment.setItemInMainHand(plugin.item(Material.STICK, 2, "Twig", "Perhaps combine it?"));
					plugin.sendActionMessage(p, "Your tool snapped in 2..", "red");
				}
				break;
			case STONE_AXE:
			case STONE_SHOVEL:
			case STONE_PICKAXE:
			case STONE_HOE:
			case STONE_SWORD:
				hand_item.setDurability((short) (hand_item.getDurability() + 5));
				if (hand_item.getDurability() >= tool_type.getMaxDurability()) {
					equipment.setItemInMainHand(plugin.item(Material.STICK, 8, "Twig", "Perhaps combine it?"));
					plugin.sendActionMessage(p, "Your tool shattered into several sticks..", "red");
				}
				break;
			case IRON_AXE:
			case IRON_SHOVEL:
			case IRON_PICKAXE:
			case IRON_HOE:
			case IRON_SWORD:
				hand_item.setDurability((short) (hand_item.getDurability() + 4));
				if (hand_item.getDurability() >= tool_type.getMaxDurability()) {
					equipment.setItemInMainHand(plugin.item(Material.STICK, 16, "Twig", "Perhaps combine it?"));
					plugin.sendActionMessage(p, "Your tool shattered into several sticks..", "red");
				}
				break;
			case GOLDEN_AXE:
			case GOLDEN_SHOVEL:
			case GOLDEN_PICKAXE:
			case GOLDEN_HOE:
			case GOLDEN_SWORD:
				hand_item.setDurability((short) (hand_item.getDurability() + 3));
				if (hand_item.getDurability() >= tool_type.getMaxDurability()) {
					equipment.setItemInMainHand(plugin.item(Material.STICK, 32, "Twig", "Perhaps combine it?"));
					plugin.sendActionMessage(p, "Your tool shattered into several sticks..", "red");
				}
				break;
			case DIAMOND_AXE:
			case DIAMOND_SHOVEL:
			case DIAMOND_PICKAXE:
			case DIAMOND_HOE:
			case DIAMOND_SWORD:
				hand_item.setDurability((short) (hand_item.getDurability() + 5));
				if (hand_item.getDurability() >= tool_type.getMaxDurability()) {
					equipment.setItemInMainHand(plugin.item(Material.STICK, 64, "Twig", "Perhaps combine it?"));
					plugin.sendActionMessage(p, "Your tool shattered into several sticks..", "red");
				}
				break;
			default:
				break;
			}
		}

		return false;
	}
}
