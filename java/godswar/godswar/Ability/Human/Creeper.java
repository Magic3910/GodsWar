package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class Creeper extends Ability
{
    private boolean plasma = false;
    private final static String[] des= {
            "크리퍼는 몬스터형 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"펑!",
            "능력의 막대를 통해 능력을 사용하면" ,
            "크리퍼와 같은 폭발력의 폭발을 일으킵니다." ,
            "번개를 맞은 적이 있다면 폭발력이 두 배로 증가합니다.",
            "번개 카운팅은 사망 시 초기화됩니다."};

    public Creeper(String playerName)
    {
        super(playerName,"크리퍼", 106, true, false, false, des);

        this.cool1=60;
        this.sta1=20;

        this.rank=3;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                leftAction(player);
                break;
            }
        }
    }

    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 0, cool1);
            World world = player.getWorld();
            Location location = player.getLocation();
            float power = plasma ? 3.0f : 6.0f;
            player.setHealth(0);
            world.createExplosion(location, power);
        }
    }

    public void T_Passive(EntityDamageEvent event)
    {
        if (event.getCause() == DamageCause.LIGHTNING)
        {
            this.plasma = true;
            (event.getEntity()).sendMessage(ChatColor.AQUA+"플라즈마 크리퍼 모드 활성화!");
        }
    }

    public void T_Passive(PlayerDeathEvent event)
    {
        this.plasma=false;
    }
}
