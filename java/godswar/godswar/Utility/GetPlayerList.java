package godswar.godswar.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import godswar.godswar.DB.GameData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GetPlayerList
{
    public static List<Player> getTeamMember(Player player)
    {
        String teamName = GameData.PlayerTeam.get(player.getName());
        ArrayList<String> memberName  = new ArrayList<>();
        if (GameData.PlayerTeam.containsValue(teamName))
        {
            Iterator<Entry<String, String>> iterator = GameData.PlayerTeam.entrySet().iterator();
            while(iterator.hasNext())
            {
                Entry<String, String> entry=iterator.next();
                if (entry.getValue().equals(teamName))
                    memberName.add(entry.getKey());
            }
        }
        ArrayList<Player> memberPlayer = new ArrayList<>();
        for (String e : memberName)
        {
            if (Bukkit.getPlayer(e)!=null)
                memberPlayer.add(Bukkit.getPlayer(e));
        }
        return memberPlayer;
    }

    public static List<Player> getNearByTeamMembers(Player player, double x, double y, double z)
    {
        String playerName = player.getName();
        String playerTeamName = GameData.PlayerTeam.get(playerName);

        ArrayList<Player> nearByTeamMembers = new ArrayList<>();
        if (playerTeamName != null)
        {
            List<Entity> nearByEntityList = player.getNearbyEntities(x, y, z);
            if (!nearByEntityList.isEmpty())
            {
                for (Entity e : nearByEntityList)
                {
                    if (e instanceof Player)
                    {
                        String memberName = e.getName();
                        String memberTeamName = GameData.PlayerTeam.get(memberName);
                        if (memberTeamName.equals(playerTeamName))
                            nearByTeamMembers.add((Player)e);
                    }
                }
            }
        }
        return nearByTeamMembers;
    }

    public static List<Player> getNearByNotTeamMembers(Player player, double x, double y, double z)
    {
        String playerName = player.getName();
        String playerTeamName = GameData.PlayerTeam.get(playerName);

        ArrayList<Player> nearByNotTeamMembers = new ArrayList<>();
        List<Entity> nearByEntityList = player.getNearbyEntities(x, y, z);
        if (playerTeamName != null)
        {
            if (!nearByEntityList.isEmpty())
            {
                for (Entity e : nearByEntityList)
                {
                    if (e instanceof Player)
                    {
                        String memberName = e.getName();
                        String memberTeamName = GameData.PlayerTeam.get(memberName);
                        if (!memberTeamName.equals(playerTeamName))
                            nearByNotTeamMembers.add((Player)e);
                    }
                }
            }
        }
        else
        {
            if (!nearByEntityList.isEmpty())
            {
                for (Entity e : nearByEntityList)
                {
                    if (e instanceof Player)
                        nearByNotTeamMembers.add((Player)e);
                }
            }
        }
        return nearByNotTeamMembers;
    }
}
