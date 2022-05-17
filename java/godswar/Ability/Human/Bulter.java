package com.magical.Ability.Human;

import com.magical.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Bulter extends Ability {

    private final static String[] des= {
            "집사는 무엇이든 안정시킬 수 있습니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"진정",
            "모든 폭발을 억제합니다."};

    public Bulter(String playerName) {
        super(playerName, "집사", 120, false, true, false, des);

        this.rank=4;
    }

    public void T_Passive(EntityExplodeEvent event){
        event.setCancelled(true);
        if(!event.getEntity().getType().equals(EntityType.FIREBALL))
            Bukkit.broadcastMessage(ChatColor.GREEN+"집사에 의해 폭발이 진정되었습니다.");
    }

}
