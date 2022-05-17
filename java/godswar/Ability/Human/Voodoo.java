package com.magical.Ability.Human;

import java.util.Timer;
import java.util.TimerTask;

import com.magical.Ability.Ability;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Voodoo extends Ability
{
    private final int coolTime0=180;
    private final int stack0=5;
    private String targetName=null;
    private Block postSign=null;
    private final static String[] des= {
            "부두술사는 팻말을 이용해 적을 공격하는 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"부두술",
            "팻말에 타격할 상대의 이름을 적으면 ",
            "그 아이디를 가진 플레이어는 팻말과 연결되며" ,
            "팻말을 두들겨 패면 대상 플레이어가 데미지를 입습니다." ,
            "설치후 7초동안 효과가 지속되며 7초 후에",
            "자동으로 팻말이 부숴집니다." ,
            "데미지는 무기의 영향을 받지 않습니다." ,
            "쿨타임은 팻말을 든 채 좌클릭하면 좀 더 ",
            "쉽게 확인 할 수 있습니다."};

    public Voodoo(String playerName)
    {
        super(playerName, "부두술사", 118, true, true, false, des);
        this.cool1=180;
        this.sta1=20;

        this.rank=3;
    }

    private boolean checkSign(Material m) {

        switch(m) {
            case ACACIA_SIGN: case ACACIA_WALL_SIGN: case BIRCH_SIGN: case BIRCH_WALL_SIGN:
            case CRIMSON_SIGN: case CRIMSON_WALL_SIGN: case DARK_OAK_SIGN: case DARK_OAK_WALL_SIGN:
            case JUNGLE_SIGN: case JUNGLE_WALL_SIGN: case OAK_SIGN: case OAK_WALL_SIGN:
            case SPRUCE_SIGN: case SPRUCE_WALL_SIGN: case WARPED_SIGN: case WARPED_WALL_SIGN:
                return true;
        }

        return false;
    }

    public void T_Passive(BlockPlaceEvent event)
    {
        Block blockId = event.getBlock();
        if (checkSign(blockId.getType()))
        {
            Player player = event.getPlayer();

            if (!(CoolTimeChecker.Check(player, 0)&& PlayerInventory.ItemCheck(player, co, stack0)))
                event.setCancelled(true);
        }
    }

    public void T_Check(PlayerInteractEvent event)
    {
        if (this.postSign != null)
        {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                Block block = event.getClickedBlock();
                if (checkSign(block.getType())&& this.postSign.getState().equals(block.getState()))
                {
                    Player player = Bukkit.getPlayer(targetName);
                    if (player == null){

                    }else
                        player.damage(1, event.getPlayer());

                }
            }
        }
        else if (checkSign(event.getPlayer().getItemInHand().getType()))
        {
            Action action = event.getAction();
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
            {
                Player player = event.getPlayer();
                if (CoolTimeChecker.Check(player, 0) && PlayerInventory.ItemCheck(player, co, stack0))
                    player.sendMessage("스킬을 사용 할 수 있습니다.");
            }
        }
    }

    public void T_Passive(SignChangeEvent event)
    {
        Player player = event.getPlayer();
        String targetName = event.getLine(0);
        int coolR = 60;
        Player target = Bukkit.getPlayer(targetName);
        if (target == null)
        {
            player.sendMessage(ChatColor.RED+targetName+ChatColor.WHITE+" 그런 플레이어는 없는데요...");
        }
        else {
            Skill.Use(player, co, stack0, 0, coolR);
            this.targetName=targetName;
            this.postSign=event.getBlock();
            player.sendMessage(ChatColor.RED+targetName+ChatColor.WHITE+" 를(을) 팻말과 연결시켰습니다.");
            player.sendMessage(ChatColor.RED+"쿨타임 : "+coolR);
            target.sendMessage(ChatColor.RED+"부두술사가 당신을 위협합니다.");
            Timer t = new Timer();
            t.schedule(new Duration(), 7000);
        }
    }


    private class Duration extends TimerTask
    {
        @Override
        public void run()
        {
            targetName=null;
            postSign.breakNaturally();
            postSign=null;
        }

    }
}