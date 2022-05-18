package godswar.godswar.Timer;

import java.util.Collection;
import java.util.List;

import godswar.godswar.Ability.Ability;
import godswar.godswar.GodsWar;
import godswar.godswar.Manager.CommandModule.GameHandler;
import godswar.godswar.DB.GameData;
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
        setting[0] = GodsWar.INVENTORY_CLEAR ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[1] = GodsWar.GIVE_ITEM ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[2] = GodsWar.IGNORE_BED ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[3] = GodsWar.AUTO_SAVE ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[4] = GodsWar.ANIMAL ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[5] = GodsWar.MONSTER ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        setting[7] = GodsWar.ENTITIES_REMOVE ? ChatColor.AQUA+"ON" : ChatColor.RED+"OFF";
        difficulty=null;
        switch (GodsWar.DIFFICULTY)
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
                    if (GodsWar.ENTITIES_REMOVE)
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
                    world.setAutoSave(GodsWar.AUTO_SAVE);
                    world.setSpawnFlags(GodsWar.MONSTER, GodsWar.ANIMAL);
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