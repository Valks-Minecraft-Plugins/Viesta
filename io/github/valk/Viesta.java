package io.github.valk;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.blocks.BlockDrops;
import io.github.blocks.Gravity;
import io.github.chat.*;
import io.github.classes.*;
import io.github.configs.LoadPlayerFiles;
import io.github.configs.PlayerFiles;
import io.github.crafting.Crafting;
import io.github.effects.DamageEffects;
import io.github.gods.*;
import io.github.inventories.*;
import io.github.mobs.SpawnMobs;
import io.github.particles.Particles;
import io.github.pets.*;
import io.github.sounds.Sounds;
import io.github.traits.*;
import io.github.utils.*;
import net.md_5.bungee.api.ChatColor;

public final class Viesta extends JavaPlugin {
	Map<String, Long> cooldown = new HashMap<String, Long>();
	
	Map<String, Long> tpaCooldown = new HashMap<String, Long>();
	Map<String, String> currentRequest = new HashMap<String, String>();

	public Map<String, Integer> invHandRecipe = new HashMap<String, Integer>();
	public Map<String, Integer> invCraftingRecipe = new HashMap<String, Integer>();
	public Map<String, Integer> invFurnaceRecipe = new HashMap<String, Integer>();

	public List<Inventory> translatedHandRecipes = new ArrayList<Inventory>();
	public List<Inventory> translatedCraftingRecipes = new ArrayList<Inventory>();
	public List<Inventory> translatedFurnaceRecipes = new ArrayList<Inventory>();

	public List<ItemStack> handRecipeItems = new ArrayList<ItemStack>();
	public List<ItemStack> craftingRecipeItems = new ArrayList<ItemStack>();
	public List<ItemStack> furnaceRecipeItems = new ArrayList<ItemStack>();

	public Map<String, Integer> invHandHomeRecipe = new HashMap<String, Integer>();
	public Map<String, Integer> invCraftingHomeRecipe = new HashMap<String, Integer>();
	public Map<String, Integer> invFurnaceHomeRecipe = new HashMap<String, Integer>();

	public List<Inventory> handRecipeHome = new ArrayList<Inventory>();
	public List<Inventory> craftingRecipeHome = new ArrayList<Inventory>();
	public List<Inventory> furnaceRecipeHome = new ArrayList<Inventory>();

	@Override
	public void onEnable() {
		getServer().clearRecipes();
		addRecipes();

		handRecipeHomeInv();
		craftingRecipeHomeInv();
		furnaceRecipeHomeInv();
		
		/*BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	Location loc = new Location(getServer().getWorld("spawn"), -35, 66, 0);
                getServer().getWorld("spawn").spawnParticle(Particle.DRAGON_BREATH, loc, 10);
            }
        }, 0L, 1L);*/
		
		mainConfig(this.getConfig());
		registerEvents(getServer().getPluginManager());
		enableInfo(this.getDescription());
	}
	
	public void mainConfig(final FileConfiguration config) {
		config.addDefault("USER-CHAT-FORMAT", "&8%player%&8: &7%message%");
		config.options().copyDefaults(true);
		saveDefaultConfig();
	}
	
	public void enableInfo(PluginDescriptionFile p) {
		getLogger().info(p.getName() + " v" + p.getVersion() + " has been enabled.");
	}
	
	public Item dropItem(Block block, double chance, ItemStack item) {
		double random = Math.random();
		if (random < chance) {
			return block.getWorld().dropItemNaturally(block.getLocation(), item);
		}
		return null;
	}
	
	public void registerEvents(PluginManager pm) {
		pm.registerEvents(new Crafting(this), this);
		pm.registerEvents(new Sounds(this), this);
		pm.registerEvents(new Particles(this), this);
		pm.registerEvents(new DamageEffects(this), this);
		pm.registerEvents(new BlockDrops(this), this);
		pm.registerEvents(new Gravity(this), this);
		pm.registerEvents(new Chat(this), this);
		pm.registerEvents(new Pets(this), this);
		pm.registerEvents(new SpawnMobs(this), this);
		pm.registerEvents(new GUI(this), this);
		pm.registerEvents(new LoadPlayerFiles(this), this);
		pm.registerEvents(new Broadcasts(this), this);
		pm.registerEvents(new JoinInventory(this), this);
		
		//Utils
		pm.registerEvents(new Portals(this), this);
		pm.registerEvents(new SignColor(this), this);
		pm.registerEvents(new Msc(this), this);
		
		//Gods
		pm.registerEvents(new Kai(this), this);
		pm.registerEvents(new Kuu(this), this);
		pm.registerEvents(new Valkyrie(this), this);
		pm.registerEvents(new Sakura(this), this);
		pm.registerEvents(new Vasa(this), this);
		
		//Traits
		pm.registerEvents(new EvilOne(this), this);
		pm.registerEvents(new LonelyWonderer(this), this);
		pm.registerEvents(new WildChild(this), this);
		
		//Classes
		pm.registerEvents(new Classes(this), this);
		pm.registerEvents(new Shadow(this), this);
		pm.registerEvents(new Angel(this), this);
		pm.registerEvents(new Archer(this), this);
		pm.registerEvents(new Beserker(this), this);
		pm.registerEvents(new Blazer(this), this);
		pm.registerEvents(new Dweller(this), this);
		pm.registerEvents(new Enchantress(this), this);
		pm.registerEvents(new Farmer(this), this);
		pm.registerEvents(new Gatherer(this), this);
		pm.registerEvents(new Healer(this), this);
		pm.registerEvents(new Lurker(this), this);
		pm.registerEvents(new Miner(this), this);
		pm.registerEvents(new Summoner(this), this);
		pm.registerEvents(new Vishna(this), this);
		pm.registerEvents(new Wizard(this), this);
		pm.registerEvents(new Xysta(this), this);
	}
	
	public boolean cooldown(Player p, int cooldown_time) {
		if (cooldown.containsKey(p.getName())) {
			long diff = (System.currentTimeMillis() - cooldown.get(p.getName())) / 1000;
			if (diff < cooldown_time) {
				p.sendMessage(prefix("e") + "Cooldown! Wait " + (cooldown_time - diff) + " seconds!");
			} else {
				cooldown.put(p.getName(), System.currentTimeMillis());
				return true;
			}
		} else {
			cooldown.put(p.getName(), System.currentTimeMillis());
			return true;
		}
		return false;
	}
	
