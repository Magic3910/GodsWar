package godswar.godswar.Manager.CommandModule;

import godswar.godswar.Utility.PermissionChecker;
import godswar.godswar.Timer.CoolTime;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CoolTimeClear
{
    public static void Module(CommandSender sender)
    {
        if (PermissionChecker.Sender(sender))
        {
            CoolTime.ini=true;
            Bukkit.broadcastMessage("관리자가 모든 쿨타임을 초기화 하였습니다.");
        }
    }
}