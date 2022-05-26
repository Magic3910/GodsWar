package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Theomachy.DB.GameData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.Bukkit.getPlayer;

public class Ira extends Ability {
    private final static String[] des= {
            "이라",
            ChatColor.WHITE+"【패시브】 자폭",
            "다른 생명체에게 3번 공격을 당할 때마다",
            "상대방의 위치에 폭발을 일으킵니다.","자기 자신도 폭발 데미지를 입습니다."};
    public static int casti;
    public Ira(String playerName)
    {
        super(playerName,"이라", 1005, false, true, false ,des);
        this.rank=2;
    }
    public void T_Passive(EntityDamageByEntityEvent event){
        Ability ability= GameData.PlayerAbility.get(event.getEntity().getName());
        if (ability.abilityName == "이라") {
            casti++;
            if(casti >=3) {
                casti = 0;
                Player player = (Player) event.getEntity();
                Entity damager = event.getDamager();
                if (damager instanceof Projectile) {
                    if (((Projectile) damager).getShooter() instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) ((Projectile) damager).getShooter();
                        Location location = entity.getLocation();
                        player.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 1.3f, false, false);
                        if (entity.getVelocity().getY() > 0) {
                            entity.setVelocity(entity.getVelocity().setY(0));
                        }
                    }
                } else {
                    Location location = damager.getLocation();
                    player.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 1.3f, false, false);
                    if (damager.getVelocity().getY() > 0) {
                        damager.setVelocity(damager.getVelocity().setY(0));
                    }
                }
            }
        }
    }
}
