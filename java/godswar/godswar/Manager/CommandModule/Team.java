package godswar.godswar.Manager.CommandModule;

import godswar.godswar.DB.GameData;
import godswar.godswar.Utility.PermissionChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Team
{
    public static void Module(CommandSender sender, String[] data)
    {
        if (PermissionChecker.Sender(sender))
        {
            if (data.length>2)
            {
                for (int i=2; i<data.length; i++)
                {
                    if (Bukkit.getPlayer(data[0])==null)
                    {
                        String playerName = data[i];
                        String teamName = data[1];
                        String teamNameOld = GameData.PlayerTeam.get(playerName);
                        if (teamNameOld == null) //플레이어 팀 초기화
                        {
                            GameData.PlayerTeam.put(data[i], teamName);
                            Bukkit.broadcastMessage("플레이어 "+ChatColor.RED+playerName+ChatColor.WHITE+" (이)가 팀 "+ChatColor.DARK_AQUA+teamName+ChatColor.WHITE+" 에 등록되었습니다.");
                        }
                        else //플레이어 팀 변경
                        {
                            GameData.PlayerTeam.put(data[i], teamName);
                            Bukkit.broadcastMessage("플레이어 "+ChatColor.RED+playerName+ChatColor.WHITE+" (이)가 팀 "+ChatColor.DARK_AQUA+teamName+ChatColor.WHITE+" 에 등록되었습니다.");
                        }
                    }
                    else
                        sender.sendMessage(data[i]+" 해당하는 유저가 없습니다.");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.YELLOW+("/t  team(t)   ")+ChatColor.AQUA+("<TeamName>  ")+ChatColor.RED+("<Player>  ")+ChatColor.WHITE+("플레이어를 팀에 등록합니다."));
                sender.sendMessage("한번에 다수의 플레이어를 한 팀에 등록 할 수 있습니다.");
                sender.sendMessage("ex) /t t  팀   플레이어1   플레이어2   플레이어3");
            }
        }
    }
}