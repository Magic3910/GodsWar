package godswar.godswar.Timer;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Manager.CommandModule.GameHandler;
import godswar.godswar.Theomachy.DB.GameData;
import godswar.godswar.Utility.T_Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TimerTask;

public class CoolTime extends TimerTask
{
    public static boolean ini=false;
    public static HashMap<String, Integer> COOL0 = new HashMap<>();
    public static HashMap<String, Integer> COOL1 = new HashMap<>();
    public static HashMap<String, Integer> COOL2 = new HashMap<>();
    private int count=1;
    public void run()
    {
        if (!GameHandler.Ready)
            this.cancel();

        try{
            if (!COOL0.isEmpty())
            {
                Iterator<Entry<String, Integer>> iter = COOL0.entrySet().iterator();
                while(iter.hasNext())
                {
                    Entry<String, Integer> entry = iter.next();
                    String playerName = entry.getKey();
                    int value = entry.getValue()-1;
                    if (value <= 0)
                    {
                        COOL0.remove(playerName);
                        T_Message.AbilityReset(0, playerName);
                    }
                    else
                    {
                        COOL0.put(playerName, value);
                        if (value <=3)
                            T_Message.CoolTimeCountTeller(0, playerName, value);
                    }
                }
            }

            if (!COOL1.isEmpty())
            {
                Iterator<Entry<String, Integer>> iter = COOL1.entrySet().iterator();
                while(iter.hasNext())
                {
                    Entry<String, Integer> entry = iter.next();
                    String playerName = entry.getKey();
                    int value = entry.getValue()-1;
                    if (value <= 0)
                    {
                        COOL1.remove(playerName);
                        T_Message.AbilityReset(1, playerName);
                    }
                    else
                    {
                        COOL1.put(playerName, value);
                        if (value <=3)
                            T_Message.CoolTimeCountTeller(1, playerName, value);
                    }
                }
            }
            if (!COOL2.isEmpty())
            {
                Iterator<Entry<String, Integer>> iter = COOL2.entrySet().iterator();
                while(iter.hasNext())
                {
                    Entry<String, Integer> entry = iter.next();
                    String playerName = entry.getKey();
                    int value = entry.getValue()-1;
                    if (value <= 0)
                    {
                        COOL2.remove(playerName);
                        T_Message.AbilityReset(2, playerName);
                    }
                    else
                    {
                        COOL2.put(playerName, value);
                        if (value <=3)
                            T_Message.CoolTimeCountTeller(2, playerName, value);
                    }
                }
            }
            if (ini)
            {
                COOL0.clear();
                COOL1.clear();
                COOL2.clear();
                ini=false;
            }
            if (count%150 == 0)
            {
                Collection<Ability> playerAbilityList = GameData.PlayerAbility.values();
                for (Ability e : playerAbilityList)
                {
                    if (e.buffType)
                        e.buff();
                }
            }
        }
        catch(Exception e)
        {
        }
        count++;
    }
}
