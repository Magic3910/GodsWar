package godswar.godswar.Ability.Misc;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Theomachy.DB.GameData;
import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.utils.base.color.RGB;
import godswar.godswar.utils.base.math.LocationUtil;
import godswar.godswar.utils.base.math.geometry.Circle;
import godswar.godswar.utils.library.ParticleLib;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Predicate;

import static org.bukkit.Bukkit.getPlayer;


public class Chaos extends Ability {
    private final static String[] des= {
            "태초의 신 카오스",
            ChatColor.AQUA+"【고급】 "+ChatColor.WHITE+"칠흑",
            "철괴를 우클릭하면 5초간 짙은 암흑 속으로","주변의 생명체들을 모두 끌어옵니다."};
    public Chaos(String playerName)
    {
        super(playerName,"카오스", 1003, true, false, false ,des);
        this.cool2=80;
        this.sta2=20;
        this.rank = 4;
    }
    public static int c = 0;
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
                rightAction(player);
                c =10;
                break;
            }
        }
    }
    private static final RGB BLACK = RGB.of(1, 1, 1);
    private final Circle CIRCLE = Circle.of(5, 5 * 4);
    Location center;
    Circle pCircle;
    Circle sCircle;
    private Player player;
    public void onDurationStart() {
        center = player.getLocation();
        pCircle = CIRCLE.clone();
        sCircle = CIRCLE.clone();
    }
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
    public void onDurationProcess() {
        ParticleLib.SMOKE_LARGE.spawnParticle(center, 1.4, 1.4, 1.4, 35, 0.05);
        for (Location loc : pCircle.rotateAroundAxisX(-5).rotateAroundAxisZ(5).rotateAroundAxisY(3).toLocations(center)) {
            ParticleLib.REDSTONE.spawnParticle(loc, BLACK);
        }
        for (Location loc : sCircle.rotateAroundAxisX(5).rotateAroundAxisZ(-5).rotateAroundAxisY(-6).toLocations(center)) {
            ParticleLib.REDSTONE.spawnParticle(loc, BLACK);
        }
        for (Entity entity : LocationUtil.getNearbyEntities(Entity.class, center, 5, 5, predicate)) {
            if (entity instanceof Damageable) ((Damageable) entity).damage(1);
            entity.setVelocity(center.toVector().subtract(entity.getLocation().toVector()).multiply(0.7));
        }
        c = c-1;
        if (c > 0) {
            setTimeout(() -> onDurationProcess(), 500);
        }
    }
    private void rightAction(Player players)
    {

        player = players;
        if (CoolTimeChecker.Check(player, 2)&&PlayerInventory.ItemCheck(player, co, sta2))
        {
            Skill.Use(player, co, sta2, 2, cool2);
            World world = player.getWorld();
            Location location = player.getLocation();
            onDurationStart();onDurationProcess();
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
