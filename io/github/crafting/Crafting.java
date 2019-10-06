package io.github.crafting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import io.github.valk.Viesta;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("unused")
public class Crafting implements Listener{
	Viesta plugin;
	
	public Crafting(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void craftEvent(CraftItemEvent event) {
		/*for (int i = 0; i < plugin.craftingRecipeItems.size(); i++) {
			if (event.getRecipe().getResult().getItemMeta().getDisplayName().contains(plugin.craftingRecipeItems.get(i).getItemMeta().getDisplayName())) {
				event.setCancelled(true);
				event.getWhoClicked().sendMessage(ChatColor.RED + "Obtain greater souls.");
				break;
			}
		}*/
	}
}
