package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.EventFilter;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Galbi extends Ability {

    private final static String[] des= {
            "무한으로 즐길 수 있습니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"무한으로 즐겨요",
            "익힌 돼지고기 3개를 얻을 수 있습니다.",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.WHITE+"포식",
            "항상 배고프지 않고, 체력 회복이 빠릅니다."};

    public Galbi(String playerName)
    {
        super(playerName,"명륜진사", 136, true, true, false, des);

        this.cool1=20;
        this.sta1=10;

        this.rank=2;
    }

    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                Action(player);
                break;
            }
        }
    }

    private void Action(Player player)
    {
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, sta1))
        {
            Skill.Use(player, co, sta1, 0,cool1);
            Inventory inventory = player.getInventory();
            inventory.addItem(new ItemStack(Material.COOKED_PORKCHOP, 3));
        }
    }

    public void T_Passive(FoodLevelChangeEvent event)
    {
        ((Player)event.getEntity()).setFoodLevel(20);
        event.setCancelled(true);
    }

    public void T_Passive(EntityRegainHealthEvent event)
    {
        event.setAmount(((int)event.getAmount())<<2);
    }

}
