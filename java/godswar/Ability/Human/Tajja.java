package com.magical.Ability.Human;

import com.magical.Ability.Ability;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Tajja extends Ability {

    private final static String[] des= {
            "타짜는 손놀림이 빠른 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"밑장빼기",
            "능력 사용 시 인벤토리에 가장 먼저",
            "위치한 검이 소비됩니다.",
            "능력 사용 후 맨손으로 가격 시",
            "소비된 검의 데미지만큼",
            "데미지를 줄 수 있습니다.",
            "단, 10번 쓰면 검을 쓸 수 없습니다."
    };

    public Tajja(String playerName) {
        super(playerName, "타짜", 125, true, false, false, des);

        this.rank=4;
        this.cool1=60;
        this.sta1=10;
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

    private int sword=0;
    private int time=-1;

    private void leftAction(Player player) {
        if(CoolTimeChecker.Check(player, 0) && PlayerInventory.ItemCheck(player, co, sta1)) {
            if(sword==0) {
                for(ItemStack i:player.getInventory().getContents()) {
                    try {
                        switch(i.getType()) {
                            case WOODEN_SWORD:case GOLDEN_SWORD:
                                sword=4;
                                player.getInventory().removeItem(i);
                                break;
                            case STONE_SWORD:
                                sword=5;
                                player.getInventory().removeItem(i);
                                break;
                            case IRON_SWORD:
                                sword=6;
                                player.getInventory().removeItem(i);
                                break;
                            case DIAMOND_SWORD:
                                sword=7;
                                player.getInventory().removeItem(i);
                                break;
                            default:
                                break;
                        }
                    }catch(NullPointerException e) {}
                }
                if(sword!=0){
                    player.sendMessage("손은 눈보다 빠르다.");
                    Skill.Use(player, co, sta1, 0, cool1);
                    time=10;
                }else{
                    player.sendMessage("소비할 검이 인벤토리에 없습니다.");
                }

            }
        }
    }


    public void T_Passive(EntityDamageByEntityEvent event) {
        Player p=(Player) event.getDamager();
        if(p.getName().equals(this.playerName)) {
            if(p.getItemInHand().getType().equals(Material.AIR)) {
                if(sword!=0) {
                    switch(time) {
                        case 1:
                            event.setDamage(sword);
                            p.sendMessage("동작 그만, 밑장 빼기냐.");
                            sword=0;
                            time=-1;
                            break;
                        default:
                            event.setDamage(sword);
                            time--;
                            break;
                    }
                }
            }

        }
    }
}
