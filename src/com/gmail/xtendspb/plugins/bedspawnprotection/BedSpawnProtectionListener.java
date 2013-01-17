package com.gmail.xtendspb.plugins.bedspawnprotection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BedSpawnProtectionListener implements Listener{

	@EventHandler

	public void onPlayerDeath(PlayerDeathEvent event){
		
		Player player = event.getEntity();
		if(player.getBedSpawnLocation() != null){
			if(player.getKiller() instanceof Player && player.getLocation().distance(player.getBedSpawnLocation()) < 8){
					Player killer = player.getKiller();
					killer.sendMessage(ChatColor.RED + "Dont kill players on the bed respawn!");
					killer.damage(10);
			}
			
		}
	}
}
	
