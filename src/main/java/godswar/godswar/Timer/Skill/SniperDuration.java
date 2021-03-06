package godswar.godswar.Timer.Skill;

import java.util.TimerTask;

import godswar.godswar.Ability.Human.Sniper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SniperDuration extends TimerTask {

    final Sniper sniper;
    final Player player;
    int count = 4;

    public SniperDuration(Player player, Sniper sniper)
    {
        this.sniper=sniper;
        this.player = player;
    }

    @Override
    public void run()
    {
        if (count > 0)
            player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.WHITE+count+"초 전");
        else if (count == 0)
        {
            player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.AQUA+"ON");
            sniper.sniping = true;
        }
        if (!player.isSneaking())
        {
            sniper.ready=false;
            sniper.sniping=false;
            player.sendMessage(ChatColor.RED+"[스나이핑 모드] "+ChatColor.RED+"OFF");
            this.cancel();
        }
        count--;
    }

}
