package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Theomachy.DB.GameData;
import godswar.godswar.utils.base.math.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.function.Predicate;

import static org.bukkit.Bukkit.getPlayer;

public class Magician extends Ability {
    private final static String[] des= {
            "마술사",
            ChatColor.AQUA+"【패시브】 "+ChatColor.WHITE+"환각",
            "활을 쐈을 때, 화살이 맞은 위치에서 5칸 범위 내에 있는 생명체들에게",
            "최대체력의 30% 만큼의 대미지를 추가로 입힙니다."};

    public Magician(String playerName)
    {
        super(playerName,"마술사", 1007, false, true, false ,des);
        this.rank=3;
    }
    public static Player player;
    private final Predicate<Entity> predicate = new Predicate<Entity>() {
        @Override
        public boolean test(Entity entity) {
            if (entity.getUniqueId().equals(player.getUniqueId())) return false;
            if (entity instanceof Player) {
                if(GameData.PlayerTeam.get(player.getName()) == GameData.PlayerTeam.get(entity.getName())) return false;
            }
            return true;
        }
    };
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Ability ability= GameData.PlayerAbility.get(e.getEntity().getShooter());
            if (ability.abilityName == "마술사") {
                    final Location center = e.getEntity().getLocation();
                    for (Entity entity : LocationUtil.getNearbyEntities(Entity.class, center, 5, 5, predicate)) {
                        if (entity instanceof Damageable) ((Damageable) entity).damage(((Arrow) e.getEntity()).getDamage()+(((Arrow) e.getEntity()).getDamage()*0.3));
                    }
                }
            }
    }
}
