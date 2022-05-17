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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Bomber extends Ability
{

    private Location tntLocation;
    private final static String[] des= {
            "봄버는 폭탄을 다루는 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"기폭",
            "좌클릭으로 해당 위치에 보이지 않는 TNT를 설치하며" ,
            "우클릭으로 어디서든 폭발시킬 수 있습니다."};

    public Bomber(String playerName)
    {
        super(playerName,"봄버", 105, true, false, false, des);

        this.cool1=30;
        this.sta1=25;

        this.rank=3;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 1:
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
        Block block = player.getTargetBlock(null, 5);
        if (!block.getType().equals(Material.AIR))
        {
            Location location = block.getLocation();
            location.setY(location.getY()+1);
            this.tntLocation = location;
            player.sendMessage("해당 블럭에 폭탄이 설치되었습니다.");
        }
    }

    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            if (tntLocation != null)
            {
                Skill.Use(player, co, sta1, 0, cool1);
                World world = player.getWorld();
                world.createExplosion(tntLocation, 2.0f, true);
                tntLocation = null;
                player.sendMessage("TNT가 폭발했습니다!");

            }
            else
                player.sendMessage("TNT가 설치되지 않았습니다.");
        }
    }
}
