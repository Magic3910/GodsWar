package godswar.godswar.Utility;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GambManager {

    public static void Gamb(Player player) {
        Inventory inv = player.getInventory();
        if (inv.contains(Material.COBBLESTONE, 32)){
            Random r=new Random();
            player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 32));
            int rn = (r.nextInt(100));
            if(rn<=4){
                player.sendMessage(ChatColor.AQUA+"다이아몬드 3개;;");
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 3));
            }
            else if(rn== 5 || rn <= 19){
                player.sendMessage(ChatColor.GOLD+"나무 3개 ㅋㅋㅋㅋ");
                player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 3));
            }
            else if(rn==20 || rn<=34){
                player.sendMessage(ChatColor.RED+"꽝ㅋ ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
            }
            else if(rn==35 || rn<=79){
                player.sendMessage ("철괴 3개ㅋ ㅋ");
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 3));
            }
            else if(rn==80||rn<=98){
                player.sendMessage ("평범하네 철괴 4개");
                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 4));
            }
            else if(rn==99){
                player.sendMessage(ChatColor.YELLOW+"??????????");
                player.sendMessage(ChatColor.AQUA+"다이아몬드 10개");
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 10));
            }
        }else{
            player.sendMessage(ChatColor.RED+"조약돌이 부족하다 정신차려");
        }
    }

}