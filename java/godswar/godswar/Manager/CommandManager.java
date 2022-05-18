package godswar.godswar.Manager;

import godswar.godswar.GodsWar;
import godswar.godswar.Manager.CommandModule.Convi;
import godswar.godswar.Manager.Handler.CommandHandler;
import godswar.godswar.GodsWar;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
    public static GodsWar main;
    public CommandManager(GodsWar t) {
        main=t;
        t.getCommand("t").setExecutor(this);
        t.getCommand("x").setExecutor(this);
        t.getCommand("도박").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] data) {

        if (label.equals("t"))
        {
            if (data.length==0) //설명 보기
            {
                sender.sendMessage(ChatColor.AQUA+("【"+ChatColor.WHITE+"신들의 전쟁 명령어 목록"+ChatColor.AQUA+"】"));
                sender.sendMessage(ChatColor.YELLOW+("/t  help     ")+ChatColor.WHITE+("자신의 능력을 확인합니다."));
                sender.sendMessage(ChatColor.YELLOW+("/도박     ")+ChatColor.WHITE+("도박"));
                sender.sendMessage(ChatColor.YELLOW+("/x  ")+ChatColor.RED+("<Player>     ")+ChatColor.WHITE+("해당 플레이어를 자신의 타겟으로 등록합니다"));
            }
            else
                CommandHandler.T_Handler(sender, data, main);
        }

        else if (label.equalsIgnoreCase("x"))
        {
            if (data.length==0) //설명 보기
                sender.sendMessage(ChatColor.YELLOW+("/x  ")+ChatColor.RED+("<Player>     ")+ChatColor.WHITE+("해당 플레이어를 자신의 타겟으로 등록합니다"));
            else
                CommandHandler.X_Handler(sender, data);
        }

        else if (label.equalsIgnoreCase("도박")){
            Convi.Module(sender);
        }

        return true;
    }

}