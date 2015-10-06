package net.lnfinity.HeroBattle.tools.displayers;

import net.lnfinity.HeroBattle.HeroBattle;
import net.lnfinity.HeroBattle.tools.PlayerTool;
import net.lnfinity.HeroBattle.utils.ItemCooldown;
import net.lnfinity.HeroBattle.utils.ToolsUtils;
import net.lnfinity.HeroBattle.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;


public class InkTool extends PlayerTool
{

	private final int COOLDOWN; // seconds
	private final int EFFECT_DURATION; // seconds
	private final double PROBABILITY_SENDER_HIT;

	private Random random = null;

	public InkTool(HeroBattle plugin, int cooldown, int duration, double probability)
	{
		super(plugin);
		COOLDOWN = cooldown;
		EFFECT_DURATION = duration;
		PROBABILITY_SENDER_HIT = probability;

		random = new Random();
	}

	@Override
	public String getToolID()
	{
		return "tool.ink";
	}

	@Override
	public String getName()
	{
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Jet d'encre";
	}

	@Override
	public List<String> getDescription()
	{
		return Utils.getToolDescription(ChatColor.GRAY + "Lanche un jet d'encre qui touche les joueurs alentours les aveuglant pendant " + ChatColor.GOLD + EFFECT_DURATION + " " + ChatColor.GRAY + "secondes. Attention, vous avez " + ChatColor.RED + (int) (PROBABILITY_SENDER_HIT * 100) + ChatColor.GRAY + "% " + " de chance de vous toucher ! Ne peut être utilisé que toutes les " + ChatColor.GOLD + COOLDOWN + " " + ChatColor.GRAY + "secondes.");
	}

	@Override
	public ItemStack getItem()
	{
		ItemStack item = new ItemStack(Material.INK_SACK, 1);
		ToolsUtils.resetTool(item);
		return item;
	}

	@Override
	public void onRightClick(Player player, ItemStack tool, PlayerInteractEvent event)
	{
		if (ToolsUtils.isToolAvailable(tool))
		{
			player.playSound(player.getLocation(), Sound.SPLASH, 1, 1);
			new ItemCooldown(p, player, this, COOLDOWN);

			player.getNearbyEntities(10, 10, 10).stream()
					.filter(e -> e instanceof Player)
					.forEach(e -> {
								Player pl = (Player) e;
								pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, EFFECT_DURATION * 20, 0));
								pl.playSound(pl.getLocation(), Sound.SPLASH, 1, 1);
							}
					);

			if (random.nextDouble() < PROBABILITY_SENDER_HIT)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, ((EFFECT_DURATION * 20) / 3) + random.nextInt(EFFECT_DURATION / 4), 0));
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Vous êtes trop fatigué pour réutiliser ça maintenant");
		}
	}
}
