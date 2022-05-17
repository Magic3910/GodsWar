package com.magical.Timer;

import com.magical.Ability.Ability;
import com.magical.Theomachy.DB.GameData;
import com.magical.Utility.ReturnAbilityName;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardTimer implements Runnable{
    @Override
    public void run() {

        for(Player p: Bukkit.getOnlinePlayers()){
            Scoreboard sb=Bukkit.getScoreboardManager().getNewScoreboard();
            Objective ob=sb.registerNewObjective("INFO", "Obj", ChatColor.YELLOW+"안내판");
            ob.setDisplaySlot(DisplaySlot.SIDEBAR);
            Ability a=GameData.PlayerAbility.get(p.getName());

            if(a!=null){
                if(!a.activeType){
                    ob.getScore(ChatColor.GOLD+"능력").setScore(5);
                    ob.getScore(ChatColor.WHITE+ ReturnAbilityName.name(a.abilityCode)).setScore(4);
                }else if(a.sta2==-1){
                    ob.getScore(ChatColor.GOLD+"능력").setScore(5);
                    ob.getScore(ChatColor.WHITE+ ReturnAbilityName.name(a.abilityCode)).setScore(4);
                    if(!a.abilityName.equals("메구밍") && !CoolTime.COOL0.containsKey(p.getName())){
                        ob.getScore(ChatColor.AQUA+"【일반】 "+ ChatColor.WHITE+"능력").setScore(3);
                        ob.getScore(ChatColor.DARK_AQUA+"사용 가능!").setScore(2);
                    }
                    if(CoolTime.COOL0.containsKey(p.getName())){
                        int cool0=CoolTime.COOL0.get(p.getName())-1;
                        ob.getScore(ChatColor.AQUA+"【일반】 "+ ChatColor.WHITE+"쿨타임").setScore(3);
                        ob.getScore(ChatColor.WHITE+""+cool0/60+"분 "+cool0%60+"초").setScore(2);
                    }
                }else{
                    ob.getScore(ChatColor.GOLD+"능력").setScore(5);
                    ob.getScore(ChatColor.WHITE+ ReturnAbilityName.name(a.abilityCode)).setScore(4);
                    if(CoolTime.COOL1.containsKey(p.getName())){
                        int cool1=CoolTime.COOL1.get(p.getName())-1;
                        ob.getScore(ChatColor.AQUA+"【일반】 "+ ChatColor.WHITE+"쿨타임").setScore(3);
                        ob.getScore(ChatColor.WHITE+""+cool1/60+"분 "+cool1%60+"초").setScore(2);
                    }else{
                        ob.getScore(ChatColor.AQUA+"【일반】 "+ ChatColor.WHITE+"능력").setScore(3);
                        ob.getScore(ChatColor.DARK_AQUA+"사용 가능!").setScore(2);
                    }


                    if(CoolTime.COOL2.containsKey(p.getName())){
                        int cool1=CoolTime.COOL2.get(p.getName())-1;
                        ob.getScore(ChatColor.RED+"【고급】 "+ ChatColor.WHITE+"쿨타임").setScore(1);
                        ob.getScore(ChatColor.WHITE+""+cool1/60+"분 "+cool1%60+"초").setScore(0);
                    }else{
                        ob.getScore(ChatColor.RED+"【고급】 "+ ChatColor.WHITE+"능력").setScore(1);
                        ob.getScore(ChatColor.DARK_AQUA+"사용 가능!").setScore(0);
                    }
                }

                p.setScoreboard(sb);
            }

        }

    }
}
