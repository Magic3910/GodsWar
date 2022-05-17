package com.magical.Ability.Human;

import com.magical.Ability.Ability;
import com.magical.Theomachy.Theomachy;
import com.magical.Timer.Skill.MeguminTimer;
import com.magical.Utility.BlockFilter;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;


public class Megumin extends Ability {

    Theomachy main;
    private final static String[] des= {
            "이 능력은 메구밍!",
            ChatColor.YELLOW+"【일반】 "+ChatColor.WHITE+"폭렬 ♪",
            "게임 중 한 번만 영창 후 전방의",
            "블럭에 폭렬 마법을 날립니다.",
            "사용 후 즉시 쓰러집니다."};

    public Megumin(String playerName, Theomachy theomachy) {
        super(playerName, "메구밍", 127, true, false, false, des);

        this.main=theomachy;
        this.rank=4;

        this.sta1=32;
        this.cool1=0;
    }

    public static boolean cancel=false;

    public void T_Active(PlayerInteractEvent event){
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
    public static int meguminTimer;
    private void leftAction(Player player) {
        if(PlayerInventory.ItemCheck(player, co, sta1)) {
            if(!cancel) {
                Block block=player.getTargetBlock(null, 25);
                if (BlockFilter.AirToFar(player, block))
                {
                    Skill.Use(player, co, sta1, 0, cool1);
                    meguminTimer= Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new MeguminTimer(player, block),
                                0, 20);
                    cancel=true;
                }
            }
            else {
                player.sendMessage("더 이상 쓸 수 없습니다...");
            }
        }
    }

    public void conditionSet(){
        cancel=false;
    }

}