	public void sunEffects(Player p) {
		if (!p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
			if (!p.hasPotionEffect(PotionEffectType.HUNGER)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 800, 4));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 800, 4));
				p.getWorld().spawnParticle(Particle.LAVA, p.getLocation(), 50);
				p.getWorld().strikeLightningEffect(p.getLocation());
				sendActionMessage(p, "You are suffering from sunlight exposure..", "red");
			}
		}
	}

	public void addRecipes() {
		handRecipe(item(Material.RABBIT_FOOT, 1, "Chunk of Wood", "Perhaps combine it?"), "ssss",
				new ItemStack[] { item(Material.STICK) }, new char[] { 's' });
		handRecipe(item(Material.OAK_SLAB, 1, "Wood Slab", "Perhaps combine it?"), "ssss",
				new ItemStack[] { item(Material.RABBIT_FOOT) }, new char[] { 's' });
		handRecipe(item(Material.OAK_WOOD, 1, "Plank", "Try to make tools / weapons?"), "soso",
				new ItemStack[] { item(Material.OAK_SLAB) }, new char[] { 's' });
		handRecipe(item(Material.FISHING_ROD, 1, "Rod", "Strong Rod"), "ossw",
				new ItemStack[] { item(Material.STRING), item(Material.STICK) }, new char[] { 'w', 's' });
		handRecipe(item(Material.ARROW, 1, "Arrow", "Fling!"), "sooo", new ItemStack[] { item(Material.STICK) },
				new char[] { 's' });
		handRecipe(item(Material.BOW, 1, "Bow", "Whoosh.."), "swsw",
				new ItemStack[] { item(Material.OAK_WOOD), item(Material.STICK) }, new char[] { 'w', 's' });
		addTools();
		handRecipe(item(Material.OAK_FENCE, 1, "Fence", "Keep em' out.."), "ssww",
				new ItemStack[] { item(Material.OAK_WOOD), item(Material.STICK) }, new char[] { 'w', 's' });
		handRecipe(item(Material.OAK_FENCE_GATE, 1, "Gate", "Keep em' out.."), "wwss",
				new ItemStack[] { item(Material.OAK_WOOD), item(Material.STICK) }, new char[] { 'w', 's' });
		handRecipe(item(Material.CHEST, 1, "Storage", "Store your items safely."), "ssss",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		handRecipe(item(Material.LADDER, 1, "Ladder", "Ms. Saftey"), "ssoo", new ItemStack[] { item(Material.STICK) },
				new char[] { 's' });
		handRecipe(item(Material.OAK_DOOR, 1, "Door", "Saftey comes first.."), "soso",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		handRecipe(item(Material.OAK_TRAPDOOR, 1, "Door", "Saftey comes first.."), "ssoo",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		handRecipe(item(Material.OAK_STAIRS, 1, "Jagged Plank", "Has many uses.."), "soss",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		handRecipe(item(Material.OAK_BUTTON, 1, "Button", "Handy switch.."), "sooo",
				new ItemStack[] { item(Material.OAK_SLAB) }, new char[] { 's' });
		handRecipe(item(Material.OAK_PRESSURE_PLATE, 1, "Plate", "Handy switch.."), "sooo",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		handRecipe(item(Material.OAK_LOG, 1, "Log", "Sturdy Plank.."), "wddw",
				new ItemStack[] { item(Material.OAK_WOOD), item(Material.DIRT) }, new char[] { 'w', 'd' });
		handRecipe(item(Material.OAK_BOAT, 1, "Boat", "Set sail.."), "ssoo", new ItemStack[] { item(Material.OAK_LOG) },
				new char[] { 's' });
		handRecipe(item(Material.WHITE_WOOL, 1, "Soft", "Silky.."), "ssss", new ItemStack[] { item(Material.STRING) },
				new char[] { 's' });
		handRecipe(item(Material.WHITE_WOOL, 1, (byte) 1, "Soft", "Silky.."), "ssss", new ItemStack[] { item(Material.WHITE_WOOL) },
				new char[] { 's' });
		for (int i = 0; i < 14; i++) {
			handRecipe(item(Material.WHITE_WOOL, 1, (byte) (i + 2), "Soft", "Silky.."), "sooo",
					new ItemStack[] { item(Material.WHITE_WOOL, 1, (byte) (i + 1), "Soft", "Silky..") }, new char[] { 's' });
		}
		handRecipe(item(Material.RED_BED, 1, "Bed", "Soft.. silky.."), "ssll",
				new ItemStack[] { item(Material.OAK_LOG), item(Material.WHITE_WOOL) }, new char[] { 'l', 's' });
		handRecipe(item(Material.SUGAR, 1, "White Essence", "Powerful Essence"), "ssss",
				new ItemStack[] { item(Material.WHITE_WOOL) }, new char[] { 's' });
		handRecipe(item(Material.WHEAT_SEEDS, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.SUGAR) }, new char[] { 's' });
		handRecipe(item(Material.PUMPKIN_SEEDS, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.WHEAT_SEEDS) }, new char[] { 's' });
		handRecipe(item(Material.MELON_SEEDS, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.PUMPKIN_SEEDS) }, new char[] { 's' });
		handRecipe(item(Material.POTATO, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.MELON_SEEDS) }, new char[] { 's' });
		handRecipe(item(Material.CARROT, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.POTATO) }, new char[] { 's' });
		handRecipe(item(Material.NETHER_WART, 1, "Powerful Essence", "Grow for Power"), "ssss",
				new ItemStack[] { item(Material.CARROT) }, new char[] { 's' });
		handRecipe(item(Material.DIRT, 1, (byte) 1, "Super Dirt", "???"), "ssss",
				new ItemStack[] { item(Material.DIRT) }, new char[] { 's' });
		handRecipe(item(Material.DIRT, 1, (byte) 2, "Super Dirt", "???"), "ssss",
				new ItemStack[] { item(Material.DIRT, 1, (byte) 1, "Super Dirt", "???") }, new char[] { 's' });
		handRecipe(item(Material.SOUL_SAND, 1, "Super Dirt", "???"), "ssss",
				new ItemStack[] { item(Material.DIRT, 1, (byte) 2, "Super Dirt", "???") }, new char[] { 's' });
		handRecipe(item(Material.CRAFTING_TABLE, 1, "Workbench", "The ultimate workbench.."), "ssss",
				new ItemStack[] { item(Material.OAK_LOG) }, new char[] { 's' });
		handRecipe(item(Material.PURPUR_SLAB, 1, "Stone Slab", "Perhaps combine it?"), "ssss",
				new ItemStack[] { item(Material.INK_SAC, 1, (byte) 8, "Pebble", "It's smooth!") }, new char[] { 's' });
		handRecipe(item(Material.COBBLESTONE, 1, "Cobble", "Try to make tools / weapons?"), "soso",
				new ItemStack[] { item(Material.PURPUR_SLAB) }, new char[] { 's' });
		handRecipe(item(Material.STONE_BUTTON, 1, "Button", "Handy switch.."), "sooo",
				new ItemStack[] { item(Material.PURPUR_SLAB) }, new char[] { 's' });
		handRecipe(item(Material.STONE_PRESSURE_PLATE, 1, "Plate", "Handy switch.."), "sooo",
				new ItemStack[] { item(Material.COBBLESTONE) }, new char[] { 's' });
		handRecipe(item(Material.LEVER, 1, "Lever", "Saftey comes first.."), "sowo",
				new ItemStack[] { item(Material.COBBLESTONE), item(Material.OAK_WOOD) }, new char[] { 's', 'w' });
		handRecipe(item(Material.COBBLESTONE_STAIRS, 1, "Jagged Cobble", "Has many uses.."), "soss",
				new ItemStack[] { item(Material.COBBLESTONE) }, new char[] { 's' });
		handRecipe(item(Material.REDSTONE_TORCH, 1, "Torch", "Provides light.."), "sowo",
				new ItemStack[] { item(Material.STICK), item(Material.FLINT) }, new char[] { 'w', 's' });
		craftingRecipe(item(Material.HOPPER, 1, "Funnel", "Very useful.."), "ssssossss",
				new ItemStack[] { item(Material.COBBLESTONE) }, new char[] { 's' });
		craftingRecipe(item(Material.FURNACE, 1, "Stove", "Very useful.."), "sssssssss",
				new ItemStack[] { item(Material.COBBLESTONE) }, new char[] { 's' });
		furnaceRecipe(item(Material.BLACK_STAINED_GLASS, 1, (byte) 1, "Darkened Crystal I", "Perhaps burn it?"),
				item(Material.GLASS));
		for (int i = 0; i < 14; i++) {
			furnaceRecipe(
					item(Material.BLACK_STAINED_GLASS, 1, (byte) (i + 2), "Darkened Crystal " + (i + 2), "Perhaps burn it?"),
					item(Material.BLACK_STAINED_GLASS, 1, (byte) (i + 1), "Darkened Crystal " + (i + 1), "Perhaps burn it?"));
		}
		handRecipe(item(Material.IRON_INGOT, 1, "Solid Ingot", "Iron Age.."), "ssss",
				new ItemStack[] {
						item(Material.BLACK_STAINED_GLASS, 1, (byte) 15, "Darkened Crystal 15", "Perhaps burn it?") },
				new char[] { 's' });
		handRecipe(item(Material.IRON_DOOR, 1, "Door", "Saftey comes first.."), "soso",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.IRON_TRAPDOOR, 1, "Door", "Saftey comes first.."), "soos",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.BRICK, 4, "Brick", "Building Blocks"), "sooo",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.STONE_BRICKS, 8, "Brick", "Building Blocks"), "ssoo",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.STONE, 8, "Brick", "Building Blocks"), "soso",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.RAIL, 8, "Rails", "Transportation!"), "wssw",
				new ItemStack[] { item(Material.IRON_INGOT), item(Material.STICK) }, new char[] { 'w', 's' });
		handRecipe(item(Material.POWERED_RAIL, 8, "Powerful Rail", "Boooossstttt!"), "wsoo",
				new ItemStack[] { item(Material.RAIL), item(Material.REDSTONE_TORCH) }, new char[] { 'w', 's' });
		handRecipe(item(Material.MINECART, 1, "Cart", "Like mario no? :("), "ssss",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.IRON_BARS, 1, "Bars", "Very strong."), "ssoo",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.IRON_BLOCK, 1, "Block of Iron", "Building Blocks"), "soso",
				new ItemStack[] { item(Material.IRON_INGOT) }, new char[] { 's' });
		handRecipe(item(Material.OAK_SLAB, 1, "Wood Slab", "Perhaps combine it?"), "sooo",
				new ItemStack[] { item(Material.BOWL) }, new char[] { 's' });
		handRecipe(item(Material.ANVIL, 1, "Anvil", "Repair Stuff"), "iibb",
				new ItemStack[] { item(Material.IRON_INGOT), item(Material.IRON_BLOCK) }, new char[] { 'i', 'b' });
		handRecipe(item(Material.BLACK_SHULKER_BOX, 1, "Advanced Storage", "Store stuff."), "wbbw",
				new ItemStack[] { item(Material.OAK_WOOD), item(Material.IRON_BLOCK) }, new char[] { 'w', 'b' });
		handRecipe(item(Material.TORCH, 1, "Torch", "Emits Bright Light"), "boao",
				new ItemStack[] { item(Material.IRON_INGOT), item(Material.FLINT) }, new char[] { 'a', 'b' });
		addArmor();
		craftingRecipe(item(Material.STICK, 1, "Wizard Wand", "Shoot snowballs."), "sssssssss",
				new ItemStack[] { item(Material.STICK) }, new char[] { 's' });
		craftingRecipe(item(Material.ARROW, 1, "Wizard Wand", "Shoot arrows."), "sssssssss",
				new ItemStack[] { item(Material.OAK_WOOD) }, new char[] { 's' });
		craftingRecipe(item(Material.BONE, 1, "Wizard Wand", "Shoot fireballs."), "sssssssss",
				new ItemStack[] { item(Material.INK_SAC, 1, (byte) 8, "Pebble", "It's smooth!") }, new char[] { 's' });
		craftingRecipe(item(Material.BLAZE_ROD, 1, "Wizard Wand", "Shoot lightning."), "sossossos",
				new ItemStack[] { item(Material.COBBLESTONE) }, new char[] { 's' });
		craftingRecipe(item(Material.BONE, 1, "Summoner Wand", "Summons wolfs."), "sosososos",
				new ItemStack[] { item(Material.RABBIT_FOOT, 1, "Chunk of Wood", "Perhaps combine it?") },
				new char[] { 's' });
		craftingRecipe(item(Material.BLAZE_ROD, 1, "Summoner Wand", "Summons horses."), "sosososos",
				new ItemStack[] { item(Material.REDSTONE_TORCH) }, new char[] { 's' });
		craftingRecipe(item(Material.ARROW, 1, "Summoner Wand", "Summons golems."), "sosososos",
				new ItemStack[] { item(Material.ARROW) }, new char[] { 's' });
	}

	public void addTools() {
		String[] types = new String[] { "WOOD", "STONE", "IRON", "GOLD", "DIAMOND" };
		String[] materials = new String[] { "WOOD", "COBBLESTONE", "IRON_INGOT", "GOLD_INGOT", "DIAMOND" };
		for (int i = 0; i < 5; i++) {
			handRecipe(item(Material.valueOf(types[i] + "_AXE"), 1, "Hachet", "Chop trees to get more wood."), "wsww",
					new ItemStack[] { item(Material.valueOf(materials[i])), item(Material.STICK) },
					new char[] { 'w', 's' });
			handRecipe(item(Material.valueOf(types[i] + "_PICKAXE"), 1, "Pick", "Mine stone for pebbles."), "wwsw",
					new ItemStack[] { item(Material.valueOf(materials[i])), item(Material.STICK) },
					new char[] { 'w', 's' });
			handRecipe(item(Material.valueOf(types[i] + "_HOE"), 1, "Hoe", "Used to plant seeds."), "owso",
					new ItemStack[] { item(Material.valueOf(materials[i])), item(Material.STICK) },
					new char[] { 'w', 's' });
			handRecipe(item(Material.valueOf(types[i] + "_SPADE"), 1, "Shovel", "Mine dirt to get dirt."), "woso",
					new ItemStack[] { item(Material.valueOf(materials[i])), item(Material.STICK) },
					new char[] { 'w', 's' });
			handRecipe(item(Material.valueOf(types[i] + "_SWORD"), 1, "Sword", "Get loot from mobs."), "owsw",
					new ItemStack[] { item(Material.valueOf(materials[i])), item(Material.STICK) },
					new char[] { 'w', 's' });
		}
	}
	
	public void addArmor() {
		String[] types = new String[] { "LEATHER", "CHAINMAIL", "IRON", "GOLD", "DIAMOND" };
		String[] materials = new String[] { "WOOD", "IRON_FENCE", "IRON_INGOT", "GOLD_INGOT", "DIAMOND" };
		for (int i = 0; i < 5; i++) {
			craftingRecipe(item(Material.valueOf(types[i] + "_HELMET"), 1, "Sturdy Helm", "Protection above.."), "ssssosooo",
					new ItemStack[] { item(Material.valueOf(materials[i])) }, new char[] { 's' });
			craftingRecipe(item(Material.valueOf(types[i] + "_CHESTPLATE"), 1, "Sturdy Plate", "Protection afront.."), "sosssssss",
					new ItemStack[] { item(Material.valueOf(materials[i])) }, new char[] { 's' });
			craftingRecipe(item(Material.valueOf(types[i] + "_LEGGINGS"), 1, "Sturdy Leggings", "Protection under.."), "ssssossos",
					new ItemStack[] { item(Material.valueOf(materials[i])) }, new char[] { 's' });
			craftingRecipe(item(Material.valueOf(types[i] + "_BOOTS"), 1, "Sturdy Boots", "Protection below.."), "ooosossos",
					new ItemStack[] { item(Material.valueOf(materials[i])) }, new char[] { 's' });
		}
	}

	public ItemStack item(Material material, int amount, String name, String lore) {
		ItemStack item = new ItemStack(material, amount);
		item.addUnsafeEnchantment(Enchantment.LUCK, 1);
		ItemMeta item_meta = item.getItemMeta();
		item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5" + name));
		List<String> item_lore = new ArrayList<String>();
		item_lore.add(ChatColor.translateAlternateColorCodes('&', "&d" + lore));
		item_meta.setLore(item_lore);
		item.setItemMeta(item_meta);
		return item;
	}

	public ItemStack item(Material material, int amount, byte data, String name, String lore) {
		ItemStack item = new ItemStack(material, amount, data);
		item.addUnsafeEnchantment(Enchantment.LUCK, 1);
		ItemMeta item_meta = item.getItemMeta();
		item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5" + name));
		List<String> item_lore = new ArrayList<String>();
		item_lore.add(ChatColor.translateAlternateColorCodes('&', "&d" + lore));
		item_meta.setLore(item_lore);
		item.setItemMeta(item_meta);
		return item;
	}

	public ItemStack item(Material material) {
		ItemStack item = new ItemStack(material);
		return item;
	}

	@SuppressWarnings("deprecation")
	public void handRecipe(ItemStack item, String rows, ItemStack[] ingredients, char[] ids) {
		ShapedRecipe recipe = new ShapedRecipe(item);
		String row1 = rows.substring(0, 2);
		String row2 = rows.substring(2, 4);
		recipe.shape(row1, row2);
		for (int i = 0; i < ingredients.length; i++) {
			recipe.setIngredient(ids[i], ingredients[i].getData());
		}
		getServer().addRecipe(recipe);
		translateHandRecipe(item, ingredients, rows, ids);
		handRecipeItems.add(item);
	}

	@SuppressWarnings("deprecation")
	public void craftingRecipe(ItemStack item, String rows, ItemStack[] ingredients, char[] ids) {
		ShapedRecipe recipe = new ShapedRecipe(item);
		String row1 = rows.substring(0, 3);
		String row2 = rows.substring(3, 6);
		String row3 = rows.substring(6, 9);
		recipe.shape(row1, row2, row3);
		for (int i = 0; i < ingredients.length; i++) {
			recipe.setIngredient(ids[i], ingredients[i].getData());
		}
		getServer().addRecipe(recipe);
		translateCraftingRecipe(item, ingredients, rows, ids);
		craftingRecipeItems.add(item);
	}

	public void furnaceRecipe(ItemStack result, ItemStack required) {
		FurnaceRecipe recipe = new FurnaceRecipe(result, required.getData());
		getServer().addRecipe(recipe);
		translateFurnaceRecipe(result, required);
		furnaceRecipeItems.add(result);
	}

	public void furnaceRecipeHomeInv() {
		Inventory inv = null;
		final int breakEach = 45;
		for (int i = 0; i < furnaceRecipeItems.size(); i++) {
			if (i % breakEach == 0) {
				inv = Bukkit.createInventory(null, 9 * 6, "Furnace Recipe Navigation");
				inv.setItem(45, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
				inv.setItem(49, item(Material.GREEN_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
				inv.setItem(53, item(Material.YELLOW_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
				furnaceRecipeHome.add(inv);
			}
			inv.setItem(i % breakEach, furnaceRecipeItems.get(i));
		}
	}

	public void craftingRecipeHomeInv() {
		Inventory inv = null;
		final int breakEach = 45;
		for (int i = 0; i < craftingRecipeItems.size(); i++) {
			if (i % breakEach == 0) {
				inv = Bukkit.createInventory(null, 9 * 6, "Crafting Recipe Navigation");
				inv.setItem(45, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
				inv.setItem(49, item(Material.GREEN_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
				inv.setItem(53, item(Material.YELLOW_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
				craftingRecipeHome.add(inv);
			}
			inv.setItem(i % breakEach, craftingRecipeItems.get(i));
		}
	}

	public void handRecipeHomeInv() {
		Inventory inv = null;
		final int breakEach = 45;
		for (int i = 0; i < handRecipeItems.size(); i++) {
			if (i % breakEach == 0) {
				inv = Bukkit.createInventory(null, 9 * 6, "Hand Recipe Navigation");
				inv.setItem(45, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
				inv.setItem(49, item(Material.GREEN_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
				inv.setItem(53, item(Material.YELLOW_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
				handRecipeHome.add(inv);
			}
			inv.setItem(i % breakEach, handRecipeItems.get(i));
		}
	}

	public void translateFurnaceRecipe(ItemStack result, ItemStack required) {
		Inventory recipe = Bukkit.createInventory(null, 36, "Furnace Recipe Guide");
		recipe.setItem(0, required);
		recipe.setItem(1, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 15, "--->", "--->"));
		recipe.setItem(2, result);
		recipe.setItem(18 + 9, item(Material.GREEN_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
		recipe.setItem(18 + 9 + 4, item(Material.YELLOW_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
		recipe.setItem(26 + 9, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
		translatedFurnaceRecipes.add(recipe);
	}

	public void translateCraftingRecipe(ItemStack result, ItemStack[] required, String shape, char[] ids) {
		Inventory recipe = Bukkit.createInventory(null, 36, "Crafting Recipe Guide");
		for (int i = 0; i < ids.length; i++) {
			for (int n = 0; n < shape.length(); n++) {
				char c = shape.charAt(n);
				if (c == ids[i]) {
					if (n > 5) {
						recipe.setItem(n + 12, required[i]);
					} else if (n > 2) {
						recipe.setItem(n + 6, required[i]);
					} else {
						recipe.setItem(n, required[i]);
					}
				}
			}
		}
		recipe.setItem(12, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 15, "--->", "--->"));
		recipe.setItem(13, result);
		recipe.setItem(18 + 9, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
		recipe.setItem(18 + 9 + 4, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
		recipe.setItem(26 + 9, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
		translatedCraftingRecipes.add(recipe);
	}

	public void translateHandRecipe(ItemStack result, ItemStack[] required, String shape, char[] ids) {
		Inventory recipe = Bukkit.createInventory(null, 36, "Hand Recipe Guide");
		for (int i = 0; i < ids.length; i++) {
			for (int n = 0; n < shape.length(); n++) {
				char c = shape.charAt(n);
				if (c == ids[i]) {
					if (n > 1) {
						recipe.setItem(n + 7, required[i]);
					} else {
						recipe.setItem(n, required[i]);
					}
				}
			}
		}
		recipe.setItem(2, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 15, "--->", "--->"));
		recipe.setItem(3, result);
		recipe.setItem(18 + 9, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 1, "Previous", "Flip a page."));
		recipe.setItem(18 + 9 + 4, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 3, "Back", "Go back a page."));
		recipe.setItem(26 + 9, item(Material.BLUE_STAINED_GLASS_PANE, 1, (byte) 2, "Next", "Flip a page."));
		translatedHandRecipes.add(recipe);
	}

	public String prefix(String color) {
		String prefix = ChatColor.translateAlternateColorCodes('&', "&8{&dViesta&8} &" + color);
		return prefix;
	}

	@SuppressWarnings("deprecation")
	public void moreBlocks(Location loc, int width, int length, Material check, Material set) {
		loc.setX(loc.getBlockX() - (width / 2));
		loc.setZ(loc.getBlockZ() - (length / 2));
		for (int x = 0; x < (width + 1); x++) {
			for (int z = 0; z < (length + 1); z++) {
				loc.setY(loc.getBlockY() - 1);
				if (loc.getBlock().getType().equals(check)) {
					loc.setY(loc.getBlockY() + 1);
					if (loc.getBlock().getType().equals(Material.AIR)) {
						loc.getBlock().setType(set);
						//loc.getBlock().setData((byte) 1);
					}
				} else {
					loc.setY(loc.getBlockY() + 1);
				}
				loc.setZ(loc.getBlockZ() + 1);
			}
			loc.setZ(loc.getBlockZ() - (length + 1));
			loc.setX(loc.getBlockX() + 1);
		}
	}

	@SuppressWarnings("deprecation")
	public void updateHealth(Player p) {
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (!config.get("god").equals("Valkyrie")) {
			if ((p.getLevel() & 1) == 0) {
				p.setMaxHealth(p.getLevel() + 6);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 500, 0);
			}
		} else {
			if ((p.getLevel() & 1) == 0) {
				p.setMaxHealth(6);
			}
		}
	}

	public void sendTitle(Player player, String title, String subtitle, String color) {
		String name = player.getName();
		if (subtitle != null) {
			getServer().dispatchCommand(getServer().getConsoleSender(),
					"title " + name + " subtitle {\"text\":\"" + subtitle + "\", \"color\":\"" + color + "\"}");
		}
		getServer().dispatchCommand(getServer().getConsoleSender(),
				"title " + name + " title {\"text\":\"" + title + "\", \"color\":\"" + color + "\"}");
		// {"text":"The End is Near", "bold":true, "italic":true, "color":"red"}
	}

	public void sendSubtitle(Player player, String text, String color) {
		String name = player.getName();
		getServer().dispatchCommand(getServer().getConsoleSender(),
				"title " + name + " subtitle {\"text\":\"" + text + "\", \"color\":\"" + color + "\"}");
		getServer().dispatchCommand(getServer().getConsoleSender(), "title " + name + " title \"" + "" + "\"");
	}

	public void sendActionMessage(Player player, String text, String color) {
		String name = player.getName();
		getServer().dispatchCommand(getServer().getConsoleSender(), "title " + name + " actionbar {\"text\":\"" + text
				+ "\", \"color\":\"" + color + "\", \"bold\":false}");
	}

	@Override
	public void onDisable() {
		// getServer().resetRecipes();
		getServer().clearRecipes();
		PluginDescriptionFile p = this.getDescription();
		getLogger().info(p.getName() + " v" + p.getVersion() + " has been disabled.");
	}

	public Inventory gods() {
		Inventory gods = Bukkit.createInventory(null, 27, "Choose a God");
		gods.setItem(0, invInfo(Material.BONE, "Kai", new String[] {"The sun starves you.", "Mob attacks inflict confusion."}));
		gods.setItem(1, invInfo(Material.POTION, "Kuu", new String[] {"Mob damage greatly increased.", "Mob attacks inflict slowness."}));
		gods.setItem(2, invInfo(Material.SEA_LANTERN, "Vasa", new String[] {"You can't place torches below surface."}));
		gods.setItem(3, invInfo(Material.COAL, "Sakura", new String[] {"Get blindness for the entire night.", "Deserts and cold biomes slow you down."}));
		gods.setItem(4, invInfo(Material.FERMENTED_SPIDER_EYE, "Valkyrie", new String[] {"Health greatly reduced.", "You no longer gain health."}));
		gods.setItem(26, invInfo(Material.ANVIL, "Back", new String[] { "Go Back." }));
		return gods;
	}

	public Inventory traits() {
		Inventory traits = Bukkit.createInventory(null, 27, "Choose a Trait");
		traits.setItem(0, invInfo(Material.BLUE_STAINED_GLASS_PANE, "Wild Child", new String[] {"You have a weird case of", "???. You mine twice as fast", "during the day but twice as", "slow during the night."}));
		traits.setItem(1, invInfo(Material.BLUE_STAINED_GLASS_PANE, "Lonely Wonderer", new String[] { "You want to become a human", "sized fish when you grow up.", "As a result, you no longer have pets,", "that follow you because of your", "weird looking gills that help you", "breathe under water." }));
		traits.setItem(2, invInfo(Material.BLUE_STAINED_GLASS_PANE, "Evil One", new String[] { "Ma and Pa always told you to", "stay out of the sun when you", "were little, but you never", "listened. You are slow during", "the day, but fast during the", "night." }));
		traits.setItem(26, invInfo(Material.ANVIL, "Back", new String[] { "Go Back." }));
		return traits;
	}

	public Inventory help() {
		Inventory help = Bukkit.createInventory(null, 9, "Help Central");
		help.setItem(0, invInfo(Material.BOOK, "Info", new String[] { "Useful info." }));
		help.setItem(1, invInfo(Material.PAPER, "Recipes", new String[] { "View recipes for certain items." }));

		help.setItem(6, invInfo(Material.COW_SPAWN_EGG, "God", new String[] { "Choose your God." }));
		help.setItem(7, invInfo(Material.IRON_SWORD, "Class", new String[] { "Choose your Class." }));
		help.setItem(8, invInfo(Material.FEATHER, "Trait", new String[] { "Choose your Trait." }));
		return help;
	}

	public Inventory recipeGenres() {
		Inventory genres = Bukkit.createInventory(null, 27, "Recipe Genres");
		genres.setItem(0, invInfo(Material.FEATHER, "Hand Recipes", new String[] { "2 x 2" }));
		genres.setItem(1, invInfo(Material.CRAFTING_TABLE, "Crafting Recipes", new String[] { "3 x 3" }));
		genres.setItem(2, invInfo(Material.FURNACE, "Furnace Recipes", new String[] { "1 x 1" }));
		genres.setItem(26, invInfo(Material.ANVIL, "Back", new String[] { "Go Back." }));
		return genres;
	}

	public Inventory info() {
		Inventory info = Bukkit.createInventory(null, 27, "Info");
		info.setItem(0, invInfo(Material.BOOK, "Discord", new String[] { "Link to discord." }));
		info.setItem(1, invInfo(Material.BOOK, "Forum", new String[] { "Link to forum." }));
		info.setItem(2, invInfo(Material.BOOK, "Tutorial", new String[] { "In-game tutorial." }));
		info.setItem(3, invInfo(Material.BOOK, "FAQ", new String[] { "Frequently asked questions." }));
		info.setItem(4, invInfo(Material.BOOK, "Rules", new String[] { "List rules." }));
		info.setItem(26, invInfo(Material.ANVIL, "Back", new String[] { "Go Back." }));
		return info;
	}

	public Inventory races() {
		Inventory races = Bukkit.createInventory(null, 27, "Pick a Class");
		ItemStack shadow = invClassInfo(Material.FEATHER, "Shadow",
				new String[] { "Immune to poisonous water", "Crouching gives fast digging, resistance",
						"Running increases jump height", "Regenerate in dark areas", "Regenerate in moonlight",
						"Run faster, jump higher" },
				new int[] { 5, 10, 20, 30, 40, 50 });
		ItemStack blazer = invClassInfo(Material.BLAZE_POWDER, "Blazer",
				new String[] { "The gods respect you", "PERK REMOVED", "Plant seeds on surface",
						"Regenerate in sunlight", "Regeneration 2x effective", "Never starve in light",
						"The light makes you glow" },
				new int[] { 5, 10, 20, 30, 40, 50, 60 });
		ItemStack lurker = invClassInfo(Material.COAL, "Lurker", new String[] { "Wither in light", "Water is wither",
				"Hit by mob wither", "Regen in any dark area", "Jump high, run fast." }, new int[] { 0, 0, 0, 10, 20 });
		ItemStack xysta = invClassInfo(Material.SAND, "Xysta", new String[] { "Sand safe to walk on",
				"Enter high lvl biomes with low lvl", "Sand in desert drops wood", "Regen and saturation in desert." },
				new int[] { 5, 10, 20, 40 });
		ItemStack wizard = invClassInfo(Material.STICK, "Wizard",
				new String[] { "Stick shoots stuff", "No cooldown for stick", "Arrow shoots stuff",
						"No cooldown for arrow", "Bone shoots stuff", "Blaze rod shoots stuff",
						"Reduced cooldowns by 2s", "Reduced cooldowns by 2s", "No cooldown for bone",
						"No cooldown for blaze rod" },
				new int[] { 10, 15, 20, 30, 40, 60, 70, 80, 90, 100 });
		ItemStack summoner = invClassInfo(Material.BONE, "Summoner",
				new String[] { "Bone summons 1 wolf", "Bone summons 2 wolfs", "Bone summons 3 wolfs",
						"Blaze rod summons iron horse", "Blaze rod summons gold horse", "Arrow summons 1 golem",
						"Arrow summons 2 golems", "Arrow summons 3 golems" },
				new int[] { 10, 20, 30, 40, 50, 60, 70, 80 });
		ItemStack beserker = invClassInfo(Material.IRON_AXE, "Beserker", new String[] { "Give debuffs to enemy on hit",
				"Increased dmg below 33% hp", "Weapons no longer degrade" }, new int[] { 10, 20, 30 });
		ItemStack vishna = invClassInfo(Material.DARK_OAK_LEAVES, "Vishna",
				new String[] { "No longer get effects when eating food", "Get infinite food from fishing rod",
						"PERK REMOVED", "Regen and saturation in jungle.",
						"PERK REMOVED" },
				new int[] { 5, 20, 30, 40, 50 });
		ItemStack enchantress = invClassInfo(Material.BOOK, "Enchantress",
				new String[] { "Under development." },
				new int[] { 5, 20, 30, 40, 50 });
		ItemStack archer = invClassInfo(Material.BOW, "Archer", new String[] {"Under development. Very powerful with bow.."}, new int[] {10});
		ItemStack healer = invClassInfo(Material.POTION, "Healer", new String[] {"Under development. Heal stuff.."}, new int[] {10});
		ItemStack miner = invClassInfo(Material.GOLDEN_PICKAXE, "Miner", new String[] {"Under development. Get more materials.."}, new int[] {10});
		ItemStack gatherer = invClassInfo(Material.BIRCH_LEAVES, "Gatherer", new String[] {"Under development. Gather more materials.."}, new int[] {10});
		ItemStack angel = invClassInfo(Material.FEATHER, "Angel", new String[] {"Under development. Fly / powerful in sky."}, new int[] {10});
		ItemStack dweller = invClassInfo(Material.BOW, "Dweller", new String[] {"Under development. Very powerful underground.."}, new int[] {10});
		ItemStack farmer = invClassInfo(Material.WOODEN_HOE, "Farmer", new String[] {"Under development. Specializes in farming.."}, new int[] {10});
		races.setItem(0, shadow);
		races.setItem(1, blazer);
		races.setItem(2, lurker);
		races.setItem(3, xysta);
		races.setItem(4, wizard);
		races.setItem(5, summoner);
		races.setItem(6, beserker);
		races.setItem(7, vishna);
		races.setItem(8, enchantress);
		races.setItem(9, archer);
		races.setItem(10, healer);
		races.setItem(11, miner);
		races.setItem(12, gatherer);
		races.setItem(13, angel);
		races.setItem(14, dweller);
		races.setItem(15, farmer);
		races.setItem(26, invInfo(Material.ANVIL, "Back", new String[] { "Go Back." }));
		return races;
	}

	public ItemStack invClassInfo(Material item, String name, String[] lore, int[] level) {
		ItemStack stack = new ItemStack(item);
		stack.addUnsafeEnchantment(Enchantment.FROST_WALKER, 1);
		ItemMeta stack_meta = stack.getItemMeta();
		stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + name));
		List<String> item_lore = new ArrayList<String>();
		for (int i = 0; i < lore.length; i++) {
			item_lore.add(
					ChatColor.translateAlternateColorCodes('&', "&2" + lore[i] + " &8(&7LvL &a" + level[i] + "&8)"));
		}
		stack_meta.setLore(item_lore);
		stack.setItemMeta(stack_meta);
		return stack;
	}

	public ItemStack invInfo(Material item, String name, String[] lore) {
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

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("version")) {
			PluginDescriptionFile plug = this.getDescription();
			sender.sendMessage("Running on " + plug.getName() + " v" + plug.getVersion() + " by " + plug.getAuthors());
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("home")) {
			Player target = Bukkit.getServer().getPlayer(sender.getName());
			Location bed = target.getBedSpawnLocation();
			if (bed != null) {
				target.sendMessage(prefix("e") + "Teleporting you to your bed.");
				target.teleport(bed);
			} else {
				target.sendMessage(prefix("e") + "Make a bed first.");
			}
		}

		if (cmd.getName().equalsIgnoreCase("recipe")) {
			Player target = Bukkit.getServer().getPlayer(sender.getName());
			if (args.length == 0) {
				target.sendMessage(prefix("c") + "Please specify a valid recipe. ex /recipe #");
				return true;
			}
			try {
				int value = Integer.parseInt(args[0]);
				// is an integer!
				if (value >= 0 && value <= translatedHandRecipes.size() - 1) {
					invHandRecipe.put(target.getName(), value);
					target.openInventory(translatedHandRecipes.get(value));
				} else {
					target.sendMessage(
							prefix("4" + "Valid numbers range between 0 and " + (translatedHandRecipes.size() - 1)));
				}
			} catch (NumberFormatException e) {
				target.sendMessage(prefix("4") + "Enter a integer! ex. 1, 2, 3, 4...");
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tutorial")) {
			Player target = Bukkit.getServer().getPlayer(sender.getName());
			if (args.length == 0) {
				target.sendMessage(prefix("c") + "Please specify a number. ex. /tutorial 1");
				return true;
			}
			target.sendMessage(prefix("8") + "--------------------------------");
			switch (args[0]) {
			default:
				target.sendMessage(prefix("b") + "Tutorial coming soon..");
				break;
			}
			target.sendMessage(prefix("8") + "--------------------------------");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("trail")) {
			Player target = Bukkit.getServer().getPlayer(sender.getName());
			if (target.isOp()) {
				if (args.length != 1) {
					target.sendMessage("Specify the right amount of arguements.");
					return true;
				}
				PlayerFiles cm = PlayerFiles.getConfig(target);
				FileConfiguration config = cm.getConfig();
				if (config.get("trail") == null) {
					config.set("trail", "NONE");
				}
				config.set("trail", args[0].toUpperCase());
			} else {
				target.sendMessage("Your not op.");
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("nick")) {
			if (args.length != 2) {
				if (args.length != 0) {
					Player target = Bukkit.getServer().getPlayer(sender.getName());
					String coloredName = ChatColor.translateAlternateColorCodes('&', args[0]);
					target.setDisplayName(coloredName);
					PlayerFiles cm = PlayerFiles.getConfig(target);
					FileConfiguration config = cm.getConfig();
					config.set("nick", args[1]);
					cm.saveConfig();
					target.sendMessage(prefix("5") + sender.getName() + " changed your nickname to " + coloredName);
					sender.sendMessage(prefix("5") + "Nickname set");
					return true;
				} else {
					return false;
				}
			}

			Player target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(prefix("5") + args[0] + " is not online");
				return true;
			}

			target.setDisplayName(args[1]);
			PlayerFiles cm = PlayerFiles.getConfig(target);
			FileConfiguration config = cm.getConfig();
			config.set("nick", args[1]);
			cm.saveConfig();
			sender.sendMessage(prefix("5") + args[0] + "'s nickname is now " + args[1]);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("rank")) {
			Player p = Bukkit.getServer().getPlayer(sender.getName());
			if (p.isOp()) {
				if (args.length < 2) {
					p.sendMessage("Usage: /rank <player> <rank>");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				PlayerFiles cm = PlayerFiles.getConfig(target);
				FileConfiguration config = cm.getConfig();
				config.set("rank", args[1]);
				cm.saveConfig();
				p.sendMessage("Set rank " + args[1] + " for " + target.getDisplayName());
				return true;
			} else {
				p.sendMessage("Not enough permissions.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("color")) {
			Player p = Bukkit.getServer().getPlayer(sender.getName());
			if (p.isOp()) {
				if (args.length < 2) {
					p.sendMessage("Usage: /color <player> <color>");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				PlayerFiles cm = PlayerFiles.getConfig(target);
				FileConfiguration config = cm.getConfig();
				config.set("color", args[1]);
				cm.saveConfig();
				p.sendMessage("Set color " + args[1] + " for " + target.getDisplayName());
				return true;
			} else {
				p.sendMessage("Not enough permissions.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("heal")) {
			if (args.length != 1) {
				Player target = Bukkit.getServer().getPlayer(sender.getName());
				target.setHealth(target.getMaxHealth());
				target.setFoodLevel(40);
				sender.sendMessage(prefix("5") + "Healed self");
				return true;
			}

			Player target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(prefix("5") + args[0] + " is not online");
				return true;
			}

			target.setHealth(target.getMaxHealth());
			target.setFoodLevel(40);
			target.sendMessage(prefix("5") + "You were healed by " + sender.getName());
			sender.sendMessage(prefix("5") + "Healed " + args[0]);
			return true;
		}

		Player p = null;
		if (sender instanceof Player) {
			p = (Player) sender;
		}

		if (cmd.getName().equalsIgnoreCase("spawn")) {
			World world = Bukkit.getWorld("spawn");
			p.teleport(world.getSpawnLocation());
			p.sendMessage(prefix("5") + "Teleporting you to the spawn..");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("help")) {
			Player target = Bukkit.getServer().getPlayer(sender.getName());
			target.openInventory(help());
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (!(p == null)) {
				if (p.hasPermission("viesta.overridecooldown")) {

				} else {
					int cooldown = 60;
					if (tpaCooldown.containsKey(p.getName())) {
						long diff = (System.currentTimeMillis() - tpaCooldown.get(sender.getName())) / 1000;
						if (diff < cooldown) {
							p.sendMessage(
									prefix("5") + "Please wait " + cooldown + " seconds between teleport requests.");
							return false;
						}
					}
				}

				if (args.length > 0) {
					final Player target = getServer().getPlayer(args[0]);
					long keepAlive = 30 * 20;

					if (target == null) {
						sender.sendMessage(prefix("5") + "You can only send a teleport request to online players!");
						return false;
					}

					if (target == p) {
						sender.sendMessage(prefix("5") + "You can't teleport to yourself!");
						return false;
					}

					sendRequest(p, target);

					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							killRequest(target.getName());
						}
					}, keepAlive);

					tpaCooldown.put(p.getName(), System.currentTimeMillis());
				} else {
					p.sendMessage(prefix("5") + "/tpa <player>");
				}
			} else {
				sender.sendMessage(prefix("5") + "The console can't teleport to people, silly!");
				return false;
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tpaccept")) {
			if (!(p == null)) {
				if (currentRequest.containsKey(p.getName())) {

					Player heIsGoingOutOnADate = getServer().getPlayer(currentRequest.get(p.getName()));
					currentRequest.remove(p.getName());

					if (!(heIsGoingOutOnADate == null)) {
						heIsGoingOutOnADate.teleport(p);
						p.sendMessage(prefix("5") + "Teleporting...");
						heIsGoingOutOnADate.sendMessage(ChatColor.GRAY + "Teleporting...");
					} else {
						sender.sendMessage(prefix("5")
								+ "It appears that the person trying to teleport to you doesn't exist anymore. WHOA!");
						return false;
					}
				} else {
					sender.sendMessage(prefix("5")
							+ "It doesn't appear that there are any current tp requests. Maybe it timed out?");
					return false;
				}
			} else {
				sender.sendMessage(prefix("5") + "The console can't accept teleport requests, silly!");
				return false;
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tpdeny")) {
			if (!(p == null)) {
				if (currentRequest.containsKey(p.getName())) {
					Player poorRejectedGuy = getServer().getPlayer(currentRequest.get(p.getName()));
					currentRequest.remove(p.getName());

					if (!(poorRejectedGuy == null)) {
						poorRejectedGuy.sendMessage(prefix("5") + p.getName() + " rejected your teleport request! :(");
						p.sendMessage(prefix("5") + poorRejectedGuy.getName() + " was rejected!");
						return true;
					}
				} else {
					sender.sendMessage(prefix("5")
							+ "It doesn't appear that there are any current tp requests. Maybe it timed out?");
					return false;
				}
			} else {
				sender.sendMessage(prefix("5") + "The console can't deny teleport requests, silly!");
				return false;
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("spawnmob")) {
			if (!p.isOp()) {
				sender.sendMessage(ChatColor.RED + "Only Admins may use this.");
				return true;
			}
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED
						+ "Usage: /spawnmob <mob> <wpn_main> <wpn_off> <helm> <chest> <legs> <boots> <name>");
				return true;
			}

			// mob to spawn
			try {
				EntityType type = EntityType.valueOf(args[0].toUpperCase());
				Block target = p.getTargetBlock((Set<Material>) null, 200);
				World world = p.getWorld();

				Location loc = new Location(world, target.getX(), target.getY() + 2, target.getZ());

				LivingEntity mob = (LivingEntity) world.spawnEntity(loc, type);

				mob.setRemoveWhenFarAway(true);

				EntityEquipment equip = mob.getEquipment();

				equip.setHelmetDropChance(0);
				equip.setChestplateDropChance(0);
				equip.setLeggingsDropChance(0);
				equip.setBootsDropChance(0);

				if (args.length > 1) {
					Material main_hand = Material.valueOf(args[1].toUpperCase());
					equip.setItemInMainHand(new ItemStack(main_hand));
				}

				if (args.length > 2) {
					Material off_hand = Material.valueOf(args[2].toUpperCase());
					equip.setItemInOffHand(new ItemStack(off_hand));
				}

				if (args.length > 3) {
					ItemStack head = new ItemStack(Material.SKELETON_SKULL, 1, (short) SkullType.PLAYER.ordinal());
					SkullMeta headmeta = (SkullMeta) head.getItemMeta();
					headmeta.setOwner(args[3]);
					head.setItemMeta(headmeta);
					equip.setHelmet(head);
				}

				if (args.length > 4) {
					Material chest = Material.valueOf(args[4].toUpperCase());
					equip.setChestplate(new ItemStack(chest));
				}

				if (args.length > 5) {
					Material leggings = Material.valueOf(args[5].toUpperCase());
					equip.setLeggings(new ItemStack(leggings));
				}

				if (args.length > 6) {
					Material boots = Material.valueOf(args[6].toUpperCase());
					equip.setBoots(new ItemStack(boots));
				}

				if (args.length > 7) {
					mob.setCustomNameVisible(true);
					mob.setCustomName(args[7]);
				}

				sender.sendMessage(ChatColor.GREEN + "Sucessfully spawned the mob.");

			} catch (Exception err) {
				sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + err);
			}

			return true;
		}

		return false;
	}

	public boolean killRequest(String key) {
		if (currentRequest.containsKey(key)) {
			Player loser = getServer().getPlayer(currentRequest.get(key));
			if (!(loser == null)) {
				loser.sendMessage(prefix("5") + "Your teleport request timed out.");
			}

			currentRequest.remove(key);

			return true;
		} else {
			return false;
		}
	}

	public void sendRequest(Player sender, Player recipient) {
		sender.sendMessage(prefix("5") + "Sending a teleport request to " + recipient.getName() + ".");

		String sendtpaccept = "";
		String sendtpdeny = "";

		sendtpaccept = " To accept the teleport request, type " + ChatColor.RED + "/tpaccept" + ChatColor.DARK_GRAY
				+ ".";

		sendtpdeny = " To deny the teleport request, type " + ChatColor.RED + "/tpdeny" + ChatColor.DARK_GRAY + ".";

		recipient.sendMessage(prefix("5") + sender.getName() + ChatColor.DARK_GRAY
				+ " has sent a request to teleport to you." + sendtpaccept + sendtpdeny);
		currentRequest.put(recipient.getName(), sender.getName());
	}

}
