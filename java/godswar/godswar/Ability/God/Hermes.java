package godswar.godswar.Ability.God;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import godswar.godswar.Timer.Skill.HermesFlying;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Hermes extends Ability
{
    private final static String[] des= {
            "헤르메스는 여행자의 신입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"민첩함",
            "이동 속도가 빠릅니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"비행",
            "10초 간 비행할 수 있으며,",
            "점프하면서 비행하면 바로 날 수 있습니다.",
            "비행 중에는 낙하 데미지를 받지 않습니다."};

    public Hermes(String playerName)
    {
        super(playerName,"헤르메스", 10, true, true, true, des);

        this.cool1=60;
        this.sta1=10;

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
            }
        }
    }

    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 0, cool1);
            player.setAllowFlight(true);
            player.setFlying(true);
            Timer t = new Timer();
            t.schedule(new HermesFlying(player),7000,1000);
        }
    }

    public void buff()
    {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null)
        {
            Timer t = new Timer();
            t.schedule(new buff(player), 1000);
        }
    }

    private class buff extends TimerTask
    {
        final Player player;

        buff(Player player)
        {
            this.player = player;
        }
        public void run()
        {
            player.addPotionEffects((Collection<PotionEffect>) new PotionEffect(PotionEffectType.SPEED, 6000, 1));
        }
    }
}
