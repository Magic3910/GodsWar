package godswar.godswar.Manager.CommandModule;

import java.util.Collection;
import java.util.Random;

import godswar.godswar.Ability.God.*;
import godswar.godswar.Ability.Human.*;
import godswar.godswar.Ability.Misc.*;
import godswar.godswar.GodsWar;
import godswar.godswar.DB.GameData;
import godswar.godswar.GodsWar;
import godswar.godswar.Utility.CodeHelper;
import godswar.godswar.Utility.PermissionChecker;
import godswar.godswar.Utility.RandomNumberConstructor;
import godswar.godswar.Ability.Misc.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import godswar.godswar.Ability.Ability;
import godswar.godswar.Ability.Human.*;
import godswar.godswar.Ability.God.*;

public class AbilitySet
{
    public static void Module(CommandSender sender, String[] data, GodsWar t)
    {
        if (PermissionChecker.Sender(sender))
        {
                if (data.length<=1)
                {
                    sender.sendMessage("/t a help   모든 능력의 코드를 확인합니다.");
                    sender.sendMessage("/t a random 현재 접속한 모든 플레이어에게 랜덤으로 능력을 할당합니다.");
                    sender.sendMessage("/t a remove <Player> 해당 플레이어의 능력을 삭제합니다.");
                    sender.sendMessage("/t a rn <Player> 해당 플레이어의 능력을 랜덤으로 설정합니다.");
                    sender.sendMessage("/t a reset  모든 능력을 초기화 합니다");
                    sender.sendMessage("/t a <AbilityCode> <Player>  플레이어에게 해당 능력을 적용합니다.");
                }
                else if (data[1].equalsIgnoreCase("help"))
                    CodeHelper.ShowAbilityCodeNumber(sender);
                else if (data[1].equalsIgnoreCase("remove"))//삭제
                {
                    if (data[2] != null)
                        Remove(sender, data[2]);
                    else
                        sender.sendMessage("능력을 삭제할 플레이어의 이름을 적어주세요.");
                }
                else if (data[1].equalsIgnoreCase("rn"))//랜덤설정
                {
                    try {
                        if (data[2] != null)
                            Rns(data[2], t);
                        else
                            sender.sendMessage("능력을 설정할 플레이어의 이름을 적어주세요.");
                    } catch (Exception e) {
                    }
                }
                else if (data[1].equalsIgnoreCase("reset"))//리셋
                    Reset();
                else if (data[1].equalsIgnoreCase("random"))//랜덤
                    RandomAssignment(sender, t);
                else if (data.length >= 3)
                    forceAssignment(sender, data, t);
                else
                {
                    sender.sendMessage("잘못된 입력입니다.");
                    sender.sendMessage("/t a 로 명령어를 확인하세요.");
                }
        }
    }
    public static void Rns(String playerName, GodsWar t)
    {
        if (RandomNumberConstructor.CanlistDump.isEmpty()) {
            Bukkit.getPlayer(playerName).sendMessage("능력이 모두 소진되어 배정받지 못했습니다");
        }
        else {
            GodsWar.log.info("남은 능력 수 : "+ RandomNumberConstructor.CanlistDump.size());
            int r[] = RandomNumberConstructor.ndl();
            abiltiyAssignment(r[0], playerName, Bukkit.getPlayer(playerName), t);
            RandomNumberConstructor.CanlistDump.remove(RandomNumberConstructor.CanlistDump.indexOf(r[0]));

            Bukkit.getPlayer(playerName).sendMessage("능력이 할당되었습니다. /t help로 능력을 확인해보세요.");
        }
    }
    public static void Rn()
    {
        Random random=new Random();
        int[] rn;
        rn=new int[RandomNumberConstructor.CanlistDump.size()];

        Object[] rn1= RandomNumberConstructor.CanlistDump.toArray();

        for(int i=0;i<rn1.length;i++) {
            rn[i]=(Integer) rn1[i];
        }

        for(int i=0; i<rn.length; i++)//섞
        {
            int r=random.nextInt(rn.length-1);
            int temp=rn[i];
            rn[i]=rn[r];
            rn[r]=temp;
        }
        StringBuilder sb = new StringBuilder();
        for (int num : rn)
            sb.append(num).append(" ");
        GodsWar.log.info(String.valueOf(sb));
    }
    public static void Remove(CommandSender sender, String playerName)
    {
        Ability ability = GameData.PlayerAbility.get(playerName);
        if (ability != null)
        {
            GameData.PlayerAbility.remove(playerName);
            sender.sendMessage("플레이어의 능력을 삭제하였습니다.");
        }
        else
            sender.sendMessage("플레이어의 능력이 없습니다.");
    }

