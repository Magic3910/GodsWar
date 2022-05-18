package godswar.godswar;

import godswar.godswar.Manager.CommandModule.Blacklist;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class GodsWar extends JavaPlugin {
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

    public static Logger log= Bukkit.getLogger();

    public File black=new File(getDataFolder(), "blacklist.yml");
    public File config=new File(getDataFolder(), "config.yml");

    @Override
    public void onEnable() {
        ItemStack blaze = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta=blaze.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"능력의 막대");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        List<String> l = null;
        l.add(ChatColor.GREEN+"[ 좌클릭 ] : 일반능력 사용");
        l.add(ChatColor.GREEN+"[ 우클릭 ] : 고급능력 사용");
        meta.setLore(l);
        ShapedRecipe recipe = new ShapedRecipe(blaze).shape(new String[]{"|","|","|"}).setIngredient('|',Material.STICK);
        getServer().addRecipe(recipe);
        saveResource("blacklist.yml", false);
        log.info(ChatColor.AQUA+"[ 신들의 전쟁 ]"+ChatColor.GREEN+" 플러그인 활성화 완료");
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;
        try {
            fis = new FileInputStream(black);
            isr=new InputStreamReader(fis);
            br=new BufferedReader(isr);
            String line;
            while((line=br.readLine())!=null){
                Blacklist.Blacklist.add(Integer.parseInt(line));
            }
        }catch(FileNotFoundException e) {
            log.info("블랙리스트 파일이 존재하지 않습니다. 재생성합니다.");
        } catch (IOException e) {
            log.info("블랙리스트에 알 수 없는 오류가 발생. 로그와 함께 개발자에게 문의해주세요.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
