package com.magical.Ability.Misc;

import com.magical.Ability.Ability;
import com.magical.Theomachy.DB.GameData;
import com.magical.Utility.CoolTimeChecker;
import com.magical.Utility.EventFilter;
import com.magical.Utility.PlayerInventory;
import com.magical.Utility.Skill;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class DiceGod extends Ability {
    private final static String[] des= {
            "다이스 갓",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"주사위",
            "재생 / 신속 / 저항 / 시듦 / 구속 / 나약함 효과",
            "중 하나를 7초간 받습니다.",
            ChatColor.WHITE+"【패시브】 회피",
            "공격을 받았을 때 1/6 확률로 대미지를 받는 대신",
            "대미지만큼 체력을 회복합니다."};

    public DiceGod(String playerName)
    {
        super(playerName,"다이스갓", 1004, true, true, false ,des);
        this.rank=3;
        this.sta1=7;
        this.cool1=30;
    }
    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                    break;
                case 2:case 3:
                    leftAction(player);
                    break;
            }
        }
    }
    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 1, cool1);
            int i = random.nextInt(5);
            i++;
            switch (i) {
                case 1:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7, 1, true));
                    break;
                case 2:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 7, 1, true));
                    break;
                case 3:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7, 1, true));
                    break;
                case 4:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 7, 1, true));
                    break;
                case 5:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 7, 1, true));
                    break;
                case 6:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 7, 1, true));
                    break;
            }
        }
    }
    public void T_Passive(EntityDamageByEntityEvent event){
        Ability ability= GameData.PlayerAbility.get(event.getEntity().getName());
        Player pl = (Player) event.getEntity();
        if (ability.abilityName == "다이스갓") {
            int i = random.nextInt(5);
            i++;
            int h;
            switch (i) {
                case 1: case 2: case 3: case 4: case 5:
                    event.setDamage(event.getDamage());
                    break;
                case 6:
                    if (pl.getHealth() + event.getDamage() >= pl.getMaxHealth()){
                        h = (int) pl.getMaxHealth();
                        pl.setHealth(h);
                    } else {
                        pl.setHealth(pl.getHealth() + event.getDamage());
                    }
                    break;
            }
        }else {event.setDamage(event.getDamage());}
    }

    private static final Random random = new Random();
}
