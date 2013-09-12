package com.gmail.xtendspb.plugins.bedspawnprotection;


import java.io.File;
import java.util.List;

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
				fc.addDefault("Potion-effect-enable", true);
				fc.addDefault("Potion-amount", "ONE");
				fc.addDefault("Potion-effect-name", "CONFUSION");
				fc.addDefault("Potion-effect-time", 60);
				fc.addDefault("Potion-effect-level", 2);
				//StringList
				List<String> list = fc.getStringList("Potion-list");
				list.add("CONFUSION");
				list.add("BLINDNESS");
                                fc.set("Potion-list", list);
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
		
		this.getLogger().info("Plugin is Enabled!");
	}


}
