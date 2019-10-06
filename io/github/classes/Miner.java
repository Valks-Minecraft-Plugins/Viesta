package io.github.classes;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Miner implements Listener{
	Viesta plugin;
	
	public Miner(Viesta instance) {
		plugin = instance;
	}
}
