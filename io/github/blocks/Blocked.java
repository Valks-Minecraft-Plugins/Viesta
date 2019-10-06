package io.github.blocks;

import org.bukkit.event.Listener;

import io.github.valk.Viesta;

public class Blocked implements Listener{
	Viesta plugin;
	
	public Blocked(Viesta instance) {
		plugin = instance;
	}
}
