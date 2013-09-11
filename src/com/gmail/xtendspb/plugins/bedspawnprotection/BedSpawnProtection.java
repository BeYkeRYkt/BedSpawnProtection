package com.gmail.xtendspb.plugins.bedspawnprotection;


import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class BedSpawnProtection extends JavaPlugin{

	public void onEnable(){
		//Create Config
		PluginDescriptionFile pdFile = getDescription();
		
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header("BedSpawnProtection v" + pdFile.getVersion() + " Configuration" + 
				    "\nOriginal Code: XtenD" +
					"\nModded Dinnerspond_");
				fc.addDefault("Radius-protection", 8);
				fc.addDefault("Potion-amount", "ONE");
				fc.addDefault("Potion-effect-one-enable", true);
				fc.addDefault("Potion-effect-list-enable", false);
				fc.addDefault("Potion-effect-name", "");
				fc.addDefault("Potion-effect-time", 60);
				fc.addDefault("Potion-effect-level", 2);
				//StringList
				fc.createSection("Potion-list");
				fc.set("Potion-list", "CONFUSION");
				//End
				fc.addDefault("Locale.warning-killer", "Не убивай игроков возле их кровати!");
				
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Register Events
		getServer().getPluginManager().registerEvents(new BedSpawnProtectionListener(), this);
		
		//Logger
		this.getLogger().info("Plugin is Enabled!");
		this.getLogger().info("Orignal code: XtenD");
		this.getLogger().info("Modded: Dinnerspond_");
	}


}

