package godswar.godswar.Manager.Handler;

import godswar.godswar.Manager.CommandModule.*;
import godswar.godswar.Theomachy.DB.GameData;
import godswar.godswar.Theomachy.Theomachy;
import godswar.godswar.Ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler {

    public static void T_Handler(CommandSender sender, String[] data, Theomachy main){

        switch(data[0]){
            case "alist":
                AbilityInfo.showAllAbility(sender); break;
            case "black":
                Blacklist.Module(sender); break;
            case "set":
                GUISetting.Module(sender); break;
            case "help":
                Help.Module(sender); break;
            case "spawn": case "s":
                Spawn.Module(sender, data); break;
            case "team": case "t":
                Team.Module(sender, data); break;
            case "info": case "i":
                TeamInfo.Module(sender, data); break;
            case "tip":
                Tip.Module(sender); break;
            case "start":
                GameHandler.GameReady(sender, main); break;
            case "stop":
                GameHandler.GameStop(sender); break;
            case "clear": case "c":
                CoolTimeClear.Module(sender); break;
            case "ability": case "a":
                AbilitySet.Module(sender, data, main); break;
            default:
                sender.sendMessage("명령어를 잘못 입력하셨습니다."); break;
        }

    }

    public static void X_Handler(CommandSender sender, String[] data)
    {
        String playerName = sender.getName();
        String targetName = data[0];
        Ability ability = GameData.PlayerAbility.get(playerName);
        if (ability != null)
        {
            boolean check=false;
            for(Player p:Bukkit.getOnlinePlayers()){
                if(p.getName().equals(targetName))
                    check=true;
            }
            if (check)
                ability.targetSet(sender, targetName);
            else
                sender.sendMessage("온라인 플레이어가 아닙니다.  "+targetName);
        }
        else
            sender.sendMessage("능력이 없습니다.");
    }

}
