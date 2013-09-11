package com.gmail.xtendspb.plugins.bedspawnprotection;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BedSpawnProtectionListener implements Listener{

	public BedSpawnProtection plugin;
	
	//Respawn Event
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		//Player
		Player player = event.getPlayer();
		//Respawn Location
		Location respawn = event.getRespawnLocation();
		//DamageCause
		DamageCause cause = player.getLastDamageCause().getCause();
		//Type Cause and teleport to Respawn
		if(cause == DamageCause.ENTITY_ATTACK){
        player.teleport(respawn);
		}
		
	}
	
//Death Event
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){

		Player player = event.getEntity();
		if(player.getBedSpawnLocation() != null){
			if(player.getKiller() instanceof Player && player.getLocation().distance(player.getBedSpawnLocation()) < plugin.getConfig().getDouble("Radius-protection")){
					Player killer = player.getKiller();
					String message = plugin.getConfig().getString("Locale.warning-killer");
					killer.sendMessage(ChatColor.RED + message);
					
					if(plugin.getConfig().getString("Potion-amount").equals("ONE")){
					//Config: Potion-effect-enable: false
					if(!plugin.getConfig().getBoolean("Potion-effect-one-enable")){
					killer.damage(10);
					//Config: Potion-effect-enable: true
					}else{
						//Type in config
						String potion = plugin.getConfig().getString("potion-effect-type");
						//Time
						int time = plugin.getConfig().getInt("potion-effect-time");
						//20 ticks = 1 seconds
						int seconds = time * 20;
						//Level potion
						int level = plugin.getConfig().getInt("potion-effect-level");
						//AddPotion
						killer.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), seconds, level));
					}
					}else if(plugin.getConfig().getString("Potion-amount").equals("LIST")){
					//Config: Potion-effect-list-enable: true
					if (plugin.getConfig().getBoolean("Potion-effect-list-enable")){
						List<String> list = plugin.getConfig().getStringList("Potion-list");
						for(String s: list){
							//Type in config
							String potion = s;
							//Time
							int time = plugin.getConfig().getInt("potion-effect-time");
							//20 ticks = 1 seconds
							int seconds = time * 20;
							//Level potion
							int level = plugin.getConfig().getInt("potion-effect-level");
							//AddPotion
							killer.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), seconds, level));
						}
					}
					}
			}


		}
	}
}
	
