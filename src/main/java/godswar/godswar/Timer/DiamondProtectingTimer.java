package godswar.godswar.Timer;

import godswar.godswar.Manager.Handler.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class DiamondProtectingTimer implements Runnable{

    Block b;

    public DiamondProtectingTimer(Block b){
        this.b=b;
    }

    @Override
    public void run() {
        b.setType(Material.DIAMOND_BLOCK);
        Bukkit.getScheduler().cancelTask(EventManager.diamond);
    }
}
