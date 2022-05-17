package com.magical.Ability.Misc;

import com.magical.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Demigod extends Ability {
    private final static String[] des= {
            "데미갓",
            ChatColor.AQUA+"【패시브】 "+ChatColor.WHITE+"저항",
            "공격을 받으면 20% 확률로 5초간 세기 2의","임의의 버프가 적용됩니다."};
    private static final Random random = new Random();
    public Demigod(String playerName)
    {
        super(playerName,"데미갓", 1002, false, true, false ,des);
        this.rank = 4;
    }
    public void T_Passive(EntityDamageByEntityEvent e){
        Player entity = (Player) e.getEntity();

        int value = (int) (Math.random()*9)+1;
        if (value <= 2) {
            switch (random.nextInt(5)) {
                case 0:
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1, true));
                    break;
                case 1: case 2:
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1, true));
                    break;
                case 3: case 4:
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1, true));
                    break;
            }
        }
    }
}
