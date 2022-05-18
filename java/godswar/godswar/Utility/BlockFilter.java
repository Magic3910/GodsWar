package godswar.godswar.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockFilter
{
    public static boolean AirToFar(Player player, Block block)
    {
        if (!block.getType().equals(Material.AIR))
            return true;
        else
        {
            T_Message.TooFarError(player,1);
            return false;
        }
    }
}
