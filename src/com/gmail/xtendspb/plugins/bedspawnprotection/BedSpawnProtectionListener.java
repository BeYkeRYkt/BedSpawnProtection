package com.gmail.xtendspb.plugins.bedspawnprotection;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BedSpawnProtectionListener implements Listener{

	public BedSpawnProtection plugin;
	public ArrayList<String> players = new ArrayList <String>();

	//Respawn Event
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		final Player player = event.getPlayer();
		final Location respawn = player.getWorld().getSpawnLocation();	
		//Start Timer for PotionEffect
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {            
            	//Check : Killng Player from bad killers
            	if(players.contains(player.getName())){
            	//Teleport to World Spawn Location
            	 player.teleport(respawn);		
         //Add Potion
            	 player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
            	 players.remove(player.getName());
            	}
            }
        }, 1);

	}
	
	
//Death Event
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		if(player.getBedSpawnLocation() != null){
			if(player.getKiller() instanceof Player && player.getLocation().distance(player.getBedSpawnLocation()) < plugin.getConfig().getDouble("Radius-protection")){
					Player killer = player.getKiller();
					
					//Mesasge
					String message = plugin.getConfig().getString("Locale.warning-killer");
					killer.sendMessage(ChatColor.RED + message);
					
					//Added new ArrayList
					if(!players.contains(player.getName())){
					players.add(player.getName());
					}
					
					
					//PotionEnable = false
					if(!plugin.getConfig().getBoolean("Potion-effect-enable")){
						killer.damage(10);
					}else{
					if(plugin.getConfig().getString("Potion-amount").equals("ONE")){
					//Config: Potion-effect-enable: false
						//Type in config
						String potion = plugin.getConfig().getString("Potion-effect-name");
						//Time
						int time = plugin.getConfig().getInt("Potion-effect-time");
						//20 ticks = 1 seconds
						int seconds = time * 20;
						//Level potion
						int level = plugin.getConfig().getInt("Potion-effect-level");
						//AddPotion
						killer.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), seconds, level));
					}else if(plugin.getConfig().getString("Potion-amount").equals("LIST")){
					//Config: Potion-effect-list-enable: true
						List<String> list = plugin.getConfig().getStringList("Potion-list");
						for(String s: list){
							//Type in config
							String potion = s;
							//Time
							int time = plugin.getConfig().getInt("Potion-effect-time");
							//20 ticks = 1 seconds
							int seconds = time * 20;
							//Level potion
							int level = plugin.getConfig().getInt("Potion-effect-level");
							//AddPotion
							killer.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), seconds, level));
					}
					}
					}
			}


		}
	}
}
