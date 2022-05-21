package godswar.godswar.Ability.God;

import java.util.List;

import godswar.godswar.Utility.*;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Aprodite extends Ability {

    private final static String[] des= {
            "아프로디테는 미의 여신입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"매혹",
            "가까이 있는 사람들을 끌어올 수 있습니다.",
            "자신이 블록 위에 서 있고",
            "웅크리지 않아야 발동합니다."};

    public Aprodite(String playerName)
    {
        super(playerName, "아프로디테", 12, true, false, false, des);

        this.cool1=500;
        this.sta1=64;

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

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)) {
            if(!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
                Skill.Use(player, co, sta1, 0, cool1);
                try {
                    List<Player> list= GetPlayerList.getNearByNotTeamMembers(player, 20, 20, 20);

                    for(Player e:list) {
                        e.teleport(player);
                        e.sendMessage(ChatColor.YELLOW+"미의 여신에게 이끌려갑니다!");
                    }
                }catch(Exception e) {}

                player.sendMessage("미로 다른 사람들을 홀렸습니다.");
            }else {
                player.sendMessage(ChatColor.RED+"웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
            }
        }

    }

}

