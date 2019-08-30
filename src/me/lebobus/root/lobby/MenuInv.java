package me.lebobus.root.lobby;

import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class MenuInv implements Listener {

	/*
	public void sendStaff(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m---------------------------------------------------"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bShuultz &7: &cAdmin&7, &5Streamer"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBoblus &7: &cAdmin&7, &4Dev"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMoutronis &7: &6Mod"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYakkil &7: &6Mod"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAzaeps &7: &6Mod"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m---------------------------------------------------"));
	    return;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event){
	    Player p = event.getPlayer();
	    
	   if(!(Bukkit.getServerName().equalsIgnoreCase("Lobby"))) return;
	 
	    if((p.getItemInHand().getType().equals(Material.COMPASS)) && event.getAction() == Action.RIGHT_CLICK_AIR){
	    	event.setCancelled(true);
	    	Menu.show(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.COMPASS)) && event.getAction() == Action.LEFT_CLICK_AIR){
	    	event.setCancelled(true);
	    	Menu.show(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.COMPASS)) && event.getAction() == Action.LEFT_CLICK_BLOCK){
	    	event.setCancelled(true);
	    	Menu.show(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.COMPASS)) && event.getAction() == Action.RIGHT_CLICK_BLOCK){
	    	event.setCancelled(true);
	    	Menu.show(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.BLAZE_POWDER)) && event.getAction() == Action.RIGHT_CLICK_AIR){
	    	if(!(event.getPlayer().hasPermission("rank.vip"))) {
	    		event.setCancelled(true);
	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must be &bVIP &7in order to have &bParticles&7."));
	    		return;
	    	}
	    	event.setCancelled(true);
	    	p.performCommand("pp");
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.BLAZE_POWDER)) && event.getAction() == Action.LEFT_CLICK_AIR){
	    	if(!(event.getPlayer().hasPermission("rank.vip"))) {
	    		event.setCancelled(true);
	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must be &bVIP &7in order to have &bParticles&7."));
	    		return;
	    	}
	    	event.setCancelled(true);
	    	p.performCommand("pp");
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.BLAZE_POWDER)) && event.getAction() == Action.LEFT_CLICK_BLOCK){
	    	if(!(event.getPlayer().hasPermission("rank.vip"))) {
	    		event.setCancelled(true);
	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must be &bVIP &7in order to have &bParticles&7."));
	    		return;
	    	}
	    	event.setCancelled(true);
	    	p.performCommand("pp");
	    	return;
	    }
	    if (event.getHand().equals(EquipmentSlot.HAND)) {
	    if((p.getItemInHand().getType().equals(Material.BLAZE_POWDER)) && event.getAction() == Action.RIGHT_CLICK_BLOCK){
	    	if(!(event.getPlayer().hasPermission("rank.vip"))) {
	    		event.setCancelled(true);
	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must be &bVIP &7in order to have &bParticles&7."));
	    		return;
	    	}
	    	event.setCancelled(true);
	    	p.performCommand("pp");
	    	return;
	      }
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.SKULL_ITEM)) && event.getAction() == Action.RIGHT_CLICK_AIR){
	    	event.setCancelled(true);
	    	sendStaff(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.SKULL_ITEM)) && event.getAction() == Action.LEFT_CLICK_AIR){
	    	event.setCancelled(true);
	    	sendStaff(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.SKULL_ITEM)) && event.getAction() == Action.LEFT_CLICK_BLOCK){
	    	event.setCancelled(true);
	    	sendStaff(p);
	    	return;
	    }
	    
	    if((p.getItemInHand().getType().equals(Material.SKULL_ITEM)) && event.getAction() == Action.RIGHT_CLICK_BLOCK){
	    	event.setCancelled(true);
	    	sendStaff(p);
	    	return;
	    }
	    
	}
	*/
}
