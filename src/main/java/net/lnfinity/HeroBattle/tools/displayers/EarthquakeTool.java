package net.lnfinity.HeroBattle.tools.displayers;

import net.lnfinity.HeroBattle.HeroBattle;
import net.lnfinity.HeroBattle.tasks.displayers.EarthquakeTask;
import net.lnfinity.HeroBattle.tools.PlayerTool;
import net.lnfinity.HeroBattle.utils.ItemCooldown;
import net.lnfinity.HeroBattle.utils.ToolsUtils;
import net.lnfinity.HeroBattle.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
public class EarthquakeTool extends PlayerTool
{

	private final int COOLDOWN;
	private final int MIN_DAMAGES;
	private final int MAX_DAMAGES;

	public EarthquakeTool(HeroBattle plugin, int cooldown, int min, int max)
	{
		super(plugin);
		COOLDOWN = cooldown;
		MIN_DAMAGES = min;
		MAX_DAMAGES = max;
	}

	@Override
	public String getToolID()
	{
		return "tool.earthquake";
	}

	@Override
	public String getName()
	{
		return ChatColor.BLACK + "" + ChatColor.BOLD + "Tremblement de terre";
	}

	@Override
	public List<String> getDescription()
	{
		return Utils.getToolDescription(ChatColor.GRAY + "Vous fait tomber au sol provoquant ainsi un séisme qui occasionne aux joueurs alentours " + ChatColor.RED + MIN_DAMAGES + " " + ChatColor.GRAY + "à " + ChatColor.RED + MAX_DAMAGES + " " + ChatColor.GRAY + "dégâts. Ne peut être utilisé que toutes les " + ChatColor.GOLD + COOLDOWN + " " + ChatColor.GRAY + "secondes.");
	}

	@Override
	public ItemStack getItem()
	{
		ItemStack item = new ItemStack(Material.FLINT, 1);
		ToolsUtils.resetTool(item);

		return item;
	}

	@Override
	public void onRightClick(Player player, ItemStack tool, PlayerInteractEvent event)
	{
		if (ToolsUtils.isToolAvailable(tool))
		{
			new ItemCooldown(p, player, this, COOLDOWN);
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)
			{
				player.setVelocity(player.getVelocity().setY(-1));
				player.setFallDistance(10);

				p.getGamePlayer(player).addTask(new EarthquakeTask(p, player, MIN_DAMAGES, MAX_DAMAGES));

			}
			else
			{
				player.sendMessage(ChatColor.RED + "Vous ne chutez pas d'assez haut, vous échouez !");
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Vous êtes trop fatigué pour réutiliser ça maintenant");
		}

	}
}
