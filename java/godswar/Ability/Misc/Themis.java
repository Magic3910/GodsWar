package com.magical.Ability.Misc;

import com.magical.Ability.Ability;
import com.magical.Theomachy.DB.GameData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.*;

public class Themis extends Ability {
    public static Map<UUID, Integer> killsMap = new HashMap<UUID, Integer>();
    private final static String[] des= {
            "정의의 여신 테미스",
            ChatColor.AQUA+"【패시브】 "+ChatColor.WHITE+"징벌",
            "다른 플레이어를 살해한 플레이어에게 죄 스택을 1씩 부여하며","죄 스택이 쌓인 플레이어를 공격할 때 ","§c스택 §7x §c10%§f만큼의 추가 대미지를 줍니다."};

    public Themis(String playerName)
    {
        super(playerName,"테미스", 1001, false, true, false ,des);
        this.rank=3;
    }
    public void T_Passive(EntityDamageByEntityEvent event){
        Ability ability= GameData.PlayerAbility.get(event.getDamager().getName());
        if (ability.abilityName == "테미스") {
            double addDamage = (event.getDamage()*(0.1 * killsMap.get(event.getEntity().getUniqueId())));
            event.setDamage(event.getDamage()+addDamage);
        }else {event.setDamage(event.getDamage());}
    }

        public static void addKills(Player player) {
            int ki = killsMap.get(player.getUniqueId());
            ki = ki + 1;
            killsMap.put(player.getUniqueId(),ki);
        }
        public static void onSilentEnd(Player player) {
            killsMap.put(player.getUniqueId(),0);
        }
}
