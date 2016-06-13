package com.mistri.plugin.cow;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CowOfDoom extends JavaPlugin implements Listener {
	String prefix = " §cCow §7> §f";
	Logger l = Bukkit.getLogger();

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) {
			if (e.getClickedBlock().getType() == Material.IRON_PLATE) {
				Player p = e.getPlayer();
				p.sendMessage("§eCow joined the game");

				Cow c = (Cow) p.getWorld().spawn(
						new Location(p.getWorld(), 0.5, 76, -6.5), Cow.class);

				c.setCustomName("§cCow");
				c.setCustomNameVisible(true);
				c.setMaxHealth(500);
				c.setHealth(500);

				p.teleport(new Location(p.getWorld(), 0.5, 76, 0.5, 180, 0));
				p.sendMessage(prefix + "You will die today! I am the cow of --");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100, 100);
				p.playSound(p.getLocation(), Sound.EXPLODE, 100, 100);
				p.sendMessage(prefix + "§4MOOOOOOOOOOOOOOOOOOOOOM");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				p.sendMessage(prefix + "Oh wait... I mean...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100, 100);
				p.playSound(p.getLocation(), Sound.EXPLODE, 100, 100);
				p.sendMessage(prefix + "§4DOOOOOOOOOOOOOOOOOOOOOM");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				p.sendMessage(prefix
						+ "Pretty scary, right? Anyways, I CHALLENGE YOU TO A DUEL!!! Type §c/yep §fif you accept, or §c/nope §fif you want to decline.");
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (sender instanceof Player) {
			if (commandLabel.equalsIgnoreCase("yep")) {				
				for (Entity c : p.getWorld().getEntities()) {
					if (c instanceof Item){
						c.remove();
					}
				}

				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "summon Cow 0.5 107 -6.5 {Riding:{id:\"Zombie\",IsVillager:0,IsBaby:1,Invulnerable:1,Slient:1,ActiveEffects:[{Id:14,Amplifier:0,Duration:199980,ShowParticles:0b}]},Attributes:[{Name:generic.maxHealth,Base:500}]}");
				p.teleport(new Location(p.getWorld(), 0.5, 107, 0.5, 180, 0));
				p.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
				p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
				p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
				p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
				p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

			} else if (commandLabel.equalsIgnoreCase("nope")) {
				p.sendMessage(prefix + "§cThen... you die. >:D");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100, 100);
				p.playSound(p.getLocation(), Sound.EXPLODE, 100, 100);
				p.setHealth(0.0D);
				p.sendMessage("§eCow left the game");
				for (Entity e : p.getWorld().getEntities()) {
					if (e instanceof Cow) {
						((Cow) e).setHealth(0.0D);
					}
				}
			}
		}
		return true;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(
				new Location(e.getPlayer().getWorld(), 0.5, 65, 0.5));
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);

		Player p = event.getEntity();
		Bukkit.broadcastMessage(p.getName() + " was murdered by the brutal Cow");

		for (Entity e : p.getWorld().getEntities()) {
			if (e instanceof Cow) {
				((Cow) e).setHealth(0.0D);
			} else if (e instanceof Zombie) {
				((Zombie) e).setHealth(0.0D);
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.COW) {
			Player p = event.getEntity().getKiller();
			if (p instanceof Player) {
				for (Entity e : p.getWorld().getEntities()) {
					if (e instanceof Cow) {
						((Cow) e).setHealth(0.0D);
					} else if (e instanceof Zombie) {
						((Zombie) e).setHealth(0.0D);
					}
				}
				p.sendMessage("§7 > §4YOU HAVE KILLED THE ALMIGHTY §cCOW§4! §7He will now haunt your soul for the rest of eternity. Have a nice day! :)");
				for (int i = 0; i < 100; i++) {
					p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 100,
							100);
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 100, 100);
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST2, 100,
							100);
				}
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				p.teleport(new Location(p.getWorld(), 0.5, 65, 0.5));
			}
		}
	}

}