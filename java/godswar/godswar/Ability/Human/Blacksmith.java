package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Blacksmith extends Ability
{
    private final static String[] des= {
            "대장장이는 다양한 광물을 만들어",
            "낼 수 있는 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"철 연성",
            "코블스톤을 소비하여 철괴 10개를",
            "얻을 수 있습니다.",
            ChatColor.RED+"【고급】 "+ChatColor.WHITE+"금강석 연성",
            "철괴를 소비하여 다이아 5개를",
            "얻을 수 있습니다."};

    public Blacksmith(String playerName)
    {
        super(playerName,"대장장이", 113, true, false, false, des);

        this.cool1=300;
        this.cool2=600;
        this.sta1=70;
        this.sta2=20;

        this.rank=4;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                leftAction(player);
                break;
                case 2:case 3:
                rightAction(player);
                break;
            }
        }
    }

    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 1, cool1);
            World world = player.getWorld();
            world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.IRON_INGOT, 10));
        }
    }

    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, Material.IRON_INGOT, sta2))
        {
            Skill.Use(player, Material.IRON_INGOT, sta2, 2, cool2);
            World world = player.getWorld();
            world.dropItem(player.getLocation().add(0,2,0), new ItemStack(Material.DIAMOND, 5));
        }
    }
}
