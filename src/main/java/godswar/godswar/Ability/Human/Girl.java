package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.*;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Girl extends Ability {

    private final static String[] des= {
            "안락소녀는 귀여움으로 상대를 아사시킵니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"가짜 연약함",
            "주변의 적을 자신의 앞으로 끌어옵니다.",
            "끌려 온 플레이어들의 배고픔 지수는 0이 됩니다."};

    public Girl(String playerName) {
        super(playerName, "안락소녀", 126, true, false, false, des);

        this.rank= 3;
        this.cool1=60;
        this.sta1=15;
    }

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

    private void leftAction(Player player) {
        if(CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)) {

            Skill.Use(player, co, sta1, 0, cool1);

            for(Player e: GetPlayerList.getNearByNotTeamMembers(player, 5, 5, 5)) {
                e.teleport(player);
                e.setFoodLevel(0);
                e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 200));
                e.sendMessage(ChatColor.GREEN+"안락소녀"+ChatColor.WHITE+"에게 이끌려 갑니다!");
            }
        }
    }

}