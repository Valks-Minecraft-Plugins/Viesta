package io.github.classes;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Dweller implements Listener{
	Viesta plugin;
	
	public Dweller(Viesta instance) {
		plugin = instance;
	}
}
