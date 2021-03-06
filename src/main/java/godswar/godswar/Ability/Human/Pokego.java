package godswar.godswar.Ability.Human;

import java.util.Random;

import godswar.godswar.Manager.CommandModule.AbilitySet;
import godswar.godswar.Theomachy.DB.AbilityData;
import godswar.godswar.Theomachy.Theomachy;
import godswar.godswar.Utility.ReturnAbilityName;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerMoveEvent;

public class Pokego extends Ability {

    Theomachy main;
    private final static String[] des= {
            "열심히 걸어다니면 능력을 잡을 수 있습니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"넌 내꺼야!",
            "1000걸음 걸으면 무작위로 능력을 얻을 수 있습니다.",
            "이는 블랙리스트를 무시합니다."};

    public Pokego(String playerName, Theomachy theomachy) {
        super(playerName, "포켓몬고", 128, false, true, false, des);

        this.main=theomachy;
        this.rank=3;
    }

    private int coolWalk=0;

    public void T_Passive(PlayerMoveEvent event) {

        if(coolWalk==1000) {

            Random r=new Random();

            int rn1=r.nextInt(2);
            event.getPlayer().sendMessage(ChatColor.YELLOW+" ★ 경  "+ChatColor.WHITE+"만 보 걷기에 성공했습니다!  "+ChatColor.YELLOW+" 축 ★");
            if(rn1==0) {
                int ar=r.nextInt(AbilityData.GOD_ABILITY_NUMBER);
                AbilitySet.abiltiyAssignment(ar+1, playerName, event.getPlayer(), main);
                event.getPlayer().sendMessage(ChatColor.AQUA+ ReturnAbilityName.name(ar)+ChatColor.WHITE+"!! 너로 정했다!!");
            }else {
                int ar=r.nextInt(AbilityData.HUMAN_ABILITY_NUMBER);
                AbilitySet.abiltiyAssignment(ar+101, playerName, event.getPlayer(), main);
                event.getPlayer().sendMessage(ChatColor.AQUA+ ReturnAbilityName.name(ar)+ChatColor.WHITE+"!! 너로 정했다!!");
            }
            event.getPlayer().sendMessage("능력이 할당되었습니다. /t help로 능력을 확인해보세요.");
        }else {
            coolWalk++;
        }

    }

    public void conditionSet(){
        this.coolWalk=0;
    }

}