    public static void Reset()
    {
        GameData.PlayerAbility.clear();
        Bukkit.broadcastMessage(ChatColor.AQUA+"관리자가 모두의 능력을 초기화하였습니다.");
    }
    private static void RandomAssignment(CommandSender p, GodsWar t)
    {


        if (!GameData.PlayerAbility.isEmpty())
        {
            Bukkit.broadcastMessage("모든 능력을 삭제한 후 재추첨합니다.");
            GameData.PlayerAbility.clear();
        }
        Collection<? extends Player> playerlist=Bukkit.getOnlinePlayers();
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA+"인식된 플레이어 목록");
        for(Player e : playerlist)
            Bukkit.broadcastMessage(ChatColor.GOLD+"  "+e.getName());
        int[] rn = RandomNumberConstructor.nonDuplicate();
        int length;
        length = Math.min(playerlist.size(), Blacklist.Canlist);

        int i=0;
        for (Player e:playerlist)
        {
            String playerName = e.getName();
            abiltiyAssignment(rn[i],playerName,p, t);
            RandomNumberConstructor.CanlistDump.remove(RandomNumberConstructor.CanlistDump.indexOf(rn[i]));
            i++;
        }
        Rn();
        Bukkit.broadcastMessage("모두에게 능력이 적용되었습니다.");
        Bukkit.broadcastMessage("/t help 로 확인해보세요.");
        if (length!=playerlist.size())
            Bukkit.broadcastMessage("인원이 너무 많습니다. 전부에게 능력을 할당하지 못했을수도 있습니다.");
    }

    private static void forceAssignment(CommandSender sender, String[] data, GodsWar t)
    {

        Player p=(Player) sender;

        for (int i=2; i<data.length; i++)
        {
            String abilityName = data[1];
            String playerName=data[i];
            if (Bukkit.getPlayer(playerName)!=null)
            {
                try{
                    int abilityCode = Integer.parseInt(abilityName);
                    abiltiyAssignment(abilityCode, playerName, p, t);
                    Player player = Bukkit.getPlayer(playerName);
                    Bukkit.broadcastMessage("관리자가 "+ChatColor.RED+playerName+ChatColor.WHITE+" 에게 능력을 할당하였습니다.");
                    if(player != null)
                        player.sendMessage("능력이 할당되었습니다. /t help로 능력을 확인해보세요.");
                }
                catch (NumberFormatException e)
                {sender.sendMessage("능력코드는 정수를 입력해 주세요");}
            }
            else
                sender.sendMessage(playerName+" 에 해당하는 온라인 유저가 없습니다.");
        }
    }

    public static void abiltiyAssignment(int abilityCode, String playerName, CommandSender p, GodsWar t)
    {
        if (abilityCode == 1)
            GameData.PlayerAbility.put(playerName, new Zeus(playerName));
        else if (abilityCode == 2)
            GameData.PlayerAbility.put(playerName, new Hades(playerName));
        else if (abilityCode == 3)
            GameData.PlayerAbility.put(playerName, new Demeter(playerName));
        else if (abilityCode == 4)
            GameData.PlayerAbility.put(playerName, new Athena(playerName));
        else if (abilityCode == 5)
            GameData.PlayerAbility.put(playerName, new Apollon(playerName));
        else if (abilityCode == 6)
            GameData.PlayerAbility.put(playerName, new Artemis(playerName));
        else if (abilityCode == 7)
            GameData.PlayerAbility.put(playerName, new Ares(playerName));
        else if (abilityCode == 8)
            GameData.PlayerAbility.put(playerName, new Hephaestus(playerName));
        else if (abilityCode == 9)
            GameData.PlayerAbility.put(playerName, new Asclepius(playerName));
        else if (abilityCode == 10)
            GameData.PlayerAbility.put(playerName, new Hermes(playerName));
        else if (abilityCode == 11)
            GameData.PlayerAbility.put(playerName, new Dionysus(playerName));
        else if (abilityCode == 12)
            GameData.PlayerAbility.put(playerName, new Aprodite(playerName));
        else if (abilityCode == 13)
            GameData.PlayerAbility.put(playerName, new Eris(playerName));
        else if (abilityCode == 14)
            GameData.PlayerAbility.put(playerName, new Morpious(playerName));
        else if (abilityCode == 15)
            GameData.PlayerAbility.put(playerName, new Aeolus(playerName, t));
        else if (abilityCode == 16)
            GameData.PlayerAbility.put(playerName, new Akasha(playerName));
        else if (abilityCode == 17)
            GameData.PlayerAbility.put(playerName, new Horeundal(playerName, t));
        else if (abilityCode == 18)
            GameData.PlayerAbility.put(playerName, new JuJak(playerName));

        else if (abilityCode == 101)
            GameData.PlayerAbility.put(playerName, new Archer(playerName));
        else if (abilityCode == 102)
            GameData.PlayerAbility.put(playerName, new Miner(playerName));
        else if (abilityCode == 103)
            GameData.PlayerAbility.put(playerName, new Stance(playerName));
        else if (abilityCode == 104)
            GameData.PlayerAbility.put(playerName, new Teleporter(playerName));
        else if (abilityCode == 105)
            GameData.PlayerAbility.put(playerName, new Bomber(playerName));
        else if (abilityCode == 106)
            GameData.PlayerAbility.put(playerName, new Creeper(playerName));
        else if (abilityCode == 107)
            GameData.PlayerAbility.put(playerName, new Wizard(playerName, t));
        else if (abilityCode == 108)
            GameData.PlayerAbility.put(playerName, new Assasin(playerName));
        else if (abilityCode == 109)
            GameData.PlayerAbility.put(playerName, new Reflection(playerName));
        else if (abilityCode == 110)
            GameData.PlayerAbility.put(playerName, new Blinder(playerName));
        else if (abilityCode == 111)
            GameData.PlayerAbility.put(playerName, new Invincibility(playerName));
        else if (abilityCode == 112)
            GameData.PlayerAbility.put(playerName, new Clocking(playerName, t));
        else if (abilityCode == 113)
            GameData.PlayerAbility.put(playerName, new Blacksmith(playerName));
        else if (abilityCode == 114)
            GameData.PlayerAbility.put(playerName, new Boxer(playerName));
        else if (abilityCode == 115)
            GameData.PlayerAbility.put(playerName, new Priest(playerName));
        else if (abilityCode == 116)
            GameData.PlayerAbility.put(playerName, new Witch(playerName));
        else if (abilityCode == 117)
            GameData.PlayerAbility.put(playerName, new Sniper(playerName));
        else if (abilityCode == 118)
            GameData.PlayerAbility.put(playerName, new Voodoo(playerName));
        else if (abilityCode == 119)
            GameData.PlayerAbility.put(playerName, new Anorexia(playerName));
        else if (abilityCode == 120)
            GameData.PlayerAbility.put(playerName, new Bulter(playerName));
        else if (abilityCode == 121)
            GameData.PlayerAbility.put(playerName, new Midoriya(playerName));
        else if (abilityCode == 122)
            GameData.PlayerAbility.put(playerName, new Goldspoon(playerName));
        else if (abilityCode == 123)
            GameData.PlayerAbility.put(playerName, new QueenBee(playerName));
        else if (abilityCode == 124)
            GameData.PlayerAbility.put(playerName, new Snow(playerName));
        else if (abilityCode == 125)
            GameData.PlayerAbility.put(playerName, new Tajja(playerName));
        else if (abilityCode == 126)
            GameData.PlayerAbility.put(playerName, new Girl(playerName));
        else if (abilityCode == 127)
            GameData.PlayerAbility.put(playerName, new Megumin(playerName, t));
        else if (abilityCode == 128)
            GameData.PlayerAbility.put(playerName, new Pokego(playerName, t));
        else if (abilityCode == 129)
            GameData.PlayerAbility.put(playerName, new Darkness(playerName));
        else if (abilityCode == 130)
            GameData.PlayerAbility.put(playerName, new Gasolin(playerName));
        else if (abilityCode == 131)
            GameData.PlayerAbility.put(playerName, new Zet(playerName));
        else if (abilityCode == 132)
            GameData.PlayerAbility.put(playerName, new Hermione(playerName));
        else if (abilityCode == 133)
            GameData.PlayerAbility.put(playerName, new Harry(playerName));
        else if (abilityCode == 134)
            GameData.PlayerAbility.put(playerName, new Gardener(playerName));
        else if (abilityCode == 135)
            GameData.PlayerAbility.put(playerName, new AcidArcher(playerName));
        else if (abilityCode == 136)
            GameData.PlayerAbility.put(playerName, new Galbi(playerName));

        else if (abilityCode == 1001)
            GameData.PlayerAbility.put(playerName, new Themis(playerName));
        else if (abilityCode == 1002)
            GameData.PlayerAbility.put(playerName, new Demigod(playerName));
        else if (abilityCode == 1003)
            GameData.PlayerAbility.put(playerName, new DiceGod(playerName));
        else if (abilityCode == 1004)
            GameData.PlayerAbility.put(playerName, new Ira(playerName));
        else if (abilityCode == 1005)
            GameData.PlayerAbility.put(playerName, new Summoner(playerName));

        else
        {
            p.sendMessage("능력 혹은 능력 코드 번호를 잘못 입력하셨습니다.");
            p.sendMessage("/t a help 명령어로 능력 코드를 확인하실 수 있습니다.");
        }
    }
}