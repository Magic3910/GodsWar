package godswar.godswar.Ability.Human;

import java.util.List;
import java.util.Random;

import godswar.godswar.Utility.*;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blinder extends Ability
{
    private final static String[] des= {
            "블라인더는 상대방의 시야를",
            "가리는 능력입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"블라인딩 Ⅰ",
            "자신을 공격한 상대는 일정 확률로",
            "시야가 가려집니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"블라인딩 Ⅱ",
            "주변의 적의 시야를 가립니다."};

    public Blinder(String playerName)
    {
        super(playerName,"블라인더", 110, true, true, false, des);

        this.cool1=30;
        this.sta1=10;

        this.rank=3;
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
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            List<Player> targetList = GetPlayerList.getNearByNotTeamMembers(player, 5, 5, 5);
            if (!targetList.isEmpty())
            {
                Skill.Use(player, co, sta1, 0, cool1);
                player.sendMessage("주변의 적의 시야를 가립니다.");
                for (Player e : targetList)
                {
                    e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 0), true);
                    e.sendMessage("블라인더에 의해 시야가 어두워집니다.");
                }
            }
            else
                player.sendMessage("사용 가능한 대상이 없습니다.");
        }
    }

    public void T_Passive(EntityDamageByEntityEvent event)
    {
        Player player = (Player) event.getEntity();
        if (player.getName().equals(this.playerName))
        {
            Random random = new Random();
            if (random.nextInt(10) == 0)
            {
                Player target = (Player) event.getDamager();
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0), true);
                target.sendMessage("블라인더에 의해 시야가 어두워집니다.");
            }
        }
    }
}
