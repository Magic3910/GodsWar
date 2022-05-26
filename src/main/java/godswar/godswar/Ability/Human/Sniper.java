package godswar.godswar.Ability.Human;

import java.util.Timer;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import godswar.godswar.Timer.Skill.SniperDuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Sniper extends Ability
{
    public boolean ready = false;
    public boolean sniping = false;
    private final static String[] des= {
            "저격수는 빠른 화살을 이용해",
            "상대방을 공격하는 능력입니다.",
            "게임 시작시 활 1개 화살 10개를 지급합니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"스나이핑",
            "활을 들고 앉은 채(shift) 좌클릭하면",
            "4초 뒤 스나이핑 모드가 활성화됩니다." ,
            "스나이핑 모드일 때는 쏜 화살이",
            "타겟방향으로 보이지 않는 속도로 날아가며",
            "맞은 적은 약 100~200의 데미지를 입습니다.",
            "앉은 채로 화살을 발사해야 능력이 발동됩니다."};

    public Sniper(String playerName)
    {
        super(playerName, "저격수", 117, true, false, false, des);
        this.cool1=50;
        this.sta1=5;

        this.rank=3;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BOW))
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
        if (player.isSneaking() && !ready)
        {
            ready=true;
            Timer t = new Timer();
            t.schedule(new SniperDuration(player, this),0,1000);
        }
    }

    @Override
    public void T_Passive(ProjectileLaunchEvent event, Player player)
    {
        if (this.sniping && (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)))
        {
            Entity entity = event.getEntity();
            if (entity instanceof Arrow)
            {
                Skill.Use(player, co, sta1, 0, cool1);
                player.getInventory().removeItem(new ItemStack(Material.ARROW));
                entity.remove();

                Arrow arrow = player.launchProjectile(Arrow.class);
                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(100));
            }
        }
    }

    @Override
    public void conditionSet()
    {
        Player player = Bukkit.getPlayer(playerName);
        player.getInventory().addItem(new ItemStack(Material.BOW, 1));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
    }

}
