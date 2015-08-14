package net.lnfinity.HeroBattle.utils;

import net.lnfinity.HeroBattle.HeroBattle;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

public class DamageTag {

	private final int damage;
	private final Location location;
	
	private BukkitTask task;
	
	public DamageTag(int damage, Location location) {
		this.damage = damage;
		this.location = location;
	}
	
	public void play() {
		final ArmorStand am = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		am.setVisible(false);
		am.setGravity(false);
		
		am.setCustomName((damage >= 0 ? ChatColor.RED + "" + ChatColor.BOLD + "+" : ChatColor.GREEN + "" + ChatColor.BOLD) + damage + " %");
		
		am.setCustomNameVisible(true);

		task = Bukkit.getScheduler().runTaskTimer(HeroBattle.getInstance(), new Runnable() {
			private int i = 0;
			@Override
			public void run() {
				am.teleport(am.getLocation().clone().add(0, 0.05, 0));
				i++;
				if(i > 15) {
					task.cancel();
					Bukkit.getScheduler().runTaskLater(HeroBattle.getInstance(), new Runnable() {
						@Override
						public void run() {
							am.remove();
						}
					}, 10L);
				}
			}
		}, 5L, 1L);
	}
}