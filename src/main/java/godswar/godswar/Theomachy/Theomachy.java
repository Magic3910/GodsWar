
package godswar.godswar.Theomachy;

import godswar.godswar.Theomachy.DB.AbilityData;
import godswar.godswar.Theomachy.DB.PluginData;
import godswar.godswar.Manager.CommandManager;
import godswar.godswar.DB.AbilityData;
import godswar.godswar.DB.PluginData;
import godswar.godswar.Manager.CommandModule.Blacklist;
import godswar.godswar.Manager.Handler.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Logger;

public final class Theomachy extends JavaPlugin {

    public static boolean INVENTORY_CLEAR = true;
    public static boolean GIVE_ITEM = true;
    public static boolean IGNORE_BED = true;
    public static boolean ENTITIES_REMOVE = true;
    public static boolean AUTO_SAVE = false;
    public static boolean ANIMAL = true;
    public static boolean MONSTER = true;
    public static boolean FAST_START = true;
    public static int DIFFICULTY = 1;
    public static boolean GAMB = true;
    public static boolean PROTECT = true;
    public static boolean FORBID = true;
    public static boolean SCOREBOARD = true;

    public CommandManager cm;
    public static Logger log= Bukkit.getLogger();

    public File file=new File(getDataFolder(), "blacklist.yml");

    public void onEnable()
    {
        //업데이트 체크
        UpdateChecker.check(this.getDescription().getVersion());

        log.info("[신들의 전쟁] 플러그인이 활성화되었습니다.   "+ PluginData.buildnumber+"  "+PluginData.version);
        log.info("[신들의 전쟁] 플러그인의 기본 설정을 적용 중입니다.");

        cm=new CommandManager(this);

        //블레이즈 로드 레시피 추가
        ItemStack blaze=new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta=blaze.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"능력의 막대");
        blaze.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(blaze).shape(new String[]{"|","|","|"}).
                setIngredient('|', Material.STICK);
        getServer().addRecipe(recipe);


        getServer().getPluginManager().registerEvents(new EventManager(), this);

        //블랙리스트 불러오기 및 처리
        saveResource("blacklist.yml", false);
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;
        try {
            fis = new FileInputStream(file);
            isr=new InputStreamReader(fis);
            br=new BufferedReader(isr);
            String line;
            while((line=br.readLine())!=null){
                Blacklist.Blacklist.add(Integer.parseInt(line));
            }
        }catch(FileNotFoundException e) {
            log.info("앗... 블랙리스트 파일 관련 오류가 발생했어요.");
        } catch (IOException e) {
            log.info("앗.. 블랙리스트 파일 관련 오류가 발생했어요.");
        }

        for(int i = 1; i<= AbilityData.GOD_ABILITY_NUMBER; i++) {
            if(!Blacklist.Blacklist.contains(i)) Blacklist.GodCanlist.add(i);
        }for(int i=101;i<=AbilityData.HUMAN_ABILITY_NUMBER+100;i++) {
        if(!Blacklist.Blacklist.contains(i)) Blacklist.HumanCanlist.add(i);
        }for(int i=1001;i<=AbilityData.CITY_ABILITY_NUMBER+1000;i++) {
        if(!Blacklist.Blacklist.contains(i)) Blacklist.MiscCanlist.add(i);
    }

        log.info("[신들의 전쟁] 등록된 능력");
        log.info("[신들의 전쟁] 신: "+Blacklist.GodCanlist.size()+", 인간: "+Blacklist.HumanCanlist.size()+", 기타: "+Blacklist.MiscCanlist.size());
        log.info("[신들의 전쟁] 총합: "+(Blacklist.GodCanlist.size()+Blacklist.HumanCanlist.size()+Blacklist.MiscCanlist.size()));

