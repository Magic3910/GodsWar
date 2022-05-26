package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.utils.base.math.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flora extends Ability {
    private final static String[] des= {
            "꽃과 풍요의 여신 플로라",
            ChatColor.AQUA+"【패시브】 "+ ChatColor.WHITE+"축복",
            "주변에 있는 모든 플레이어를 재생시키거나 신속 효과를 줍니다.",
            ChatColor.AQUA+"【일반】 "+ ChatColor.WHITE+"축복 변경",
            "효과를 뒤바꿉니다."};
    int num = 0;
    public Flora(String playerName)
    {
        super(playerName,"플로라", 1008, true, true, false ,des);
        this.rank=3;
        this.sta1=5;
        this.cool1=7;
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

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, 1) && PlayerInventory.ItemCheck(player, co, sta1)) {
            Skill.Use(player, co, sta1, 1, cool1);
            if(num == 0) {
                for (Player players : LocationUtil.getEntitiesInCircle(Player.class, player.getLocation(), 6)) {
                    if (!players.isDead()) {
                        final double maxHealth = players.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        if (players.getHealth() < maxHealth) {
                            final EntityRegainHealthEvent event = new EntityRegainHealthEvent(players, .3, EntityRegainHealthEvent.RegainReason.CUSTOM);
                            Bukkit.getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                if (players.getHealth() + event.getAmount() >= players.getMaxHealth()) {
                                    players.setHealth(players.getMaxHealth());
                                } else {
                                    players.setHealth((players.getHealth() + event.getAmount()));
                                }
                            }
                        }
                    }
                }
            }
            if(num == 1) {
                for (Player players : LocationUtil.getEntitiesInCircle(Player.class, player.getLocation(), 6)) {
                    if (!players.isDead()) {
                        players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 7, 0, true));
                    }
                }
            }
        }
    }

}
