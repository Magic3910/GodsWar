package com.magical.Ability.God;

import java.util.Random;

import com.magical.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Ares extends Ability
{

    private final static String[] des= {
            "아레스는 전쟁의 신입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"잔혹함",
            "모든 공격 데미지가 1.5배 상승합니다." ,
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"민첩함",
            "10% 확률로 공격을 회피합니다"};

    public Ares(String playerName)
    {
        super(playerName,"아레스", 7, false, true, false, des);

        this.rank=3;

    }

    public void T_Passive(EntityDamageByEntityEvent event)
    {
        Player player = (Player) event.getEntity();
        if (!player.getName().equals(playerName)) //공격
            event.setDamage(event.getDamage()*1.5);
        else											//피격
        {
            Random random = new Random();
            if (random.nextInt(10) == 0) 	//1/2 확률
            {
                event.setCancelled(true);
                player.sendMessage("회피했습니다!");
            }
        }
    }
}

