package godswar.godswar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class update {
    public static void updt(String ver) {
        try {
            URL u=new URL("https://raw.githubusercontent.com/Magic3910/version/main/README.md");
            InputStreamReader isr=new InputStreamReader(u.openStream());
            BufferedReader br=new BufferedReader(isr);

            String Line;

            while((Line=br.readLine())!=null){
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"[신들의 전쟁] "+ChatColor.GREEN+"최신 버전은 "+Line+"입니다.");
                if(Line.equals(ver)){
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"[신들의 전쟁] "+ChatColor.GREEN+"최신 버전입니다!");
                }else{
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"[신들의 전쟁] "+ChatColor.RED+"구 버전입니다. 개발자 깃허브에서 최신 버전을 다운 받으세요.");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[신들의 전쟁] 개발자 블로그: https://github.com/Magic3910/GodsWar");
                }
            }
        }catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"[ 신들의 전쟁 ] "+ChatColor.RED+"업데이트 체크 실패. 인터넷 연결을 확인해 주세요.");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"[ 신들의 전쟁 ] "+ChatColor.RED+"인터넷이 제대로 연결되 있다면 제작자에게 문의해 주세요.");
        }
    }
}
