package io.github.classes;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Healer implements Listener{
	Viesta plugin;
	
	public Healer(Viesta instance) {
		plugin = instance;
	}
}
