package com.github.triarry.iRestore.listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Stray;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;


import com.github.triarry.iRestore.iRestore;
import com.github.triarry.iRestore.utilities.Utilities;

import de.Keyle.MyPet.api.entity.MyPet;
import de.Keyle.MyPet.compat.v1_12_R1.entity.CraftMyPet;
import de.Keyle.MyPet.api.skill.skills.ranged.CraftMyPetProjectile;
import de.Keyle.MyPet.api.skill.skills.ranged.EntityMyPetProjectile;

public class PlayerListener implements Listener {
	
	private iRestore plugin;
	 
	public HashMap<Player , ItemStack[]> items = new HashMap<Player , ItemStack[]>();
	public HashMap<Player , ItemStack[]> armor = new HashMap<Player , ItemStack[]>();
	
	public List<ItemStack> itemsToBeRemoved = new ArrayList<ItemStack>();

    public PlayerListener(iRestore plugin) {
        this.plugin = plugin;
    }
	
	@EventHandler
	public void informAdmins(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		if (iRestore.update && p.hasPermission("irestore.update")) {
			p.sendMessage(ChatColor.GREEN + "An update is available: " + ChatColor.GOLD + iRestore.ver);
			p.sendMessage(ChatColor.GREEN + "Download it here: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/irestore/");
		}
	}
	
