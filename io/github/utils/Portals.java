package io.github.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import io.github.valk.Viesta;

public class Portals implements Listener{
	Viesta plugin;
	
	public Portals(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private void portalEnter(PlayerPortalEvent event) {
		Player p = event.getPlayer();

		if (p.getWorld().getName().equals("world")) {
			World world = Bukkit.getWorld("spawn");
			p.sendMessage(plugin.prefix("5") + "Teleporting you to the spawn..");
			event.setTo(world.getSpawnLocation());
		} else {
			Location bed = p.getBedSpawnLocation();
			if (bed != null) {
				p.sendMessage(plugin.prefix("e") + "Teleporting you to your bed..");
				event.setTo(bed);
			} else {
				p.sendMessage(plugin.prefix("3") + "Teleporting you to default survival area..");
				World wild_world = plugin.getServer().getWorld("world");
				Location wild = new Location(wild_world, 100, 88, 100);
				event.setTo(wild);
				p.sendMessage(plugin.prefix("b") + "Remember to make a bed!");
			}
		}
	}
}
