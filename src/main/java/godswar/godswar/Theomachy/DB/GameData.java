package godswar.godswar.Theomachy.DB;

import java.util.HashMap;

import godswar.godswar.Ability.Ability;
import org.bukkit.Location;

public class GameData
{
    public static HashMap<String, Ability> PlayerAbility = new HashMap<>(); //플레이어 지정 능력
    public static HashMap<String,String> PlayerTeam = new HashMap<>(); //플레이어, 팀이름
    public static HashMap<String,Location> SpawnArea = new HashMap<>(); //팀 스폰 지역
    public static HashMap<Location, String> DiamondLocation = new HashMap<>(); //다이아몬드 블럭 위치
}
