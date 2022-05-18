package godswar.godswar.Manager.CommandModule;

import java.util.Iterator;
import java.util.Map.Entry;

import godswar.godswar.DB.GameData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TeamInfo
{
    public static void Module(CommandSender sender, String[] data)
    {
        if (data.length>=2)
        {
            String teamName=data[1];
            if (GameData.PlayerTeam.containsValue(teamName))
            {
                sender.sendMessage(ChatColor.GREEN+"======  "+ChatColor.DARK_AQUA+teamName+ChatColor.GREEN+"  ======");
                Iterator<Entry<String, String>> iterator = GameData.PlayerTeam.entrySet().iterator();
                for (int i=1;iterator.hasNext();i++)
                {
                    Entry<String, String> entry=iterator.next();
                    if (entry.getValue().equals(teamName))
                    {
                        String playerName=entry.getKey();
                        sender.sendMessage(i+".  "+ChatColor.GOLD+playerName);
                    }
                }
            }
            else
                sender.sendMessage("해당 팀에 팀원이 없습니다.");
        }
        else
        {
            sender.sendMessage(ChatColor.YELLOW+"자신의 팀을 확인합니다");
            String teamName = GameData.PlayerTeam.get(sender.getName());
            if (teamName != null)
            {
                if (GameData.PlayerTeam.containsValue(teamName))
                {
                    sender.sendMessage(ChatColor.GREEN+"======  "+ChatColor.DARK_AQUA+teamName+ChatColor.GREEN+"  ======");
                    Iterator<Entry<String, String>> iterator = GameData.PlayerTeam.entrySet().iterator();
                    for (int i=1;iterator.hasNext();i++)
                    {
                        Entry<String, String> entry=iterator.next();
                        if (entry.getValue().equals(teamName))
                        {
                            String playerName=entry.getKey();
                            sender.sendMessage(i+".  "+ChatColor.GOLD+playerName);
                        }
                    }
                }
                else
                    sender.sendMessage("소속된 팀이 없습니다.");
            }
        }

    }
}

