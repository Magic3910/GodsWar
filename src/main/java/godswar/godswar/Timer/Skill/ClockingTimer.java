package godswar.godswar.Timer.Skill;

import java.util.List;

import godswar.godswar.Ability.Human.Clocking;
import godswar.godswar.Theomachy.DB.GameData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClockingTimer implements Runnable
{
    List<Player> targetList;
    Player player;

    public ClockingTimer(List<Player> targetList, Player player)
    {
        this.targetList=targetList;
        this.player=player;
    }

    public void run()
    {
        try{
            if (GameData.PlayerAbility.get(player.getName()).flag)
            {
                player.sendMessage("은신 시간이 종료되었습니다.");
                GameData.PlayerAbility.get(player.getName()).flag = false;
            }
            for (Player e : targetList)
                e.showPlayer(player);
        }
        catch (Exception e)
        {
            Bukkit.broadcastMessage(e.getLocalizedMessage()+"");
        }
        Bukkit.getScheduler().cancelTask(Clocking.clockingTimer);
    }
}
