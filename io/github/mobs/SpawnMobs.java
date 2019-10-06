package io.github.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.valk.Viesta;

public class SpawnMobs implements Listener {
	Viesta plugin;
	
	public SpawnMobs(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private boolean deathEvent(EntityDeathEvent e) {
		LivingEntity target = (LivingEntity) e.getEntity();
		World world = target.getLocation().getWorld();
		Location loc = target.getLocation();
		Player p = target.getKiller();
		
		if (!(target instanceof LivingEntity)) {
			return false;
		}
		if (target instanceof HumanEntity) {
			return false;
		}
		if (target instanceof Player) {
			return false;
		}

		if (p == null) {
			return false;
		}

		if (p.isDead() && !p.isOnline()) {
			return false;
		}

		for (ItemStack i : e.getDrops()) {
			i.setType(Material.AIR);
		}
		e.setDroppedExp(0);
		p.giveExp(10);
		plugin.updateHealth(p);

		world.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 100, 0);

		world.spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 4);
		world.spawnParticle(Particle.PORTAL, target.getLocation(), 50);

		EntityType type = target.getType();
		
		plugin.moreBlocks(loc, 4, 4, Material.GRASS, Material.TALL_GRASS);

		switch (type) {
		case ENDERMITE:
			dropItem(target, 1, plugin.item(Material.STICK, 1, "Twig", "Perhaps combine it?"));
			break;
		case SPIDER:
			dropItem(target, 1, plugin.item(Material.STRING, 1, "Silk", "Perhaps combine it?"));
			dropBonemeal(target);
			p.sendMessage(plugin.prefix("5") + "Its guts burst out everywhere!!");
			for (int n = 0; n < 8; n++) {
				LivingEntity mob = (LivingEntity) world.spawnEntity(loc, EntityType.ENDERMITE);
				mob.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6000, 2));
				mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
				mob.setRemoveWhenFarAway(true);
			}
			break;
		case ZOMBIE:
			if (target.getFireTicks() > 0) {

			} else {
				if (target.getWorld().getEntitiesByClass(Zombie.class).size() < 30) {
					plugin.sendActionMessage(p, "You must burn it to keep it dead forever!!", "red");
					for (int n = 0; n < 2; n++) {
						LivingEntity zombie = spawnMob(world, loc, "&cUndead", "valkyrienyanko", EntityType.ZOMBIE, 0,
								null, null, null, null, null, null, false);
						zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
					}
				}
			}
			break;
		case WITHER_SKELETON:
			plugin.sendActionMessage(p, "Ewwww!!", "red");
			for (int n = 0; n < 2; n++) {
				LivingEntity cave_spider = spawnMob(world, loc, "&cSlimy Remains", "valkyrienyanko", EntityType.SLIME,
						0, null, null, null, null, null, null, false);
				cave_spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
			}
			break;
		case SKELETON:
			plugin.sendActionMessage(p, "A horse spawns!!", "red");
			for (int n = 0; n < 2; n++) {
				LivingEntity cave_spider = spawnMob(world, loc, "&cYour Best Friend", "valkyrienyanko",
						EntityType.HORSE, 0, null, null, null, null, null, null, false);
				cave_spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
			}
			break;
		case CAVE_SPIDER:
			if (target.getWorld().getEntitiesByClass(Zombie.class).size() < 40) {
				plugin.sendActionMessage(p, "Evil lurkers will never stop coming no matter what!!", "red");
				for (int n = 0; n < 2; n++) {
					LivingEntity cave_spider = spawnMob(world, loc, "&cEvil Lurker", "valkyrienyanko",
							EntityType.CAVE_SPIDER, 0, null, null, null, null, null, null, false);
					cave_spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
				}
			}
			break;
		default:
			break;
		}

		return false;
	}


	@EventHandler
	private boolean mobController(CreatureSpawnEvent e) {
		World world = e.getLocation().getWorld();
		Location loc = e.getLocation();
		Biome biome = loc.getBlock().getBiome();
		
		if (e.getSpawnReason().equals(SpawnReason.NETHER_PORTAL)) {
			e.setCancelled(true);
		}

		if (e.getSpawnReason() != SpawnReason.NATURAL) {
			return false;
		}

		e.setCancelled(true);

		switch (biome) {
		case DESERT:
		case DESERT_HILLS:
			LivingEntity skele = spawnMob(world, loc, "&cTemper", "valkyrienyanko", EntityType.SKELETON, 0, null, null,
					null, null, null, null, false);
			skele.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
			break;
		case ICE_SPIKES:
		case TAIGA:
			LivingEntity wither_skele = spawnMob(world, loc, "&cThe Insane", "valkyrienyanko",
					EntityType.WITHER_SKELETON, 0, null, null, null, null, null, null, false);
			wither_skele.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 2));
			break;
		case JUNGLE:
		case JUNGLE_EDGE:
		case JUNGLE_HILLS:
			LivingEntity cave_spider = spawnMob(world, loc, "&cEvil Lurker", "valkyrienyanko", EntityType.CAVE_SPIDER,
					0, null, null, null, null, null, null, false);
			cave_spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
			break;
		case GIANT_SPRUCE_TAIGA_HILLS:
		case GIANT_TREE_TAIGA_HILLS:
			LivingEntity zombie = spawnMob(world, loc, "&cUndead", "valkyrienyanko", EntityType.ZOMBIE, 0, null, null,
					Material.BLACK_WOOL, null, null, null, false);
			zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
			break;
		default:
			// LivingEntity spider = (LivingEntity) world.spawnEntity(loc,
			// EntityType.SPIDER);
			LivingEntity spider = spawnMob(world, loc, "&cLurker", null, EntityType.SPIDER, 0, null, null, null, null,
					null, null, false);
			spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
			spider.setRemoveWhenFarAway(true);
			break;
		}

		return false;
	}
	
	private LivingEntity spawnMob(World w, Location loc, String name, String owner, EntityType type, int color,
			Material mainWeap, Material otherWeap, Material helm, Material chest, Material leggings, Material boots,
			boolean invisible) {
		LivingEntity mob = (LivingEntity) w.spawnEntity(loc, type);

		mob.setRemoveWhenFarAway(true);
		mob.addScoreboardTag("hostile");
		mob.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
		mob.setCustomNameVisible(false);
		if (invisible) {
			mob.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 2));
		}
		if (loc.getY() > 60) {
			// mob.setGlowing(true);
		}

		// set drop chance to 0
		EntityEquipment armor = mob.getEquipment();
		armor.setItemInMainHandDropChance(0F);
		armor.setItemInOffHandDropChance(0F);
		armor.setHelmetDropChance(0F);
		armor.setChestplateDropChance(0F);
		armor.setLeggingsDropChance(0F);
		armor.setBootsDropChance(0F);

		// add the gear
		if (mainWeap != null) {
			ItemStack weapon1 = new ItemStack(mainWeap);
			armor.setItemInMainHand(weapon1);
		}
		if (otherWeap != null) {
			ItemStack weapon2 = new ItemStack(otherWeap);
			armor.setItemInOffHand(weapon2);
		}
		if (helm != null) {
			ItemStack head = new ItemStack(helm);
			if (helm == Material.BLACK_WOOL) {
				// colored wool head
				head = new ItemStack(Material.SKELETON_SKULL, 1, (short) SkullType.PLAYER.ordinal());
				SkullMeta headmeta = (SkullMeta) head.getItemMeta();
				if (owner != null) {
					headmeta.setOwner(owner);
				}
				head.setItemMeta(headmeta);
			}
			if (helm == Material.LEATHER_HELMET) {
				LeatherArmorMeta helmmeta = (LeatherArmorMeta) head.getItemMeta();
				helmmeta.setColor(Color.fromRGB(color, color, color));
				head.setItemMeta(helmmeta);
			}
			armor.setHelmet(head);
		}
		if (chest != null) {
			ItemStack body = new ItemStack(chest);
			if (chest == Material.LEATHER_CHESTPLATE) {
				LeatherArmorMeta chestmeta = (LeatherArmorMeta) body.getItemMeta();
				chestmeta.setColor(Color.fromRGB(color, color, color));
				body.setItemMeta(chestmeta);
			}
			armor.setChestplate(body);
		}
		if (leggings != null) {
			ItemStack legs = new ItemStack(leggings);
			if (leggings == Material.LEATHER_LEGGINGS) {
				LeatherArmorMeta leggingsmeta = (LeatherArmorMeta) legs.getItemMeta();
				leggingsmeta.setColor(Color.fromRGB(color, color, color));
				legs.setItemMeta(leggingsmeta);
			}
			armor.setLeggings(legs);
		}
		if (boots != null) {
			ItemStack feet = new ItemStack(boots);
			if (boots == Material.LEATHER_BOOTS) {
				LeatherArmorMeta bootsmeta = (LeatherArmorMeta) feet.getItemMeta();
				bootsmeta.setColor(Color.fromRGB(color, color, color));
				feet.setItemMeta(bootsmeta);
			}
			armor.setBoots(feet);
		}
		return mob;
	}
	
	private Item dropItem(LivingEntity target, double chance, ItemStack item) {
		double random = Math.random();
		if (random < chance) {
			return target.getWorld().dropItemNaturally(target.getLocation(), item);
		}
		return null;
	}

	private Item dropBonemeal(LivingEntity target) {
		ItemStack bonemeal = new ItemStack(Material.INK_SAC, 1, (short) 15);
		return target.getWorld().dropItemNaturally(target.getLocation(), bonemeal);
	}
}
