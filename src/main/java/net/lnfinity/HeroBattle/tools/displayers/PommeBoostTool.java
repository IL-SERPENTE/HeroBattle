package net.lnfinity.HeroBattle.tools.displayers;

import net.lnfinity.HeroBattle.HeroBattle;
import net.lnfinity.HeroBattle.game.HeroBattlePlayer;
import net.lnfinity.HeroBattle.tools.PlayerTool;
import net.lnfinity.HeroBattle.utils.ItemCooldown;
import net.lnfinity.HeroBattle.utils.ToolsUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

/*
 * This file is part of HeroBattle.
 *
 * HeroBattle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HeroBattle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HeroBattle.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PommeBoostTool extends PlayerTool
{

	private final Integer COOLDOWN = 26;


	public PommeBoostTool(HeroBattle plugin)
	{
		super(plugin);
	}

	@Override
	public String getToolID()
	{
		return "tool.pommeboost";
	}

	@Override
	public String getName()
	{
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "POMME BOOST";
	}

	@Override
	public List<String> getDescription()
	{
		return Collections.singletonList(
				ChatColor.GRAY + "PLUS de vitesse, MOINS de dégâts, PLUS de POMMES !"
		);
	}

	@Override
	public ItemStack getItem()
	{
		ItemStack boost = new ItemStack(Material.GOLDEN_APPLE);
		ToolsUtils.resetTool(boost);

		return boost;
	}

	@Override
	public void onRightClick(Player player, ItemStack tool, PlayerInteractEvent event)
	{
		if (!ToolsUtils.isToolAvailable(tool))
		{
			player.sendMessage(ChatColor.RED + "Plus de POMMES :c");
			return;
		}

		HeroBattlePlayer gPlayer = HeroBattle.get().getGamePlayer(player);
		if (gPlayer == null || gPlayer.isSpectator()) return;


		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2, true, false));
		gPlayer.addRemainingReducedIncomingDamages(10);


		new ItemCooldown(HeroBattle.get(), player, this, COOLDOWN);
	}
}
