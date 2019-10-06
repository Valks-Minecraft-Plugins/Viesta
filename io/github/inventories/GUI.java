package io.github.inventories;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class GUI implements Listener {
	Viesta plugin;

	public GUI(Viesta instance) {
		plugin = instance;
	}

	@EventHandler
	public boolean onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		Server serv = plugin.getServer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		String invName = inventory.getName();
		int slot = event.getSlot();
		// all the furnace recipes
		if (invName.equals(plugin.furnaceRecipeHome.get(0).getName())) {
			event.setCancelled(true);
			if (!event.getCurrentItem().getType().equals(Material.AIR)) {
				switch (slot) {
				case 45:
					if (plugin.invFurnaceHomeRecipe.get(p.getName()) >= 1) {
						p.openInventory(plugin.furnaceRecipeHome.get(plugin.invFurnaceHomeRecipe.get(p.getName()) - 1));
						plugin.invFurnaceHomeRecipe.put(p.getName(), plugin.invFurnaceHomeRecipe.get(p.getName()) - 1);
					}
					break;
				case 49:
					p.openInventory(plugin.recipeGenres());
					break;
				case 53:
					if (plugin.invFurnaceHomeRecipe.get(p.getName()) < plugin.furnaceRecipeHome.size() - 1) {
						p.openInventory(plugin.furnaceRecipeHome.get(plugin.invFurnaceHomeRecipe.get(p.getName()) + 1));
						plugin.invFurnaceHomeRecipe.put(p.getName(), plugin.invFurnaceHomeRecipe.get(p.getName()) + 1);
					}
					break;
				default:
					int offset = plugin.invFurnaceHomeRecipe.get(p.getName());
					p.openInventory(plugin.translatedFurnaceRecipes.get(slot + (offset * 45)));
					plugin.invFurnaceRecipe.put(p.getName(), slot + (offset * 45));
				}

			}
		}

		// all the hand recipes
		if (invName.equals(plugin.handRecipeHome.get(0).getName())) {
			event.setCancelled(true);
			if (!event.getCurrentItem().getType().equals(Material.AIR)) {
				switch (slot) {
				case 45:
					if (plugin.invHandHomeRecipe.get(p.getName()) >= 1) {
						p.openInventory(plugin.handRecipeHome.get(plugin.invHandHomeRecipe.get(p.getName()) - 1));
						plugin.invHandHomeRecipe.put(p.getName(), plugin.invHandHomeRecipe.get(p.getName()) - 1);

					}
					break;
				case 49:
					p.openInventory(plugin.recipeGenres());
					break;
				case 53:
					if (plugin.invHandHomeRecipe.get(p.getName()) < plugin.handRecipeHome.size() - 1) {
						p.openInventory(plugin.handRecipeHome.get(plugin.invHandHomeRecipe.get(p.getName()) + 1));
						plugin.invHandHomeRecipe.put(p.getName(), plugin.invHandHomeRecipe.get(p.getName()) + 1);
					}
					break;
				default:
					int offset = plugin.invHandHomeRecipe.get(p.getName());
					p.openInventory(plugin.translatedHandRecipes.get(slot + (offset * 45)));
					plugin.invHandRecipe.put(p.getName(), slot + (offset * 45));
				}

			}
		}

		// all the crafting recipes
		if (invName.equals(plugin.craftingRecipeHome.get(0).getName())) {
			event.setCancelled(true);
			if (!event.getCurrentItem().getType().equals(Material.AIR)) {
				switch (slot) {
				case 45:
					if (plugin.invCraftingHomeRecipe.get(p.getName()) >= 1) {
						p.openInventory(
								plugin.craftingRecipeHome.get(plugin.invCraftingHomeRecipe.get(p.getName()) - 1));
						plugin.invCraftingHomeRecipe.put(p.getName(),
								plugin.invCraftingHomeRecipe.get(p.getName()) - 1);

					}
					break;
				case 49:
					p.openInventory(plugin.recipeGenres());
					break;
				case 53:
					if (plugin.invCraftingHomeRecipe.get(p.getName()) < plugin.craftingRecipeHome.size() - 1) {
						p.openInventory(
								plugin.craftingRecipeHome.get(plugin.invCraftingHomeRecipe.get(p.getName()) + 1));
						plugin.invCraftingHomeRecipe.put(p.getName(),
								plugin.invCraftingHomeRecipe.get(p.getName()) + 1);
					}
					break;
				default:
					int offset = plugin.invCraftingHomeRecipe.get(p.getName());
					p.openInventory(plugin.translatedCraftingRecipes.get(slot + (offset * 45)));
					plugin.invCraftingRecipe.put(p.getName(), slot + (offset * 45));
				}

			}
		}

		// specific furnace recipe
		if (invName.equals(plugin.translatedFurnaceRecipes.get(0).getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 18 + 9:
				if (plugin.invFurnaceRecipe.get(p.getName()) >= 1) {
					p.openInventory(plugin.translatedFurnaceRecipes.get(plugin.invFurnaceRecipe.get(p.getName()) - 1));
					plugin.invFurnaceRecipe.put(p.getName(), plugin.invFurnaceRecipe.get(p.getName()) - 1);

				}
				break;
			case 26 + 9:
				if (plugin.invFurnaceRecipe.get(p.getName()) < plugin.translatedFurnaceRecipes.size() - 1) {
					p.openInventory(plugin.translatedFurnaceRecipes.get(plugin.invFurnaceRecipe.get(p.getName()) + 1));
					plugin.invFurnaceRecipe.put(p.getName(), plugin.invFurnaceRecipe.get(p.getName()) + 1);
				}
				break;
			case 18 + 9 + 4:
				plugin.invFurnaceHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.furnaceRecipeHome.get(0));
				break;
			default:
				break;
			}
		}

		// specific hand recipe
		if (invName.equals(plugin.translatedHandRecipes.get(0).getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 18 + 9:
				if (plugin.invHandRecipe.get(p.getName()) >= 1) {
					p.openInventory(plugin.translatedHandRecipes.get(plugin.invHandRecipe.get(p.getName()) - 1));
					plugin.invHandRecipe.put(p.getName(), plugin.invHandRecipe.get(p.getName()) - 1);

				}
				break;
			case 26 + 9:
				if (plugin.invHandRecipe.get(p.getName()) < plugin.translatedHandRecipes.size() - 1) {
					p.openInventory(plugin.translatedHandRecipes.get(plugin.invHandRecipe.get(p.getName()) + 1));
					plugin.invHandRecipe.put(p.getName(), plugin.invHandRecipe.get(p.getName()) + 1);
				}
				break;
			case 18 + 9 + 4:
				plugin.invHandHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.handRecipeHome.get(0));
				break;
			default:
				break;
			}
		}

		// specific crafting recipe
		if (invName.equals(plugin.translatedCraftingRecipes.get(0).getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 18 + 9:
				if (plugin.invCraftingRecipe.get(p.getName()) >= 1) {
					p.openInventory(
							plugin.translatedCraftingRecipes.get(plugin.invCraftingRecipe.get(p.getName()) - 1));
					plugin.invCraftingRecipe.put(p.getName(), plugin.invCraftingRecipe.get(p.getName()) - 1);

				}
				break;
			case 26 + 9:
				if (plugin.invCraftingRecipe.get(p.getName()) < plugin.translatedCraftingRecipes.size() - 1) {
					p.openInventory(
							plugin.translatedCraftingRecipes.get(plugin.invCraftingRecipe.get(p.getName()) + 1));
					plugin.invCraftingRecipe.put(p.getName(), plugin.invCraftingRecipe.get(p.getName()) + 1);
				}
				break;
			case 18 + 9 + 4:
				plugin.invCraftingHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.craftingRecipeHome.get(0));
				break;
			default:
				break;
			}
		}

		// info
		if (invName.equals(plugin.info().getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 0:
				p.closeInventory();
				p.sendMessage(plugin.prefix("5") + "https://discord.gg/XxHHDAv");
				break;
			case 1:
				p.closeInventory();
				p.sendMessage(plugin.prefix("5")
						+ "http://www.minecraftforum.net/forums/servers/pc-servers/2841405-viesta-custom-coded");
				break;
			case 2:
				p.closeInventory();
				p.sendMessage(plugin.prefix("e") + "Usage: /tutorial <page>");
				break;
			case 3:
				p.closeInventory();
				p.sendMessage(plugin.prefix("8") + "---------------------------");
				p.sendMessage(plugin.prefix("5") + "Q: I'm stuck? A: Read the lores on the items for hints.");
				p.sendMessage(plugin.prefix("8") + "---------------------------");
				break;
			case 4:
				p.closeInventory();
				p.sendMessage(plugin.prefix("8") + "---------------------------");
				p.sendMessage(plugin.prefix("5") + "No griefing.");
				p.sendMessage(plugin.prefix("d") + "No bypassing the swear filters.");
				p.sendMessage(plugin.prefix("5") + "Respect everyone including yourself.");
				p.sendMessage(plugin.prefix("8") + "---------------------------");
				break;
			case 26:
				p.openInventory(plugin.help());
				break;
			default:
				break;
			}
		}

		// recipe genres
		if (invName.equals(plugin.recipeGenres().getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 0:
				plugin.invHandHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.handRecipeHome.get(0));
				break;
			case 1:
				plugin.invCraftingHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.craftingRecipeHome.get(0));
				break;
			case 2:
				plugin.invFurnaceHomeRecipe.put(p.getName(), 0);
				p.openInventory(plugin.furnaceRecipeHome.get(0));
				break;
			case 26:
				p.openInventory(plugin.help());
				break;
			default:
				break;
			}
		}

		// help
		if (invName.equals(plugin.help().getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 0:
				p.openInventory(plugin.info());
				break;
			case 1:
				p.openInventory(plugin.recipeGenres());
				break;
			case 6:
				p.openInventory(plugin.gods());
				break;
			case 7:
				p.openInventory(plugin.races());
				break;
			case 8:
				p.openInventory(plugin.traits());
				break;
			default:
				break;
			}
		}

		// gods
		if (invName.equals(plugin.gods().getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 0:
				p.closeInventory();
				clickedGod(cm, serv, p, "Kai", "You feel drained..");
				break;
			case 1:
				p.closeInventory();
				clickedGod(cm, serv, p, "Kuu", "You feel drained..");
				break;
			case 2:
				p.closeInventory();
				clickedGod(cm, serv, p, "Vasa", "You feel drained..");
				break;
			case 3:
				p.closeInventory();
				clickedGod(cm, serv, p, "Sakura", "You feel drained..");
				break;
			case 4:
				p.closeInventory();
				clickedGod(cm, serv, p, "Valkyrie", "You feel drained..");
				break;
			case 26:
				p.closeInventory();
				p.openInventory(plugin.help());
				break;
			default:
				break;
			}
		}

		// classes
		if (invName.equals(plugin.races().getName())) {
			event.setCancelled(true);
			if (slot != 26) {
				if (p.getLevel() < 5) {
					p.sendMessage(plugin.prefix("5") + "You need at least 5 levels of exp to do this.");
					return true;
				}
			}
			switch (slot) {
			case 0:
				p.closeInventory();
				clickedClass(cm, serv, p, "Shadow", "The night awaits..");
				break;
			case 1:
				p.closeInventory();
				clickedClass(cm, serv, p, "Blazer", "The light awaits..");
				break;
			case 2:
				p.closeInventory();
				clickedClass(cm, serv, p, "Lurker", "The dark awaits..");
				break;
			case 3:
				p.closeInventory();
				clickedClass(cm, serv, p, "Xysta", "The desert awaits..");
				break;
			case 4:
				p.closeInventory();
				clickedClass(cm, serv, p, "Wizard", "The magic stirs..");
				break;
			case 5:
				p.closeInventory();
				clickedClass(cm, serv, p, "Summoner", "You're not alone..");
				break;
			case 6:
				p.closeInventory();
				clickedClass(cm, serv, p, "Beserker", "The sword awaits..");
				break;
			case 7:
				p.closeInventory();
				clickedClass(cm, serv, p, "Vishna", "The jungle awaits..");
				break;
			case 26:
				p.closeInventory();
				p.openInventory(plugin.help());
				break;
			default:
				break;
			}
		}

		// traits
		if (invName.equals(plugin.traits().getName())) {
			event.setCancelled(true);
			switch (slot) {
			case 0:
				p.closeInventory();
				break;
			case 26:
				p.closeInventory();
				p.openInventory(plugin.help());
				break;
			default:
				break;
			}
		}

		return true;
	}

	private void clickedGod(PlayerFiles cm, Server serv, Player p, String name, String desc) {
		FileConfiguration config = cm.getConfig();
		if (config.getString("god") != name) {
			p.setLevel(p.getLevel() - 5);
			config.set("god", name);
			cm.saveConfig();
			plugin.sendTitle(p, name, desc, "green");
		} else {
			p.sendMessage("You already are one with " + name + ".");
		}
	}

	private void clickedClass(PlayerFiles cm, Server serv, Player p, String name, String desc) {
		FileConfiguration config = cm.getConfig();
		if (config.getString("class") != name) {
			p.setLevel(p.getLevel() - 5);
			config.set("class", name);
			cm.saveConfig();
			plugin.sendTitle(p, name, desc, "gold");
		} else {
			p.sendMessage("You already are a " + name + ".");
		}
	}
}
