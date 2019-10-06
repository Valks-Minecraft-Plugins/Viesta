package io.github.effects;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.configs.PlayerFiles;
import io.github.valk.Viesta;

public class DamageEffects implements Listener{
	Viesta plugin;
	
	public DamageEffects(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	private void consume(PlayerItemConsumeEvent event) {
		int[] effects = { 1, 2, 3, 4, 5 };
		int randomEffect = effects[new Random().nextInt(effects.length)];

		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (config.get("class").equals("Vishna") && p.getLevel() >= 5) {

		} else {
			switch (randomEffect) {
			case 4:
				if (!p.hasPotionEffect(PotionEffectType.REGENERATION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0));
				}
				break;
			case 3:
				if (!p.hasPotionEffect(PotionEffectType.SLOW)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 0));
				}
				break;
			case 2:
				if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 0));
				}
				break;
			case 1:
				if (!p.hasPotionEffect(PotionEffectType.CONFUSION)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 0));
				}
				break;
			default:
				break;
			}
		}

	}
	
	@EventHandler
	private void entityDamaged(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();
			switch (event.getCause()) {
			case LIGHTNING:
			case BLOCK_EXPLOSION:
			case CONTACT:
			case DRAGON_BREATH:
			case DROWNING:
			case ENTITY_EXPLOSION:
			case FALL:
			case FIRE:
			case FALLING_BLOCK:
			case FLY_INTO_WALL:
			case HOT_FLOOR:
			case LAVA:
			case SUFFOCATION:
				if (!target.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 3));
					target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 3));
					target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3));
				}
				break;
			default:
				break;
			}
		}
	}
}