	@EventHandler
	public void iRestorePlayerDeath(PlayerDeathEvent event) {
		FileConfiguration config = plugin.getConfig();
        Player p = event.getEntity();
        String killer;
        EntityDamageEvent pDamage = event.getEntity().getLastDamageCause();
        
        if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent lastDamageEvent = (EntityDamageByEntityEvent) p.getLastDamageCause();
            
            if(!p.hasPermission("irestore.participate")) {
            	return;
            }
            
            /*
             * Projectiles
             */
            
            else if(lastDamageEvent.getCause() == DamageCause.PROJECTILE) {
            	Projectile a = (Projectile) lastDamageEvent.getDamager();
            	
            	if(a.getShooter() instanceof Player && (config.getBoolean("events.pvp.bows") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.bows")) { 
            		Player s = (Player) a.getShooter();
            		killer = s.getName();
            	}
            	
            	else if(a.getShooter() instanceof Skeleton) {
            		
            		if(a.getShooter() instanceof WitherSkeleton && (config.getBoolean("events.mobs.wither_skeleton") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.wither_skeleton")) {
            			killer = "A Wither Skeleton";
            		}
            		
            		else if(a.getShooter() instanceof Stray && (config.getBoolean("events.mobs.stray") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.wither_skeleton")) {
            			killer = "A Stray";
            		}
            		
            		else if((config.getBoolean("events.mobs.skeleton") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.skeleton")) {
            			killer = "A Skeleton";
            		}
            		
            		else {
            			return;
            		}
            	}
            	
            	else if(a.getShooter() instanceof Ghast && (config.getBoolean("events.mobs.ghast") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.ghast")) {
            		killer = "A Ghast";
            	}
            	
            	else if(a.getShooter() instanceof Blaze && (config.getBoolean("events.mobs.blaze") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.blaze")) {
            		killer = "A Blaze";
            	}
            	
            	else if(a.getShooter() instanceof Wither && (config.getBoolean("events.mobs.wither") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.wither")) {
            		killer = "A Wither";
            	}
            	
            	else if(a.getShooter() instanceof EnderDragon && (config.getBoolean("events.mobs.enderdragon") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.enderdragon")) {
            		killer = "An Enderdragon";
            	}
            	
            	else if(a.getShooter() instanceof Spellcaster) {
            		
            		if(a.getShooter() instanceof Evoker && (config.getBoolean("events.mobs.evoker") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.evoker")) {
            			killer = "An Evoker";
            		} 
            		
            		else if(a.getShooter() instanceof Illusioner && (config.getBoolean("events.mobs.illusioner") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.illusioner")) {
            			killer = "An Illusioner";
            		}
            		
            		else {
            			return;
            		}
            		
            	}
            	
            	else if(iRestore.myPetEnabled && config.getBoolean("my-pet-enabled")) {
            		if(a.getShooter() instanceof CraftMyPet) {
	            		MyPet myPet = ((CraftMyPet) a.getShooter()).getMyPet();
	            		killer = myPet.getOwner().getName() + "'s pet " + myPet.getPetName();
            		}
            		
            		else {
            			return;
            		}
            		
            	}
            	else { 
            		return;
            	}
            }
            
            /*
             * Magic
             */
            
            else if(lastDamageEvent.getCause() == DamageCause.MAGIC) {
            	try {
                	ThrownPotion a = (ThrownPotion) lastDamageEvent.getDamager();
                	
                	if(a.getShooter() instanceof Witch && (config.getBoolean("events.mobs.witch") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.witch")) {
                		killer = "A Witch";
                	}
                	
                	else if(a.getShooter() instanceof Player && (config.getBoolean("events.pvp.potions") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.potion")) {
                		Player s = (Player) a.getShooter();
                		killer = s.getName();
                	}
                	else {
                		return;
                	}
            	} catch (ClassCastException e) {
            		if ((config.getBoolean("events.pvp.skills") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.potion")) {
                		killer = p.getDisplayName() + "'s skill";
            		} 
            		else {
            			return;
            		}
            	}
            }
            
            /*
             * MyPet
             */
            
            else if(iRestore.myPetEnabled && (lastDamageEvent.getDamager() instanceof CraftMyPet || lastDamageEvent.getDamager() instanceof CraftMyPetProjectile || lastDamageEvent.getDamager() instanceof EntityMyPetProjectile) && config.getBoolean("my-pet-enabled") && p.hasPermission("irestore.events.pvp.mypet")) {
                MyPet myPet = ((CraftMyPet) lastDamageEvent.getDamager()).getMyPet();
                killer = myPet.getOwner().getName() + "'s pet " + myPet.getPetName();
            }
            
            /*
             * Melee mobs
             */
            
            else if(lastDamageEvent.getDamager() instanceof PigZombie && (config.getBoolean("events.mobs.zombie_pigman") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.zombie_pigman")) {
            	killer = "A Zombie Pigman";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Zombie) {
            	Zombie z = (Zombie) lastDamageEvent.getDamager();
            	
            	if(lastDamageEvent.getDamager() instanceof ZombieVillager && (config.getBoolean("events.mobs.zombie_villager") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.zombie_villager")) {
            		if(z.isBaby()) {
            			killer = "A Baby Zombie Villager";
            		} else {
            			killer = "A Zombie Villager";
            		}
            	}
            	
            	else if(lastDamageEvent.getDamager() instanceof Husk && (config.getBoolean("events.mobs.husk") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.husk")) {
            		if(z.isBaby() ) {
            			killer = "A Baby Husk";
            		} else {
            			killer = "A Husk";
            		}
            	}
            	
            	else if(z.isBaby() && (config.getBoolean("events.mobs.baby_zombie") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.baby_zombie")) {
            		killer = "A Baby Zombie";
            	}
            	
            	else if((config.getBoolean("events.mobs.zombie") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.zombie")) {
            		killer = "A Zombie";
            	}
            	
            	else {
            		return;
            	}
            }
            
            else if(lastDamageEvent.getDamager() instanceof Guardian) {
            	
            	if(lastDamageEvent.getDamager() instanceof ElderGuardian && (config.getBoolean("events.mobs.elder_guardian") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.baby_zombie")) {
            		killer = "An Elder Guardian";
            	}
            	
            	else if((config.getBoolean("events.mobs.guardian") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.guardian")) {
            		killer = "A Guardian";
            	}
            	
            	else {
            		return;
            	}
            }
            
            else if(lastDamageEvent.getDamager() instanceof Endermite && (config.getBoolean("events.mobs.endermite") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.endermite")) {
            	killer = "An Endermite";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Blaze && (config.getBoolean("events.mobs.blaze") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.blaze")) {
            	killer = "A Blaze";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Giant && (config.getBoolean("events.mobs.giant") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.giant")) {
            	killer = "A Giant";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Rabbit) {
        		Rabbit r = (Rabbit) lastDamageEvent.getDamager();
            	
        		if(r.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY && (config.getBoolean("events.mobs.killer_rabbit") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.killer_rabbit")) {
        			killer = "The Killer Rabbit";
        		}
        		else {
        			return;
        		}
            }
            
            else if(lastDamageEvent.getDamager() instanceof Wolf) {
        		Wolf w = (Wolf) lastDamageEvent.getDamager();
            	
        		if(w.isAngry() && (config.getBoolean("events.mobs.angry_wolf") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.angry_wolf")) {
        			killer = "An Angry Wolf";
        		}
        		
        		else if(w.isTamed() && (config.getBoolean("events.pvp.tamed_wolf") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.tamed_wolf")) {
        			killer = w.getOwner().getName() + "'s Wolf";
        		}
        		
        		else {
        			return;
        		}
            }
            
            else if(lastDamageEvent.getDamager() instanceof Spider && (config.getBoolean("events.mobs.spider") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.spider")) {
            	killer = "A Spider";
            }
            
            else if(lastDamageEvent.getDamager() instanceof CaveSpider && (config.getBoolean("events.mobs.cave_spider") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.cave_spider")) {
            	killer = "A Cave Spider";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Creeper && (config.getBoolean("events.mobs.creeper") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.creeper")) {
            	killer = "A Creeper";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Enderman && (config.getBoolean("events.mobs.enderman") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.enderman")) {
            	killer = "An Enderman";
            }
            
            else if(lastDamageEvent.getDamager() instanceof IronGolem && (config.getBoolean("events.mobs.iron_golem") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.iron_golem")) {
            	killer = "An Iron Golem";
            }
            
            else if(lastDamageEvent.getDamager() instanceof MagmaCube && (config.getBoolean("events.mobs.magma_cube") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.magma_cube")) {
            	killer = "A Magma Cube";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Slime && (config.getBoolean("events.mobs.slime") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.slime")) {
            	killer = "A Slime";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Silverfish && (config.getBoolean("events.mobs.silverfish") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.silverfish")) {
            	killer = "A Silverfish";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Vindicator && (config.getBoolean("events.mobs.vindicator") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.vindicator")) {
            	killer = "A Vindicator";
            }
            
            else if(lastDamageEvent.getDamager() instanceof Vex && (config.getBoolean("events.mob.vex") || config.getBoolean("events.mobs.all")) && p.hasPermission("irestore.events.mobs.vex")) {
            	killer = "A Vex";
            }
            
            /*
             * TNT
             */
            
        	else if (lastDamageEvent.getDamager() instanceof TNTPrimed) {
        		if((config.getBoolean("events.pvp.tnt") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.tnt") && (p.getKiller() != null || lastDamageEvent.getDamager() instanceof Player)) {
        			killer = p.getKiller().getName();
        		}
        		
        		else if((config.getBoolean("events.other.tnt") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.tnt")) {
        			killer = "TNT";
        		}
        		
        		else {
        			return;
        		}
        	}
            
            /*
             * PvP
             */
            
            else if ((p.getKiller() != null || lastDamageEvent.getDamager() instanceof Player) && (config.getBoolean("events.pvp.melee") || config.getBoolean("events.pvp.all")) && p.hasPermission("irestore.events.pvp.melee")) {
            	killer = p.getKiller().getName();
            }
            
            /*
             * Enderpearls
             */
            
            else if ((pDamage.getCause() == DamageCause.FALL || lastDamageEvent.getDamager() instanceof EnderPearl) && (config.getBoolean("events.other.enderpearl") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.enderpearl")) {
            	killer = "Their own Enderpearl";
            }
            
            else if (pDamage.getCause() == DamageCause.FALLING_BLOCK && (config.getBoolean("events.other.anvil") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.anvil")) {
            	killer = "An anvil";
            }
            
            /*
             * Player was not killed by an enabled event.
             */
            
            else {
                return;
            }
        }
        
        /*
         * "Other" events.
         */
        
	    else if(!p.hasPermission("irestore.participate")) {
	    	return;
	    }
        
    	else if ((pDamage.getCause() == DamageCause.FIRE || pDamage.getCause() == DamageCause.FIRE_TICK) && (config.getBoolean("events.other.fire") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.fire")) {
    		killer = "Fire";
    	}
        
        else if (pDamage.getCause() == DamageCause.VOID && (config.getBoolean("events.other.void") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.void")) {
        	killer = "The Void";
        }
        
        else if (pDamage.getCause() == DamageCause.LAVA && (config.getBoolean("events.other.lava") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.lava")) {
        	killer = "Lava";
        }
        
        else if (pDamage.getCause() == DamageCause.CONTACT && (config.getBoolean("events.other.cactus") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.cactus")) {
        	killer = "A Cactus";
        }
        
        else if (pDamage.getCause() == DamageCause.DROWNING && (config.getBoolean("events.other.drowning") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.drowning")) {
        	killer = "Drowning";
        }
        
        else if (pDamage.getCause() == DamageCause.STARVATION && (config.getBoolean("events.other.starvation") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.starvation")) {
        	killer = "Their lack of food";
        }
        
        else if (pDamage.getCause() == DamageCause.SUFFOCATION && (config.getBoolean("events.other.suffocation") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.suffocation")) {
        	killer = "Their lack of air";
        }
        
        else if (pDamage.getCause() == DamageCause.FALL && (config.getBoolean("events.other.falling") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.falling")) {
        	killer = "Breaking their own legs";
        }
        
        else if (pDamage.getCause() == DamageCause.LIGHTNING && (config.getBoolean("events.other.lightning") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.lightning")) {
        	killer = "Lightning";
        }
        
        else if (pDamage.getCause() == DamageCause.SUICIDE && (config.getBoolean("events.other.suicide") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.suicide")) {
        	killer = "His/herself";
        }
        
        else if (pDamage.getCause() == DamageCause.MAGIC && (config.getBoolean("events.other.magic") || config.getBoolean("events.other.all")) && p.hasPermission("irestore.events.other.magic")) {
        	killer = "Magic";
        }
        
        /*
         * Player was not killed by an enabled event.
         */
        
        else {
            return;
        }
        
        /* 
         * Start figuring what the player will get to keep.
         */
        
        if(config.getBoolean("keep-inventory"))
        	if(p.hasPermission("irestore.keep.all") || p.hasPermission("irestore.keep.inventory"))
        		keepInventory(event);

        if(config.getBoolean("keep-xp"))
        	if(p.hasPermission("irestore.keep.all") || p.hasPermission("irestore.keep.xp"))
        		keepXP(event);

        if(config.getBoolean("vault.enabled"))
        	if(p.hasPermission("irestore.money.steal")  && killer != null)
        		moneySteal(event);

        if(config.getBoolean("death-message"))
        	sendDeathMessage(event, killer);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
    	saveItems(event);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
    	if(event.getPlayer().isDead()) {
    		saveItems(event);
    	}
	}

    @EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
    	if(event.getPlayer().isDead()) {
    		saveItems(event);
    	}
	}
    
    public void keepXP(PlayerDeathEvent event) {
        event.setKeepLevel(true);
        
        if (plugin.getConfig().getInt("xp-to-remove") < 100 && plugin.getConfig().getInt("xp-to-remove") > 0) {
            event.getEntity().setLevel((int) (event.getEntity().getLevel() * ((100.0 - plugin.getConfig().getInt("xp-to-remove")) / 100.0)));
        }
        
        event.setDroppedExp(0);
    }
    
    public void keepInventory(PlayerDeathEvent event) {
    	Player p = event.getEntity();
    	
        ItemStack[] content = p.getInventory().getContents();
        ItemStack[] content_armor = p.getInventory().getArmorContents();
        
        armor.put(p, content_armor);
        items.put(p, content);
        
        p.getInventory().clear();
        
        checkDropLists(event);
    }
    
    public void sendDeathMessage(PlayerDeathEvent event, String killer) {
    	event.setDeathMessage(ChatColor.YELLOW + "[iRestore] " + ChatColor.RED + event.getEntity().getName() + ChatColor.GREEN + " was killed by " + ChatColor.RED + killer + ChatColor.GREEN + "!");
    }
    
    public void checkDropLists(PlayerDeathEvent event) {
    	FileConfiguration config = plugin.getConfig();
    	
        if (config.getBoolean("percentage-drop.enabled") != true && config.getBoolean("whitelist.enabled") != true && config.getBoolean("blacklist.enabled") != true) {
        	event.getDrops().clear();
        	return;
        }
    	
        if (config.getBoolean("blacklist.enabled")) {
            dropBlacklist(event);
        }
        
        if (config.getBoolean("whitelist.enabled")) {
            dropWhitelist(event);
        }
        
        if (config.getBoolean("percentage-drop.enabled")) {
            dropPercentage(event);
        }
        
    }
    
    public void saveItems(PlayerEvent event) {
		FileConfiguration config = plugin.getConfig();
		Player p = event.getPlayer();
			
        if (items.containsKey(p)) {
            p.getInventory().clear();
            p.getInventory().setContents(items.get(p));
            items.remove(p);
            
            if (config.getBoolean("percentage-drop.enabled")) {
            	Utilities.getUtilities().dropPercentageRemove(p, itemsToBeRemoved);
            }
            
            if (config.getBoolean("blacklist.enabled")) {
            	Utilities.getUtilities().blacklistItems(p);
            }
            
            if (config.getBoolean("whitelist.enabled")) {
            	Utilities.getUtilities().whitelistItems(p);
            }
        }
        if (armor.containsKey(event.getPlayer()) && armor.size() != 0) {
            p.getInventory().setArmorContents(armor.get(p));
            armor.remove(p);
            
            if (config.getBoolean("percentage-drop.enabled")) {
            	Utilities.getUtilities().dropPercentageRemove(p, itemsToBeRemoved);
            }
            
            if (config.getBoolean("blacklist.enabled")) {
            	Utilities.getUtilities().blacklistItems(p);
            }
            
            if (config.getBoolean("whitelist.enabled")) {
            	Utilities.getUtilities().whitelistArmor(p);
            }
        }
    }

	@SuppressWarnings("deprecation")
	public void moneySteal(PlayerDeathEvent event) {
		FileConfiguration config = plugin.getConfig();
		
		Player p = event.getEntity();
		Player k = p.getKiller();
		
		if (!p.hasPermission("irestore.money.exempt") && iRestore.econ != null && k != null) {
			double r = iRestore.econ.getBalance(p.getName()) * (config.getInt("vault.money-to-steal") / 100.0);
			
            iRestore.econ.depositPlayer(k.getName(), r);
            iRestore.econ.withdrawPlayer(p.getName(), r);
            
			DecimalFormat dFormat = new DecimalFormat();
			String d = dFormat.format(r);
			
			k.sendMessage(ChatColor.YELLOW + "[iRestore] " + ChatColor.GREEN  + "You stole " + ChatColor.RED + d + " " + iRestore.econ.currencyNamePlural() + ChatColor.GREEN + " from " + ChatColor.RED + p.getName());
		}
	}

	public void dropBlacklist(PlayerDeathEvent event) {
		FileConfiguration config = plugin.getConfig();
		Player p = event.getEntity();
		
		for (String itemList : config.getStringList("blacklist.items")) {
			for (ItemStack itemStackList : event.getDrops()) {
				if (Material.valueOf(itemList) == itemStackList.getType()) {
					p.getLocation().getWorld().dropItem(p.getLocation(), itemStackList);
				}
			}
		}
		
		event.getDrops().clear();
	}

	public void dropWhitelist(PlayerDeathEvent event) {
		FileConfiguration config = plugin.getConfig();
		
		for (String itemList : config.getStringList("whitelist.items")) {
			Iterator<ItemStack> iterator = event.getDrops().iterator();
			
			while (iterator.hasNext()) {
				ItemStack itemStack = iterator.next();
				
				if (Material.valueOf(itemList) == itemStack.getType()) {
					iterator.remove();
				}
			}
		}
	}
	
	public void dropPercentage(PlayerDeathEvent event) {
		FileConfiguration config = plugin.getConfig();
		Iterator<ItemStack> iterator = event.getDrops().iterator();
		Double percentage = (config.getInt("percentage-drop.percentage") / 100.0);
		Integer i = 0;
		Integer dropModifier = (int) (percentage * event.getDrops().size());
		
		while (i < dropModifier && iterator.hasNext()) {
			ItemStack stackToRemove = iterator.next();
			
			itemsToBeRemoved.add(stackToRemove);
			
			iterator.remove();
			
			i++;
		}
	}
}
