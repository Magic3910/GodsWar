package godswar.godswar.Manager.CommandModule;

import java.util.ArrayList;
import java.util.List;

import godswar.godswar.DB.AbilityData;
import godswar.godswar.Utility.PermissionChecker;
import godswar.godswar.Utility.ReturnAbilityName;

import godswar.godswar.utils.base.minecraft.item.builder.ItemBuilder;
import godswar.godswar.utils.library.MaterialX;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Blacklist {

    public static List<Integer> GodCanlist=new ArrayList<>();
    public static List<Integer> HumanCanlist=new ArrayList<>();
    public static List<Integer> MiscCanlist=new ArrayList<>();
    public static List<Integer> Blacklist=new ArrayList<>();
    public static int Canlist;
    public static ItemStack SET_NAME(ItemStack item, String name) {
        ItemMeta meta = (ItemMeta) item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
    private static final ItemStack PREVIOUS_PAGE = new ItemBuilder(MaterialX.ARROW)
            .displayName(ChatColor.AQUA + "이전 페이지")
            .build();

    private static final ItemStack NEXT_PAGE = new ItemBuilder(MaterialX.ARROW)
            .displayName(ChatColor.AQUA + "다음 페이지")
            .build();

    private static final ItemStack QUIT = new ItemBuilder(MaterialX.SPRUCE_DOOR)
            .displayName(ChatColor.AQUA + "나가기")
            .build();

    public static void Module(CommandSender sender) {
        if(PermissionChecker.Sender(sender)) {
            ((Player) sender).openInventory(blackguiMain());
        }
    }
    public static void ModuleGod(Player player, int page) {
        player.openInventory(blackguiGod(page));
    }
    public static void ModuleMan(Player player, int page) {
        player.openInventory(blackguiMan(page));
    }
    public static void ModuleMisc(Player player, int page) {
        player.openInventory(blackguiMisc(page));
    }

    //추가라벨
    private static Inventory blackguiMain() {
        Inventory gui=Bukkit.createInventory(null, 9, ChatColor.BLACK+":: 블랙리스트 ::");
        final ItemStack stack = new ItemStack(Material.BOOK);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + ":: 신 능력 ::");
        stack.setItemMeta(meta);

        final ItemStack stack1 = new ItemStack(Material.BOOK);
        final ItemMeta meta1 = stack1.getItemMeta();
        meta1.setDisplayName(ChatColor.AQUA + ":: 인간 능력 ::");
        stack1.setItemMeta(meta1);
        final ItemStack stack2 = new ItemStack(Material.BOOK);
        final ItemMeta meta2 = stack2.getItemMeta();
        meta2.setDisplayName(ChatColor.AQUA + ":: 기타 능력 ::");
        stack2.setItemMeta(meta2);
        gui.setItem(2, stack);
        gui.setItem(4, stack1);
        gui.setItem(6, stack2);
        return gui;
    }
    public static int currentPage;
    public static int currentPageM;
    public static int currentPageC;
    private static Inventory blackguiGod(int page) {
        Inventory gui=Bukkit.createInventory(null, 54, ChatColor.BLACK+":: 블랙리스트 (신) ::");
        int maxPage = ((AbilityData.GOD_ABILITY_NUMBER) / 36) + 1;
        if (maxPage < page || page < 1) page = 1;
        currentPage = page;
        for(int i = 1;i <= AbilityData.GOD_ABILITY_NUMBER; i++) {
            if (i / 36 == page - 1) {
                final ItemStack stack = new ItemStack(Material.WHITE_WOOL);
                final ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + ReturnAbilityName.name(i));
                stack.setItemMeta(meta);
                if (!Blacklist.contains(i)) {
                    stack.setType(Material.LIME_WOOL);
                } else {
                    stack.setType(Material.RED_WOOL);
                }
                gui.setItem((i % 36)-1, stack);
            }
        }
        if (page > 1) gui.setItem(48, PREVIOUS_PAGE);
        if (page != maxPage) gui.setItem(50, NEXT_PAGE);
        gui.setItem(53, QUIT);
        final ItemStack stack = new ItemStack(Material.PAPER, 1);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§6페이지 §e" + page + " §6/ §e" + maxPage);
        stack.setItemMeta(meta);
        gui.setItem(49, stack);
            return gui;
    }
    private static Inventory blackguiMan(int page) {
        Inventory gui=Bukkit.createInventory(null, 54, ChatColor.BLACK+":: 블랙리스트 (인간) ::");
        int maxPage = ((AbilityData.HUMAN_ABILITY_NUMBER) / 36) + 1;
        if (maxPage < page || page < 1) page = 1;
        currentPageM = page;
        for(int i = 1;i <= AbilityData.HUMAN_ABILITY_NUMBER; i++) {
            if (i / 36 == page - 1) {
                final ItemStack stack = new ItemStack(Material.WHITE_WOOL);
                final ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + ReturnAbilityName.name(i+100));
                stack.setItemMeta(meta);
                if (!Blacklist.contains(i+100)) {
                    stack.setType(Material.LIME_WOOL);
                } else {
                    stack.setType(Material.RED_WOOL);
                }
                gui.setItem((i % 36)-1, stack);
            }
        }
        if (page > 1) gui.setItem(48, PREVIOUS_PAGE);
        if (page != maxPage) gui.setItem(50, NEXT_PAGE);
        gui.setItem(53, QUIT);
        final ItemStack stack = new ItemStack(Material.PAPER, 1);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§6페이지 §e" + page + " §6/ §e" + maxPage);
        stack.setItemMeta(meta);
        gui.setItem(49, stack);
        return gui;
    }
    private static Inventory blackguiMisc(int page) {
        Inventory gui=Bukkit.createInventory(null, 54, ChatColor.BLACK+":: 블랙리스트 (기타) ::");
        int maxPage = ((AbilityData.CITY_ABILITY_NUMBER) / 36) + 1;
        if (maxPage < page || page < 1) page = 1;
        currentPageC = page;
        for(int i = 1;i <= AbilityData.CITY_ABILITY_NUMBER; i++) {
            if (i / 36 == page - 1) {
                final ItemStack stack = new ItemStack(Material.WHITE_WOOL);
                final ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + ReturnAbilityName.name(i+1000));
                stack.setItemMeta(meta);
                if (!Blacklist.contains(i+1000)) {
                    stack.setType(Material.LIME_WOOL);
                } else {
                    stack.setType(Material.RED_WOOL);
                }
                gui.setItem((i % 36)-1, stack);
            }
        }
        if (page > 1) gui.setItem(48, PREVIOUS_PAGE);
        if (page != maxPage) gui.setItem(50, NEXT_PAGE);
        gui.setItem(53, QUIT);
        final ItemStack stack = new ItemStack(Material.PAPER, 1);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§6페이지 §e" + page + " §6/ §e" + maxPage);
        stack.setItemMeta(meta);
        gui.setItem(49, stack);
        return gui;
    }

}
