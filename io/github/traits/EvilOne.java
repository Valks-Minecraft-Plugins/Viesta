package io.github.traits;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class EvilOne implements Listener {
	Viesta plugin;
	
	public EvilOne(Viesta instance) {
		plugin = instance;
	}
}
