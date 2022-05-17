package com.magical.Ability.Misc;

import com.magical.Ability.Ability;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import com.magical.utils.base.random.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Summoner extends Ability {
    public final static String[] des= {
        "소환사",
        ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"랜덤소환",
        "랜덤몬스터를 대상의 자리에",
        "소환해 적을 공격합니다.",
        ChatColor.RED+"【고급】 "+ChatColor.WHITE+"홍어소환",
        "목표로 지정해 둔 상대에",
        "강력한 홍어 1마리를 소환합니다.",
        "자신이 블록 위에 서 있고",
        "웅크리지 않아야 발동합니다.",
        "목표 지정: /x <대상>"
    };



    private static final java.util.Random random = new java.util.Random();
    private String abilitytarget;

    public Summoner(String playerName) {
        super(playerName, "소환사", 1009, true, false, false, des);

        this.rank=4;

        this.cool1=60;
        this.sta1=15;
        this.cool2=140;
        this.sta2=32;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0: case 1:
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
        if(PlayerInventory.ItemCheck(player, co, sta1)&&CoolTimeChecker.Check(player, 1)){
            if(abilitytarget!=null){
                Player target=Bukkit.getPlayer(abilitytarget);
                if(target==null){
                    player.sendMessage(abilitytarget+ChatColor.RED+" 님은 현재 서버에 없는 것 같습니다..");
                }else{
                    if(!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)){
                        Skill.Use(player, co, sta1, 1, cool1);
                        target.sendMessage(ChatColor.YELLOW+"홍어"+ChatColor.WHITE+"가 당신을 공격합니다!");
                            int r = random.nextInt(9);
                            if (r == 0) {
                                Bee bee=(Bee) target.getWorld().spawnEntity(target.getLocation(), EntityType.BEE);
                                bee.setTarget(target);
                                bee.setAnger(3);
                            }
                            if (r == 1) {
                                Husk husk =(Husk) target.getWorld().spawnEntity(target.getLocation(), EntityType.HUSK);
                                husk.setTarget(target);
                            }
                            if (r == 2) {
                                Blaze blaze =(Blaze) target.getWorld().spawnEntity(target.getLocation(), EntityType.BLAZE);
                            }
                            if (r == 3) {
                                MagmaCube magmaCube =(MagmaCube) target.getWorld().spawnEntity(target.getLocation(), EntityType.MAGMA_CUBE);
                                magmaCube.setTarget(target);
                                magmaCube.setSize(3);
                            }
                            if (r == 4) {
                                Guardian guardian =(Guardian) target.getWorld().spawnEntity(target.getLocation(), EntityType.GUARDIAN);
                                guardian.setTarget(target);
                            }
                            if (r == 5) {
                                Pillager pillager=(Pillager) target.getWorld().spawnEntity(target.getLocation(), EntityType.PILLAGER);
                                pillager.setTarget(target);
                            }
                            if (r == 6) {
                                WitherSkeleton witherSkeleton =(WitherSkeleton) target.getWorld().spawnEntity(target.getLocation(), EntityType.WITHER_SKELETON);
                                witherSkeleton.setTarget(target);
                            }
                            if (r == 7) {
                                PiglinBrute piglinBrute =(PiglinBrute) target.getWorld().spawnEntity(target.getLocation(), EntityType.PIGLIN_BRUTE);
                                piglinBrute.setTarget(target);
                            }
                            if (r == 8) {
                                Shulker shulker =(Shulker) target.getWorld().spawnEntity(target.getLocation(), EntityType.SHULKER);
                                shulker.setTarget(target);
                            }
                    }else{
                        player.sendMessage(ChatColor.RED+"웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
                    }
                }
            }else{
                player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
            }
        }
    }
    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, 2) && PlayerInventory.ItemCheck(player, co, sta2)) {
            if (!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
                if (abilitytarget != null) {

                    Player target = Bukkit.getPlayer(abilitytarget);

                    if (target == null) {
                        player.sendMessage(abilitytarget + ChatColor.RED + " 님은 현재 서버에 없는 것 같습니다..");
                    } else {
                        Skill.Use(player, co, sta2, 2, cool2);
                        target.sendMessage(ChatColor.RED+"강력한 홍어의 기운이 느껴집니다..");
                        Ravager ravager=(Ravager) target.getWorld().spawnEntity(target.getLocation(), EntityType.RAVAGER);
                        ravager.setTarget(target);
                        ravager.setCustomName("&c홍어");
                        ravager.setCustomNameVisible(true);
                    }
                } else {
                    player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
                }
            } else {
                player.sendMessage(ChatColor.RED + "웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
            }

        }
    }
    public void targetSet(CommandSender sender, String targetName) {
        if (!playerName.equals(targetName)) {
            this.abilitytarget = targetName;
            sender.sendMessage("타겟을 등록했습니다.   " + ChatColor.GREEN + targetName);
        } else
            sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");
    }
}