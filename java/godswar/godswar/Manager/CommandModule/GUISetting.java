package godswar.godswar.Manager.CommandModule;

import godswar.godswar.GodsWar;
import godswar.godswar.Utility.PermissionChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUISetting {

    public static void Module(CommandSender sender) {

        Player p=(Player)sender;

        if(PermissionChecker.Sender(sender)) {

            p.openInventory(gui());

        }
    }

    private static Inventory gui() {

        Inventory gui=Bukkit.createInventory(null, 27, ChatColor.BLACK+":::::: 설정 ::::::");

        ArrayList<ItemStack> wool=new ArrayList<>();
        ArrayList<ItemMeta> meta=new ArrayList<>();
        ArrayList<Material> dura=new ArrayList<>();

        dura.add(GodsWar.INVENTORY_CLEAR ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.GIVE_ITEM ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.ENTITIES_REMOVE ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.IGNORE_BED ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.FAST_START ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.AUTO_SAVE ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.ANIMAL  ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.MONSTER  ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.GAMB ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.PROTECT ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.FORBID ? Material.LIME_WOOL:Material.RED_WOOL);
        dura.add(GodsWar.SCOREBOARD ? Material.LIME_WOOL:Material.RED_WOOL);

        for(int i=0;i<dura.size();i++) {
            wool.add(new ItemStack(dura.get(i)));
            meta.add(wool.get(i).getItemMeta());
        }

        meta.get(0).setDisplayName(ChatColor.WHITE+"게임 시작 시 인벤토리 클리어");
        meta.get(1).setDisplayName(ChatColor.WHITE+"게임 시작 시 스카이블럭 아이템 지급");
        meta.get(2).setDisplayName(ChatColor.WHITE+"게임 시작 시 엔티티 제거");
        meta.get(3).setDisplayName(ChatColor.WHITE+"침대 무시");
        meta.get(4).setDisplayName(ChatColor.WHITE+"빠른 시작");
        meta.get(5).setDisplayName(ChatColor.WHITE+"서버 자동 저장");
        meta.get(6).setDisplayName(ChatColor.WHITE+"동물 스폰");
        meta.get(7).setDisplayName(ChatColor.WHITE+"몬스터 스폰");
        meta.get(8).setDisplayName(ChatColor.WHITE+"도박 허용");
        meta.get(9).setDisplayName(ChatColor.WHITE+"다이아몬드 블록 방어");
        meta.get(10).setDisplayName(ChatColor.WHITE+"다이아 곡괭이 금지");
        meta.get(11).setDisplayName(ChatColor.WHITE+"스코어보드 안내 사용");

        for(int i=0;i<wool.size();i++) {
            wool.get(i).setItemMeta(meta.get(i));
        }

        for(int i=0;i<5;i++){
            gui.setItem(i*2, wool.get(i));
        }

        for(int i=0;i<5;i++){
            gui.setItem((i*2)+9, wool.get(i+5));
        }

        gui.setItem(20, wool.get(10));
        gui.setItem(23, wool.get(11));

        return gui;
    }

    public static void guiListener(ItemStack wool){

        switch(ChatColor.stripColor(wool.getItemMeta().getDisplayName())) {
            case "게임 시작 시 인벤토리 클리어":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.INVENTORY_CLEAR=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.INVENTORY_CLEAR=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "게임 시작 시 스카이블럭 아이템 지급":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.GIVE_ITEM=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.GIVE_ITEM=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "게임 시작 시 엔티티 제거":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.ENTITIES_REMOVE=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.ENTITIES_REMOVE=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "침대 무시":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.IGNORE_BED=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.IGNORE_BED=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "빠른 시작":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.FAST_START=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.FAST_START=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "서버 자동 저장":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.AUTO_SAVE=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.AUTO_SAVE=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "동물 스폰":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.ANIMAL=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.ANIMAL=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "몬스터 스폰":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.MONSTER=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.MONSTER=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "도박 허용":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.GAMB=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.GAMB=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "다이아몬드 블록 방어":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.PROTECT=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.PROTECT=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "다이아 곡괭이 금지":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.FORBID=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.FORBID=true; wool.setType(Material.LIME_WOOL); }
                break;
            case "스코어보드 안내 사용":
                if(wool.getType().equals(Material.LIME_WOOL)) { GodsWar.SCOREBOARD=false; wool.setType(Material.RED_WOOL);}
                else { GodsWar.SCOREBOARD=true; wool.setType(Material.LIME_WOOL); }
                break;
        }
    }

}
