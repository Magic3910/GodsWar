package com.magical.Ability.Human;

import com.magical.Ability.Ability;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Archer extends Ability
{
    private final static String[] des= {
            "활을 아주 잘 다루는 궁수입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"능숙함",
            "활 공격 데미지가 1.3배로 상승합니다.",
            ChatColor.AQUA+"【일반/고급】 "+ChatColor.WHITE+"화살/활 생성",
            "일반능력으로 화살을,",
            "고급 능력으로 활을 만듭니다."};

    public Archer(String playerName)
    {
        super(playerName,"아처", 101, true, true, false, des);

        this.cool1=20;
        this.cool2=60;
        this.sta1=7;
        this.sta2=15;

        this.rank=2;
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
            Location location = player.getLocation();
            world.dropItem(location, new ItemStack(Material.ARROW, 1));
        }
    }

    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, co, sta2))
        {
            Skill.Use(player, co, sta2, 2, cool2);
            World world = player.getWorld();
            Location location = player.getLocation();
            world.dropItem(location, new ItemStack(Material.BOW, 1));
        }
    }

    public void T_Passive(EntityDamageByEntityEvent event)
    {
        double damage = event.getDamage();
        event.setDamage((damage*1.3));
    }
}
