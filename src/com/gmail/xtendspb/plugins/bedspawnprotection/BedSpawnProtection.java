package com.gmail.xtendspb.plugins.bedspawnprotection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BedSpawnProtection extends JavaPlugin{
	protected FileConfiguration config;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new BedSpawnProtectionListener(), this);
	}
	
}
