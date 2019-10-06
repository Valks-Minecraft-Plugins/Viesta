package io.github.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class SuperPickaxe implements Listener{
	Viesta plugin;
	
	public SuperPickaxe(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private void blockEvent(BlockBreakEvent event) {
		Block b = event.getBlock();
		Location loc = b.getLocation();
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		cm.getConfig();
		EntityEquipment equipment = p.getEquipment();
		Material tool_type = equipment.getItemInMainHand().getType();
		equipment.getItemInMainHand();

		if (p.isSneaking()) {
			switch (tool_type) {
			case DIAMOND_PICKAXE:
				superPickaxe(loc);
				break;
			default:
				break;
			}
		}
	}
	
	private void superPickaxe(Location loc) {
		loc.setY(loc.getY() + 1);
		loc.setX(loc.getX() - 1);
		loc.setZ(loc.getZ() + 1);
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					if (loc.getBlock().getType().equals(Material.STONE)) {
						loc.getBlock().setType(Material.AIR);
					}
					loc.setX(loc.getX() + 1);
				}
				loc.setX(loc.getX() - 3);
				loc.setY(loc.getY() - 1);
			}
			loc.setY(loc.getY() + 3);
			loc.setZ(loc.getZ() - 1);
		}
	}
}
