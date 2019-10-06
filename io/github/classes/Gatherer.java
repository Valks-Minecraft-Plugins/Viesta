package io.github.classes;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Gatherer implements Listener{
	Viesta plugin;
	
	public Gatherer(Viesta instance) {
		plugin = instance;
	}
}
