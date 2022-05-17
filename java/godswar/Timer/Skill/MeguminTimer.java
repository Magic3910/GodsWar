package com.magical.Timer.Skill;

import com.magical.Ability.Human.Megumin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MeguminTimer implements Runnable
{
    final Player player;
    final Block block;
    private int count = 3;

    public MeguminTimer(Player player, Block block)
    {
        this.player=player;
        this.block=block;
        player.sendMessage("영창을 시작합니다!!");
    }

    public void run()
    {
        if (count == 0)
        {
            player.getWorld().createExplosion(block.getLocation(), 6.0f);
            player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 32));
            player.setHealth(0);
            player.sendMessage("폭렬 마법의 효과로 쓰러졌습니다!");

            Megumin.cancel=true;
            Bukkit.getScheduler().cancelTask(Megumin.meguminTimer);

        }
        else
            player.sendMessage("영창 완료까지 "+ChatColor.AQUA+count+ChatColor.WHITE+"초 남았습니다.");
        count--;
    }
}
