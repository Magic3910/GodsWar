package godswar.godswar.Utility;

import godswar.godswar.GodsWar;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory
{
    public static boolean InHandItemCheck(Player player, Material m)
    {
        if (player.getItemInHand().getType().equals(m))
            return true;
        else
            return false;
    }

    public static boolean ItemCheck(Player player, Material material, int stack)
    {
        Inventory inventory = player.getInventory();
        if (inventory.contains(material, stack))
            return true;
        else
        {
            T_Message.LackItemError(player, material, stack);
            return false;
        }
    }

    public static void skyBlockBasicItemAdd(Player player)
    {
        Inventory inventory = player.getInventory();
        if (GodsWar.INVENTORY_CLEAR)
        {
            inventory.clear();
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
        }
        if (GodsWar.GIVE_ITEM)
        {
            inventory.addItem(new ItemStack(Material.CHEST,1));
            inventory.addItem(new ItemStack(Material.LAVA_BUCKET,1));
            inventory.addItem(new ItemStack(Material.LAVA_BUCKET,1));
            inventory.addItem(new ItemStack(Material.ICE,2));
            inventory.addItem(new ItemStack(Material.OAK_SAPLING,1));
            inventory.addItem(new ItemStack(Material.BONE_MEAL,1));
        }
    }
}