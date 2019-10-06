package io.github.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.valk.Viesta;

public class JoinInventory implements Listener {
	Viesta plugin;
	
	public JoinInventory (Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerInventory inventory = p.getInventory();

		if (!p.hasPlayedBefore()) {
			inventory.setHelmet(invItem(Material.IRON_HELMET, "Lil' Helm",
					new String[] { "Keeps your head warm!", "Never get cold again!" }));
			inventory.setChestplate(invItem(Material.IRON_CHESTPLATE, "Plate",
					new String[] { "Protects you from stuff.", "Very useful.", "Will save your life.." }));
			inventory.setLeggings(invItem(Material.IRON_LEGGINGS, "Leggings",
					new String[] { "Protects you from alot of stuff.", ":D" }));
			inventory.setBoots(invItem(Material.IRON_BOOTS, "Lil' Shoes",
					new String[] { "Very useful for walking.", "I'm really bad at this xD" }));
			inventory.addItem(invItem(Material.IRON_SWORD, "Ms. Sharpy", new String[] { "Early-game helpful sword.",
					"Very hard to craft in-game.", "Also known as sword of the cat." }));
			inventory.addItem(invItem(Material.BREAD, "Bread", new String[] { "Yumm!" }));
		}
	}
	
	private ItemStack invItem(Material item, String name, String[] lore) {
		ItemStack stack = new ItemStack(item);
		stack.addUnsafeEnchantment(Enchantment.FROST_WALKER, 1);
		ItemMeta stack_meta = stack.getItemMeta();
		stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + name));
		List<String> item_lore = new ArrayList<String>();
		for (int i = 0; i < lore.length; i++) {
			item_lore.add(ChatColor.translateAlternateColorCodes('&', "&2" + lore[i]));
		}
		stack_meta.setLore(item_lore);
		stack.setItemMeta(stack_meta);
		return stack;
	}
}
