package com.magical.Ability.God;

import com.magical.Ability.Ability;
import com.magical.Theomachy.DB.GameData;
import com.magical.Utility.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Morpious extends Ability {

    private final static String[] des= {
            "모르피우스는 잠의 신입니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"수면 오라",
            "목표로 지정한 적을 30초 간 잠들게 합니다.",
            "목표가 자신의 주변에 있어야 사용 가능합니다.",
            "목표 지정: /x <대상>"};

    private String abilitytarget;

    public Morpious(String playerName) {
        super(playerName, "모르피우스", 14, true, false, false, des);

        this.rank=3;

        this.cool1=180;
        this.sta1=32;
    }

    public void T_Active(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, st))
        {
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                leftAction(player);
                break;
            }
        }
    }

    private void leftAction(Player player){
        if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, co, cool1))
        {
            String[] team = new String[2];
            team[0]= GameData.PlayerTeam.get(player.getName());
            team[1]=GameData.PlayerTeam.get(abilitytarget);

            if(team[0]!=team[1]){
                if(abilitytarget!=null){
                    if(player.getName().equals(abilitytarget)){
                        player.sendMessage(ChatColor.RED+"목표는 본인이 아니어야 합니다.");
                    }

                    else{
                        Player target = Bukkit.getPlayer(abilitytarget);

                        if(target==null){
                            player.sendMessage(abilitytarget+" 님은 현재 서버에 없는 것 같습니다..");
                        }else{
                            if(GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10).contains(player)){
                                Skill.Use(player, co, sta1, 0, cool1);
                                player.sendMessage(ChatColor.GRAY+"목표를 잠재웠습니다!");
                                target.sendMessage(ChatColor.GRAY+"모르피우스가 당신을 재웠습니다!");
                                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*30,0));
                                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*30, 3));
                            }else{
                                player.sendMessage("목표가 주변에 있어야 합니다!");
                            }
                        }
                    }

                }
                else{
                    player.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
                }
            }
            else{
                player.sendMessage(ChatColor.GRAY+"본인의 팀이므로 잠을 재울 수 없습니다!");
            }
        }
    }

    public void targetSet(CommandSender sender, String targetName)
    {
        if (!playerName.equals(targetName))
        {
           this.abilitytarget = targetName;
           sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
        }
        else
            sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");
    }
}
