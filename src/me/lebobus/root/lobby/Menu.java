package me.lebobus.root.lobby;

import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("unused")
public class Menu implements Listener {

    private static Inventory inv;
/*
	public static void sendToServer(Player player, String servername){
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(byteOutput);
        try {
            output.writeUTF("Connect");
            output.writeUTF(servername);
            player.sendPluginMessage(Main.getPlugin(), "BungeeCord", byteOutput.toByteArray());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
    public Menu(Plugin p) {
            inv = Bukkit.getServer().createInventory(null, 9, ChatColor.AQUA + "Servers");
        
    	    ItemStack kitpvp = new ItemStack(Material.DIAMOND_SWORD);
    	    ItemStack freebuild = new ItemStack(Material.GRASS);
    	    ItemStack minigames = new ItemStack(Material.BOW);
    	    
            ItemMeta kitpvpmeta = kitpvp.getItemMeta();
            ItemMeta freebuildmeta = freebuild.getItemMeta();
            ItemMeta minigamesmeta = minigames.getItemMeta();
            
            kitpvpmeta.setDisplayName(ChatColor.GOLD + "KitPvP");
            freebuildmeta.setDisplayName(ChatColor.YELLOW + "Freebuild");
            minigamesmeta.setDisplayName(ChatColor.RED + "Minigames");
            
            kitpvpmeta.setLore(Arrays.asList(ChatColor.GREEN + "Click to connect"));
            freebuildmeta.setLore(Arrays.asList(ChatColor.GREEN + "Click to connect"));
            minigamesmeta.setLore(Arrays.asList(ChatColor.GREEN + "Click to connect"));
            
            kitpvp.setItemMeta(kitpvpmeta);
            freebuild.setItemMeta(freebuildmeta);
            minigames.setItemMeta(minigamesmeta);
            
            inv.setItem(2, kitpvp);
            inv.setItem(4, freebuild);
            inv.setItem(6, minigames);
            
            return;
    }
    
 
    public static void show(Player p) {
        p.openInventory(inv);
    }
    
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
    	if(!(Bukkit.getServerName().equalsIgnoreCase("Lobby"))) return;
    	e.setCancelled(true);
    }
    
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
    	if(!(Bukkit.getServerName().equalsIgnoreCase("Lobby"))) return;
    	e.setCancelled(true);
    }
    
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	
    	Player p = (Player)e.getPlayer();
    
    	if(!(Bukkit.getServerName().equalsIgnoreCase("Lobby"))) return;
    	
    	if(p.hasPermission("rank.vip")) {
    		p.setAllowFlight(true);
    	}
    	
    	ItemStack servers = new ItemStack(Material.COMPASS);
	    ItemStack pp = new ItemStack(Material.BLAZE_POWDER);
	    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
	    
	    ItemMeta serversmeta = servers.getItemMeta();
        ItemMeta ppmeta = pp.getItemMeta();
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        skullmeta.setOwner("Boblus");
        
        serversmeta.setDisplayName(ChatColor.AQUA + "Servers");
        ppmeta.setDisplayName(ChatColor.AQUA + "Particles");
        skullmeta.setDisplayName(ChatColor.AQUA + "Staff");
        
        servers.setItemMeta(serversmeta);
        pp.setItemMeta(ppmeta);
        skull.setItemMeta(skullmeta);
    	
    	if(!(p.getInventory().contains(Material.COMPASS)) || (!(p.getInventory().contains(Material.BLAZE_POWDER)) || (!(p.getInventory().contains(Material.SKULL_ITEM))))) {
    	p.getInventory().setItem(0, servers);
    	p.getInventory().setItem(4, pp);
    	p.getInventory().setItem(8, skull);
    	
    	}
    	
    }
    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	
    	Player p = (Player)e.getWhoClicked(); 
    	
    	if(!(Bukkit.getServerName().equalsIgnoreCase("Lobby"))) return;
    	
            if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "KitPvP")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    sendToServer(p, "kitpvp");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Freebuild")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    sendToServer(p, "freebuild");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Minigames")) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    sendToServer(p, "minigames");
            }
    }
    */
}
