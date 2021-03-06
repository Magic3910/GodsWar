package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Snow extends Ability {

    private final static String[] des= {
            "강력한 눈덩이를 구사하는 능력입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"눈싸움",
            "눈을 맞추면 70%의 확률로 ",
            "공격 지수만큼의 데미지를 줍니다.",
            "공격 지수가 죽을 때마다 최대 7까지 상승합니다.",
            "공격 지수는 능력의 막대 좌클릭으로 확인할 수 있습니다.",
            "눈덩이를 맞춰도 상대가 밀려나지 않습니다.",
            "게임 시작 시 눈덩이 1개를 받으며 눈덩이는",
            "던질 때마다 무한으로 지급됩니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"얼음 속성",
            "불에 의한 데미지를 2배로 받습니다."};


    public Snow(String playerName) {
        super(playerName, "사이코스노우", 124, true, true, false, des);

        this.rank=3;

        this.cool1=0;
        this.sta1=0;
    }

    private int attack=0;

    public void T_Check(PlayerInteractEvent event){
        Player player=event.getPlayer();
        if(PlayerInventory.InHandItemCheck(player, st)){
            switch(EventFilter.PlayerInteract(event)){
                case 0:case 1:
                    player.sendMessage("공격 지수 : "+attack);  break;
            }
        }
    }

    public void T_Passive(PlayerDeathEvent event) {
        if(attack<8){
            event.getEntity().sendMessage(ChatColor.RED+"공격 지수가 증가하고 있습니다!");
            attack++;
        }
    }

    public void T_PassiveSnow(EntityDamageByEntityEvent event){
        Player p=(Player)event.getEntity();
        event.setCancelled(true);
        p.damage(attack);
        p.getInventory().addItem(new ItemStack(Material.SNOWBALL));
    }

    public void T_Passive(EntityDamageEvent event){
        DamageCause dc=event.getCause();
        if(dc.equals(DamageCause.FIRE_TICK)||dc.equals(DamageCause.LAVA)||dc.equals(DamageCause.FIRE)){
            event.setDamage(event.getDamage()*2);
        }
    }

    public void conditionSet(){
        Bukkit.getPlayer(playerName).getInventory().addItem(new ItemStack(Material.SNOWBALL, 8));
    }

}