        //컨피그 처리
        log.info("[신들의 전쟁] 게임의 설정 불러오는 중입니다.");
        getConfig().options().copyDefaults(true);
        saveConfig();
        INVENTORY_CLEAR = getConfig().getBoolean("Clear Inventory");
        GIVE_ITEM = getConfig().getBoolean("Give Skyblock Item");
        ENTITIES_REMOVE = getConfig().getBoolean("Remove Entity");
        IGNORE_BED = getConfig().getBoolean("Ignore Bed");
        AUTO_SAVE = getConfig().getBoolean("Autosave");
        ANIMAL = getConfig().getBoolean("Spawn Animal");
        MONSTER = getConfig().getBoolean("Spawn Monster");
        DIFFICULTY = getConfig().getInt("Difficulty");
        FAST_START=getConfig().getBoolean("Fast Start");
        GAMB=getConfig().getBoolean("Use Gambling");
        PROTECT=getConfig().getBoolean("Protect Diamond from Explosion");
        FORBID=getConfig().getBoolean("Forbid Break Diamond with Diamond Pickaxe");
        SCOREBOARD=getConfig().getBoolean("Scoreboard Information");

        log.info("[신들의 전쟁] ========================================");
        log.info("[신들의 전쟁] 게임 시작 시 인벤토리 클리어 : "+INVENTORY_CLEAR);
        log.info("[신들의 전쟁] 게임 시작 시 스카이블럭 기본 아이템 지급 : "+GIVE_ITEM);
        log.info("[신들의 전쟁] 게임 시작 시 엔티티 제거 : "+ENTITIES_REMOVE);
        log.info("[신들의 전쟁] 리스폰 시 침대 무시 : "+IGNORE_BED);
        log.info("[신들의 전쟁] 빠른 시작 : "+FAST_START);
        log.info("[신들의 전쟁] 도박 허용 : "+GAMB);
        log.info("[신들의 전쟁] 서버 자동저장 : "+AUTO_SAVE);
        log.info("[신들의 전쟁] 동물 스폰 : "+ANIMAL);
        log.info("[신들의 전쟁] 몬스터 스폰 : "+MONSTER);
        log.info("[신들의 전쟁] 난이도 : "+DIFFICULTY);
        log.info("[신들의 전쟁] 스코어보드 안내 : "+SCOREBOARD);
        log.info("[신들의 전쟁] 폭발로부터 다이아몬드 블럭 보호: "+PROTECT);
        log.info("[신들의 전쟁] 다이아몬드 블럭을 다이아몬드 곡괭이로 캐는 것 금지: "+FORBID);
        log.info("[신들의 전쟁] ========================================");
        Bukkit.getConsoleSender().sendMessage("재수정자: "+ ChatColor.WHITE+"매직(magic3910)");
    }

    public void onDisable() {
        //블랙리스트 작성
        BufferedWriter bw;
        try {
            bw=new BufferedWriter(new FileWriter(file));

            for(int i:Blacklist.Blacklist) {
                bw.write(String.valueOf(i)); bw.newLine();
            }

            bw.close();
        }catch(FileNotFoundException e) {
            log.info("앗... 블랙리스트 파일 관련 오류가 발생했어요.");
        } catch (IOException e) {
            log.info("앗.. 블랙리스트 파일 관련 오류가 발생했어요.");
        }

        log.info("[신들의 전쟁] 블랙리스트가 파일로 저장되었습니다. 절대로 플러그인 폴더 내에 blacklist.yml을 건들지 마십시오.");

        //컨피그 수정
        getConfig().set("Clear Inventory", INVENTORY_CLEAR);
        getConfig().set("Give Skyblock Item", GIVE_ITEM);
        getConfig().set("Remove Entity", ENTITIES_REMOVE);
        getConfig().set("Ignore Bed", IGNORE_BED);
        getConfig().set("Autosave", AUTO_SAVE);
        getConfig().set("Spawn Animal", ANIMAL);
        getConfig().set("Spawn Monster", MONSTER);
        getConfig().set("Difficulty", DIFFICULTY);
        getConfig().set("Fast Start", FAST_START);
        getConfig().set("Use Gambling", GAMB);
        getConfig().set("Protect Diamond from Explosion", PROTECT);
        getConfig().set("Forbid Break Diamond with Diamond Pickaxe", FORBID);
        getConfig().set("Scoreboard Information", SCOREBOARD);

        getConfig().options().copyDefaults(true);
        saveConfig();

    }
}
