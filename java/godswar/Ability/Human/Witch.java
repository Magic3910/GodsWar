package com.magical.Ability.Human;

import java.util.List;
import java.util.Random;

import com.magical.Ability.Ability;
import com.magical.Utility.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Witch extends Ability
{
    final int coolTime0=60;
    final int stack0=1;
    private final static String[] des= {
            "마녀는 디버프를 사용하는 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"저주 Ⅰ",
            "주변의 적에게 각종 디버프를 10초 간 적용합니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"저주 Ⅱ",
            "자신을 공격한 상대는 7% 확률로",
            "5초 간 디버프에 걸리게 됩니다."};

    public Witch(String playerName)
    {
        super(playerName,"마녀", 116, true, false, false, des);

        this.cool1=60;
        this.sta1=15;

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
            }
        }
    }

    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, stack0))
        {
            List<Player> targetList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
            if (!targetList.isEmpty())
            {
                Skill.Use(player, co, stack0, 0, coolTime0);
                for (Player e : targetList)
                {

                    e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200,0));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200,0));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200,0));
                    e.sendMessage("마녀에 의해 저주에 걸렸습니다.");
                }
            }
            else
                player.sendMessage("능력을 사용 할 대상이 없습니다.");
        }
    }

    public void T_Passive(EntityDamageByEntityEvent event)
    {
        Player player = (Player)event.getEntity();
        if (player.getName().equals(playerName))
        {
            Random random = new Random();
            int rn = random.nextInt(14);
            if (rn == 0)
            {
                Player target = (Player) event.getDamager();
                target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100,0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100,0));
                target.sendMessage("마녀에 의해 저주가 걸렸습니다.");
            }
        }
    }
}