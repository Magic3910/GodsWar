package godswar.godswar.Ability.God;

import java.util.Timer;
import java.util.TimerTask;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

public class Hephaestus extends Ability
{
    private final static String[] des= {
            "헤파이토스는 대장장이의 신입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"화염 속성",
            "불에 관한 데미지를 일절 받지 않으나,",
            "물에 들어가면 데미지를 입습니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"용암 생성",
            "블럭을 클릭하면 용암을 놓습니다.",
            "놓은 용암은 2초 뒤 사라집니다.",};

    public Hephaestus(String playerName)
    {
        super(playerName,"헤파이토스", 8, true, true, false, des);

        this.cool1=10;
        this.sta1=1;

        this.rank=2;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            if(EventFilter.PlayerInteract(event)==1)
            {
                leftAction(player);
            }
        }
    }

    private void leftAction(Player player)
    {
        Location location = player.getTargetBlock(null, 5).getLocation();
        location.setY(location.getY()+1);
        Block block = location.getBlock();
        if (block.getType().equals(Material.AIR))
        {
            if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
            {
                Skill.Use(player, co, sta1, 0, cool1);
                block.setType(Material.LAVA);
                Timer t = new Timer();
                t.schedule(new LavaTimer(block), 2000);
            }
        }
    }

    public void T_Passive(EntityDamageEvent event)
    {
        Player player = (Player) event.getEntity();
        DamageCause dc = event.getCause();
        if (dc.equals(DamageCause.LAVA) ||
                dc.equals(DamageCause.FIRE) ||
                dc.equals(DamageCause.FIRE_TICK))
        {
            event.setCancelled(true);
            player.setFireTicks(0);
        }
        else if (dc.equals(DamageCause.DROWNING))
            event.setDamage((int)event.getDamage()<<1);
    }

    public void conditionSet()
    {
        Player player = Bukkit.getPlayer(playerName);
        player.setMaximumAir(0);
        player.setRemainingAir(0);
    }


    public void conditionReSet()
    {
        Player player = Bukkit.getPlayer(playerName);
        player.setMaximumAir(300);
        player.setRemainingAir(300);
    }




    static class LavaTimer extends TimerTask
    {
        Block block;

        LavaTimer(Block block)
        {
            this.block=block;
        }

        public void run()
        {
            block.setType(Material.AIR);
        }
    }

}
