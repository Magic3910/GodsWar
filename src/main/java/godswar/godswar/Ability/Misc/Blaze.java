package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.player.PlayerInteractEvent;

public class Blaze extends Ability {
    private final static String[] des= {
            "블레이즈",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"화염구",
            "바라보는 방향으로 작은 화염구를 3회 발사합니다. "};
    public Blaze(String playerName)
    {
        super(playerName,"블레이즈", 1010, true, true, false ,des);
        this.cool1=80;
        this.sta1=20;
        this.rank = 2;
    }
    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                break;
                case 2:case 3:
                leftAction(player);
                break;
            }
        }
    }
    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 1, cool1);
            player.launchProjectile(SmallFireball.class);
            player.launchProjectile(SmallFireball.class);
            player.launchProjectile(SmallFireball.class);
        }
    }
}
