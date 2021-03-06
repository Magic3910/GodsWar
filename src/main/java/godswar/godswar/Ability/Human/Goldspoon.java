package godswar.godswar.Ability.Human;

import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Goldspoon extends Ability {

    private final static String[] des= {
            "금수저를 물고 태어난 능력입니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"상속",
            "리스폰될 때마다 금레깅스와 흉갑을를 받습니다."};

    public Goldspoon(String playerName) {
        super(playerName, "금수저", 122, false, true, false, des);

        this.rank=2;
    }

    public void T_Passive(PlayerRespawnEvent event){
        Player p=event.getPlayer();
        p.getInventory().addItem(new ItemStack(Material.GOLDEN_LEGGINGS));
        p.getInventory().addItem(new ItemStack(Material.GOLDEN_CHESTPLATE));
    }

}
