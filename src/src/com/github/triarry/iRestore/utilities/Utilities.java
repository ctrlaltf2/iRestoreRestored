package com.github.triarry.iRestore.utilities;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.triarry.iRestore.iRestore;

public class Utilities {
	
	private iRestore plugin;
	static Utilities instance = new Utilities();
	
    public static Utilities getUtilities() {
        return instance;
    }
  
    public void startUp(iRestore plug) {
        plugin = plug;
    }

	public void blacklistItems(Player p) {
		if (plugin.getConfig().getBoolean("blacklist.enabled") == true) {
			for (String itemList : plugin.getConfig().getStringList("blacklist.items")) {
				p.getInventory().remove(Material.valueOf(itemList));
				
				if(p.getInventory().getHelmet() != null) {
				    if(p.getInventory().getHelmet().getType() == Material.valueOf(itemList)) {
				    	p.getInventory().setHelmet(null);
				    }
				}
				
				if(p.getInventory().getChestplate() != null) {
				    if(p.getInventory().getChestplate().getType() == Material.valueOf(itemList)) {
				    	p.getInventory().setChestplate(null);
				    }
				}
				
				if(p.getInventory().getLeggings() != null) {
				    if(p.getInventory().getLeggings().getType() == Material.valueOf(itemList)) {
				    	p.getInventory().setLeggings(null);
				    }
				}
				
				if(p.getInventory().getBoots() != null) {
				    if(p.getInventory().getBoots().getType() == Material.valueOf(itemList)) {
				    	p.getInventory().setBoots(null);
				    }
				}
			} 
		}
	}

	public void whitelistItems(Player p) {
		Boolean itemCheck = false;
		
		if (plugin.getConfig().getBoolean("whitelist.enabled") == true) {
			for (ItemStack stackList : p.getInventory().getContents()) {
				for (String itemList : plugin.getConfig().getStringList("whitelist.items")) {
					if (stackList != null) {
						if (stackList.getType() == Material.valueOf(itemList)){
							itemCheck = true;
						}
					}
				}
				
				if (itemCheck != true) {
					if (stackList != null) {
						p.getInventory().remove(stackList);
					}
				}
				
				else {
					itemCheck = false;
				}
			} 
		}
	}

	public void whitelistArmor(Player p) {
		Boolean helmetCheck = false;
		Boolean chestplateCheck = false;
		Boolean leggingsCheck = false;
		Boolean bootsCheck = false;
		
		if (plugin.getConfig().getBoolean("whitelist.enabled") == true) {
			for (ItemStack stackList : p.getInventory().getArmorContents()) {
				for (String itemList : plugin.getConfig().getStringList("whitelist.items")) {
					if (stackList != null) {
						if (stackList.getType() == Material.valueOf(itemList)){
							if(p.getInventory().getHelmet() != null && helmetCheck == false) {
							    if(p.getInventory().getHelmet().getType() == Material.valueOf(itemList)) {
							    	p.getInventory().setHelmet(stackList);
							    	helmetCheck = true;
							    }
							}
							
							if(p.getInventory().getChestplate() != null && chestplateCheck == false) {
							    if(p.getInventory().getChestplate().getType() == Material.valueOf(itemList)) {
							    	p.getInventory().setChestplate(stackList);
							    	chestplateCheck = true;
							    }
							}
							
							if(p.getInventory().getLeggings() != null && leggingsCheck == false) {
							    if(p.getInventory().getLeggings().getType() == Material.valueOf(itemList)) {
							    	p.getInventory().setLeggings(stackList);
							    	leggingsCheck = true;
							    }
							}
							
							if(p.getInventory().getBoots() != null && bootsCheck == false) {
							    if(p.getInventory().getBoots().getType() == Material.valueOf(itemList)) {
							    	p.getInventory().setBoots(stackList);
							    	bootsCheck = true;
							    }
							}
						}
					}
				}
			}
			
			if (helmetCheck == false) {
				p.getInventory().setHelmet(null);
			}
			
			if (chestplateCheck == false) {
				p.getInventory().setChestplate(null);
			}
			
			if (leggingsCheck == false) {
				p.getInventory().setLeggings(null);
			}
			
			if (bootsCheck == false) {
				p.getInventory().setBoots(null);
			}
		}
	}
	
	public void dropPercentageRemove(Player p, List<ItemStack> i) {
		for (ItemStack itemList : i) {
			p.getInventory().remove(itemList);
			
			if(p.getInventory().getHelmet() != null) {
			    if(p.getInventory().getHelmet() == itemList) {
			    	p.getInventory().setHelmet(null);
			    }
			}
			
			if(p.getInventory().getChestplate() != null) {
			    if(p.getInventory().getChestplate() == itemList) {
			    	p.getInventory().setChestplate(null);
			    }
			}
			
			if(p.getInventory().getLeggings() != null) {
			    if(p.getInventory().getLeggings() == itemList) {
			    	p.getInventory().setLeggings(null);
			    }
			}
			
			if(p.getInventory().getBoots() != null) {
			    if(p.getInventory().getBoots() == itemList) {
			    	p.getInventory().setBoots(null);
			    }
			}
		} 
	}
}