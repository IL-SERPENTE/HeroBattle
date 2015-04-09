package net.lnfinity.HeroBattle.tools;

import java.util.List;

import net.lnfinity.HeroBattle.HeroBattle;
import net.lnfinity.HeroBattle.utils.ItemCooldown;
import net.lnfinity.HeroBattle.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.samagames.utils.GlowEffect;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class SpeedTool extends PlayerTool {

	private final int COOLDOWN; // seconds
	private final int EFFECT_DURATION;

	public SpeedTool(HeroBattle plugin, int cooldown, int duration) {
		super(plugin);
		COOLDOWN = cooldown;
		EFFECT_DURATION = duration;
	}

	@Override
	public String getToolID() {
		return "tool.speed";
	}

	@Override
	public String getName() {
		return ChatColor.BLUE + "" + ChatColor.BOLD + "Boost de vitesse";
	}

	@Override
	public List<String> getDescription() {
		return Utils.getToolDescription(ChatColor.GRAY + "Vous ajoute l'effet de potion vitesse " + ChatColor.GOLD + "1 " + ChatColor.GRAY + "pendant " + ChatColor.GOLD + EFFECT_DURATION + " " + ChatColor.GRAY + "secondes. Ne peut être utilisé que toutes les " + ChatColor.GOLD + COOLDOWN + " " + ChatColor.GRAY + "secondes.");
	}

	@Override
	public ItemStack getItem() {
		ItemStack item = new ItemStack(Material.SUGAR, 1);
		GlowEffect.addGlow(item);

		return item;
	}

	@Override
	public void onRightClick(Player player, ItemStack tool, PlayerInteractEvent event) {
		if(tool.containsEnchantment(GlowEffect.getGlow())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, EFFECT_DURATION * 20, 1));
			new ItemCooldown(p, player, this, COOLDOWN);
		}
		else {
			player.sendMessage(ChatColor.RED + "Vous êtes trop fatigué pour réutiliser ça maintenant");
		}
	}

	@Override
	public void onLeftClick(Player player, ItemStack tool, PlayerInteractEvent event) {

	}
}