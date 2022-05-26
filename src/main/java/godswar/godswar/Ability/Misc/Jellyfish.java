package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Theomachy.DB.GameData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Jellyfish extends Ability {
    private final static String[] des= {
            "해파리",
            ChatColor.AQUA+"【패시브】 "+ChatColor.WHITE+"촉수",
            "플레이어를 타격하면 대상을 0.1초간 움직이지 못하게 합니다."
    };
    public static Map<UUID, Integer> stop = new HashMap<UUID, Integer>();
    public Jellyfish(String playerName)
    {
        super(playerName,"해파리", 1006, false, true, false ,des);
    }
    public void T_Passive(EntityDamageByEntityEvent event){
        Ability ability= GameData.PlayerAbility.get(event.getDamager().getName());
        Player pl = (Player) event.getEntity();
        if (ability.abilityName == "해파리") {
            stop.put(event.getDamager().getUniqueId(), 1);
            setTimeout(() -> stop.put(event.getDamager().getUniqueId(), 0), 100);
        }else {event.setDamage(event.getDamage());}
    }
    @EventHandler(priority= EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (stop.containsKey(player.getUniqueId())){
            if (stop.get(player.getUniqueId()) == 1) {
                Location from = e.getFrom();
                if (from.getZ() != e.getTo().getZ() && from.getX() != e.getTo().getX()) {
                    player.teleport(e.getFrom());
                }
            }
        }
    }
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
