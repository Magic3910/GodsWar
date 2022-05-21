package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Midoriya extends Ability {

    public final static String[] des= {
            "미도리야는 UA에 재학 중입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"원 포 올",
            "능력 사용 후 상대를 가격하면",
            "원 포 올을 쓸 수 있습니다.",
            "원 포 올을 쓰고 난 뒤에는",
            "각종 디버프에 시달립니다."};

    public Midoriya(String playerName) {
        super(playerName, "미도리야", 121, true, false, false, des);

        this.rank=4;

        this.cool1=350;
        this.sta1=64;
    }

    private boolean Ready=false;

    public void T_Active(PlayerInteractEvent event){
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

    private void leftAction(Player player) {
        if(CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)&&!Ready){
            Ready=true;
            player.sendMessage(ChatColor.YELLOW+"원"+ChatColor.GREEN+" 포 "+ChatColor.AQUA+"올"+
                    ChatColor.WHITE+"이 준비되었습니다!");
        }
    }

    public void T_Passive(EntityDamageByEntityEvent event){
        Player player=(Player)event.getDamager();
        Player d=(Player)event.getEntity();

        if(player.getItemInHand().getType().equals(Material.AIR) && player.getName().equals(this.playerName)){
            if(CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1)) {
                if(Ready) {
                    player.sendMessage(ChatColor.YELLOW+"원"+ChatColor.GREEN+" 포 "+ChatColor.AQUA+"올"
                            +ChatColor.WHITE+"이 가동되었습니다!");
                    d.damage(200);
                    player.getWorld().strikeLightningEffect(d.getLocation());

                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));

                    Skill.Use(player, co, sta1, 0, cool1);

                    Ready=false;
                }else {
                    player.sendMessage("아직 원 포 올의 준비가 되어있지 않습니다.");
                }
            }
        }

    }

}
