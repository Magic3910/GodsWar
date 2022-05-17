package com.magical.Timer;

import java.util.Collection;
import java.util.List;

import com.magical.Ability.Ability;
import com.magical.Manager.CommandModule.GameHandler;
import com.magical.Theomachy.DB.GameData;
import com.magical.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class GameReadyTimer implements Runnable
{
    private int count=1;
    private final Player[] playerList;
    private final String[] setting = new String[8];
    private Difficulty difficulty;
    private final World world;
    public GameReadyTimer()
    {
        this.playerList=new Player[Bukkit.getOnlinePlayers().size()];
        int i=0;
        for(Player p:Bukkit.getOnlinePlayers()) {
            this.playerList[i]=p; i++;
        }
        setting[0] = Theomachy.INVENTORY_CLEAR ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[1] = Theomachy.GIVE_ITEM ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[2] = Theomachy.IGNORE_BED ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[3] = Theomachy.AUTO_SAVE ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[4] = Theomachy.ANIMAL ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[5] = Theomachy.MONSTER ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[7] = Theomachy.ENTITIES_REMOVE ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        difficulty=null;
        switch (Theomachy.DIFFICULTY)
        {
            case 0:
                setting[6] = ChatColor.GREEN + "평화로움";
                difficulty = Difficulty.PEACEFUL;
                break;
            case 1:
                setting[6] = ChatColor.AQUA + "쉬움";
                difficulty = Difficulty.EASY;
                break;
            case 2:
                setting[6] = ChatColor.YELLOW + "보통";
                difficulty = Difficulty.NORMAL;
                break;
            case 3:
                setting[6] = ChatColor.GREEN + "어려움";
                difficulty = Difficulty.HARD;
                break;
            default:
                setting[6] = ChatColor.GREEN + "쉬움";
                difficulty = Difficulty.EASY;
        }
        world = Bukkit.getWorlds().get(0);
    }


    public void run()
    {
        if (GameHandler.Ready && count<20)
        {
            switch(count)
            {
                case 2:
                    Bukkit.broadcastMessage(ChatColor.AQUA+"잠시 후 능력이 활성화됩니다.");
                    break;
                case 4:
                    if (Theomachy.ENTITIES_REMOVE)
                    {
                        try{
                            List<Entity> entities = world.getEntities();
                            for (Entity e : entities)
                            {
                                if (e instanceof Item || e instanceof Monster || e instanceof Animals)
                                    e.remove();
                            }
                        }catch(NullPointerException e){
                            e.getCause();
                        }
                    }
                    Bukkit.broadcastMessage(ChatColor.AQUA+"능력 활성화 완료.");

                case 6:
                    world.setPVP(true);
                    world.setAutoSave(Theomachy.AUTO_SAVE);
                    world.setSpawnFlags(Theomachy.MONSTER, Theomachy.ANIMAL);
                    world.setDifficulty(this.difficulty);
                    world.setTime(6000);
                    Collection<Ability> playerAbilityList = GameData.PlayerAbility.values();
                    for (Ability e : playerAbilityList)
                    {
                        e.conditionSet();
                        e.buff();
                    }
                    GameHandler.Start=true;
                    break;
            }
        }
        else {
            Bukkit.getScheduler().cancelTask(GameHandler.timer);
        }

        count++;
    }
}