package godswar.godswar.Ability.Human;

import java.util.List;
import java.util.Random;

import godswar.godswar.GodsWar;
import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import godswar.godswar.Timer.Skill.ClockingTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Clocking extends Ability
{
    private List<Player> targetList;
    private final static String[] des= {
            "클로킹은 일정 시간 자신의 몸을 숨길 수 있는 능력입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"감추기",
            "자신의 모습을 잠시 감출 수 있습니다.",
            "감춘 상태에서 상대방을 공격하면",
            "다시 모습이 나타나게 되며,",
            "공격 당한 상대는 20% 확률로 사망합니다."};

    GodsWar main;

    public static int clockingTimer;
    public Clocking(String playerName, GodsWar theomachy)
    {
        super(playerName,"클로킹", 112, true, true, false, des);
        this.main=theomachy;
        this.cool1=60;
        this.sta1=25;

        this.rank=3;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 2:case 3:
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
            targetList = player.getWorld().getPlayers();
            for (Player e : targetList)
                e.hidePlayer(player);
            clockingTimer= Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new ClockingTimer(targetList, player),
                    0, 140);
            super.flag = true;
        }
    }

    public void T_Passive(EntityDamageByEntityEvent event)
    {
        if (flag)
        {
            Player player = (Player) event.getDamager();
            if (player.getName().equals(this.playerName))
            {
                targetList = player.getWorld().getPlayers();
                for (Player e : targetList)
                    e.showPlayer(player);
                Random random = new Random();
                if (random.nextInt(5)==0)
                {
                    Player target = (Player) event.getEntity();
                    event.setDamage(100);
                    target.sendMessage("알 수 없는 이유로 인해 즉사 하였습니다.");
                    player.sendMessage("상대가 즉사 하였습니다.");
                }
            }
            super.flag = false;
        }
    }
}
