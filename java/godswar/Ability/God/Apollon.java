package com.magical.Ability.God;

import com.magical.Ability.Ability;
import com.magical.Timer.Skill.ApollonPlayerScorching;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Timer;

public class Apollon extends Ability
{

    private final static String[] des= {
            "아폴론은 태양의 신입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"햇볕",
            "밤을 낮으로 바꿉니다.",
            ChatColor.RED+"【고급】 "+ChatColor.WHITE+"뙤약볕",
            "밤을 낮으로 바꾸고 온갖 물을 증발시키며",
            "다른 사람을 태웁니다.",
            "화염 속성의 능력자, 그늘, 물속에 있는",
            "플레이어는 피해를 입지 않습니다."};

    public Apollon(String playerName)
    {
        super(playerName, "아폴론", 5, true, false, false, des);

        this.cool1=90;
        this.cool2=180;
        this.sta1=1;
        this.sta2=25;
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
            world.setTime(6000);
            Bukkit.broadcastMessage(ChatColor.YELLOW+"태양의 신이 해를 띄웠습니다.");
        }
    }

    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, co, sta2))
        {
            Skill.Use(player, co, sta2, 2, cool2);
            World world = player.getWorld();
            world.setTime(6000);
            world.setStorm(false);
            Timer t = new Timer();
            t.schedule(new ApollonPlayerScorching(player, 15), 5000,2000);
        }
    }
}
