package godswar.godswar.Ability.Human;

import godswar.godswar.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Anorexia extends Ability {

    private final static String[] des= {
            "거식증은 신경성 식욕부진증이라고도 합니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"단식",
            "배고픔이 절반으로 유지됩니다."
    };

    public Anorexia(String playerName) {
        super(playerName, "거식증", 119, false, true, false, des);

        this.rank=2;

    }

    public void conditionSet(){
        Bukkit.getPlayer(playerName).setFoodLevel(10);
    }

    public void T_Passive(FoodLevelChangeEvent event) {
        event.setFoodLevel(10);
    }

}
