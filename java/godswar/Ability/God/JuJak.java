package com.magical.Ability.God;

import com.magical.Ability.Ability;
import com.magical.Timer.Skill.HermesFlying;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Timer;

public class JuJak extends Ability {
    private final static String[] des= {
            "주작은 4신수 중 불의 새입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"화염 속성",
            "불에 관한 데미지를 일절 받지 않으나, ",
            "물에 들어가면 데미지를 입습니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"날아오르라",
            "15초 간 비행할 수 있으며, 점프하면서 ",
            "비행하면 바로 날 수 있습니다.",
            "비행 중에는 낙하 데미지를 받지 않습니다."};

    public JuJak(String playerName)
    {
        super(playerName,"주작", 18, true, true, false, des);

        this.cool1=80;
        this.sta1=15;

        this.rank=4;
    }

    public void T_Passive(EntityDamageEvent event){
        Player player = (Player) event.getEntity();
        EntityDamageEvent.DamageCause dc = event.getCause();
        if (dc.equals(EntityDamageEvent.DamageCause.LAVA) ||
                dc.equals(EntityDamageEvent.DamageCause.FIRE) ||
                dc.equals(EntityDamageEvent.DamageCause.FIRE_TICK))
        {
            event.setCancelled(true);
            player.setFireTicks(0);
        }
        else if (dc.equals(EntityDamageEvent.DamageCause.DROWNING))
            event.setDamage((int)event.getDamage()<<1);
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
            player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT,  2);
            player.getWorld().spawnParticle(Particle.FLAME,  player.getLocation(), 2);
            Timer t = new Timer();
            t.schedule(new HermesFlying(player),12*1000,1000);
        }
    }

}
