package io.github.utils;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import io.github.valk.Viesta;

public class SignColor implements Listener{
	Viesta plugin;
	
	public SignColor(Viesta instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		for (int i = 0; i < 4; i++) {
			String line = e.getLine(i);
			if (line != null && !line.equals("")) {
				e.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
			}
		}
	}
}
