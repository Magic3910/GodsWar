package godswar.godswar.Ability.God;

import godswar.godswar.Theomachy.Theomachy;
import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import godswar.godswar.Timer.Skill.HoreunTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Horeundal extends Ability {

    private Theomachy main;
    private final static String[] des= {
            "호른달은 시간과 공간의 신입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"시공의 기약",
            "위치 기억 후 10초 뒤 되돌아옵니다.",
            "되돌아 온 뒤에 잠시 투명해집니다."};

    public Horeundal(String playerName, Theomachy t)
    {
        super(playerName,"호른달", 17, true, false, false ,des);


        this.main=t;
        this.cool1=120;
        this.sta1=32;

        this.rank=4;
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


    public static int horeundalT;
    private void leftAction(Player player) {

        if(CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)){

            Skill.Use(player, co, sta1, 0, cool1);
            player.sendMessage("위치를 기억했습니다! 10초 뒤에 여기로 올 것입니다.");

            horeundalT=Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new HoreunTimer
                            (player, player.getLocation()), 140, 20);
        }

    }

}
