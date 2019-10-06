package io.github.classes;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Farmer implements Listener{
	Viesta plugin;
	
	public Farmer(Viesta instance) {
		plugin = instance;
	}
}
