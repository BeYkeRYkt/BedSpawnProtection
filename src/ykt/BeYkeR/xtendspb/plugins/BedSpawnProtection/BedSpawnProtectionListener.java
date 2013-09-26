package ykt.BeYkeR.xtendspb.plugins.BedSpawnProtection;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BedSpawnProtectionListener implements Listener{

	public BedSpawnProtection plugin;
	public ArrayList<String> players = new ArrayList <String>();

	
	public BedSpawnProtectionListener(BedSpawnProtection plugin){
		this.plugin = plugin;
	}
	
	//DamageEvent v1.1-[DEV]
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event){
		if(plugin.getConfig().getBoolean("Save-Zone")){
			Entity entity = event.getEntity();
		//Check: Damager instanceof Player
		if(event.getDamager() instanceof Player){
			Player player = (Player) entity;
		//Check: Entity instanceof Player
		if(entity instanceof Player){
			Player player2 = (Player) entity;
			//Check: getBedSpawn
			if(player.getBedSpawnLocation() != null){
			if(player.getLocation().distance(player.getBedSpawnLocation()) < plugin.getConfig().getDouble("Radius-protection") || player2.getLocation().distance(player2.getBedSpawnLocation()) < plugin.getConfig().getDouble("Radius-protection")){
				//Damage Canceled
				event.setCancelled(true);
				event.setDamage(0);
			}
			}
		}
		}
		}
	}
	
	
	//Respawn Event v1.0-[DEV]
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		final Player player = event.getPlayer();
		final Location respawn = player.getWorld().getSpawnLocation();	
		//Start Timer for PotionEffect
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {            
            	if(players.contains(player.getName())){
            	//Teleport to World Spawn Location
            	 player.teleport(respawn);		
         //Add Potion
            	 player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
            	 players.remove(player.getName());
					if(plugin.getConfig().getBoolean("Debug")){
						plugin.getLogger().info("[DEBUG]Player " + player.getName() + " has been respawned");
						}
            	}
            }
        }, 1);

	}
	
//Death Event v1.0
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if(!plugin.getConfig().getBoolean("Save-Zone")){
		Player player = event.getEntity();
		if(player.getBedSpawnLocation() != null){
			if(player.getKiller() instanceof Player && player.getLocation().distance(player.getBedSpawnLocation()) < plugin.getConfig().getDouble("Radius-protection")){
					Player killer = player.getKiller();
					
					//Message to Console
					plugin.getLogger().info("Player " + player.getName() + " killed by " + killer.getName());

					//Message
					String message = plugin.getConfig().getString("Locale.warning-killer");
					killer.sendMessage(ChatColor.RED + message);
					
					//Added new ArrayList
					if(!players.contains(player.getName())){
					players.add(player.getName());
					if(plugin.getConfig().getBoolean("Debug")){
					plugin.getLogger().info("[DEBUG]Player " + player.getName() + "  added ArrayList");
					}
					}
					
					
					//PotionEnable = false
					if(!plugin.getConfig().getBoolean("Potion-effect-enable")){
						killer.damage(10);
						if(plugin.getConfig().getBoolean("Debug")){
							plugin.getLogger().info("[DEBUG]Killer " + killer.getName() + "  damaged 10 heals");
							}
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
						
						if(plugin.getConfig().getBoolean("Debug")){
							plugin.getLogger().info("[DEBUG]Killer " + killer.getName() + "  added PotionEffect");
							}
						
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
							
							if(plugin.getConfig().getBoolean("Debug")){
								plugin.getLogger().info("[DEBUG]Killer " + killer.getName() + "  added list PotionEffect");
								}
					}
					}
					}
			}
		}

		}
	}
}
