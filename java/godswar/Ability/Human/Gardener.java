package com.magical.Ability.Human;

import com.magical.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Gardener extends Ability {

    private final static String[] des= {
            "돌과 꽃으로 기지 앞마당을 꾸며보세요.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"소재 획득",
            "나무를 캐면 돌 1개, 꽃 한 송이를 얻습니다.",
            "얻은 돌과 꽃으로 기지의 앞마당을",
            "아름답게 꾸밉시다! 우리 강산 푸르게 푸르게"};

    public Gardener(String playerName) {
        super(playerName, "정원사", 135, false, true, false, des);

        this.rank=2;

    }

    public void T_Passive(BlockBreakEvent event)
    {
        Material m=event.getBlock().getType();
        if(m.equals(Material.ACACIA_LOG) || m.equals(Material.OAK_LOG) || m.equals(Material.DARK_OAK_LOG) ||
           m.equals(Material.BIRCH_LOG) || m.equals(Material.JUNGLE_LOG) || m.equals(Material.SPRUCE_LOG)){
            event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.POPPY, 1));
            event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE, 1));
        }
    }

    public void conditionSet(){
        Bukkit.getPlayer(playerName).getInventory().addItem(new ItemStack(Material.OAK_SAPLING, 5));
        Bukkit.getPlayer(playerName).getInventory().addItem(new ItemStack(Material.BONE_MEAL, 10));
    }

}
