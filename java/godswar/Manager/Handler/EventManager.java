package godswar.Manager.Handler;

import com.magical.Ability.Ability;
import com.magical.Timer.DiamondProtectingTimer;
import com.magical.Utility.GambManager;
import com.magical.Utility.ReturnAbilityName;
import godswar.Manager.CommandManager;
import godswar.godswar.GodsWar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static com.magical.Ability.Misc.Themis.*;
import static com.magical.Manager.CommandModule.Blacklist.*;

public class EventManager implements Listener
{
    @EventHandler
    public static void onProjectileLaunch(ProjectileLaunchEvent event)
    {
        if (event.getEntity() instanceof Arrow)
        {
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getShooter() instanceof Player)
            {
                Player player = (Player) arrow.getShooter();
                Ability ability = GameData.PlayerAbility.get(player.getName());
                if (ability != null && ability.abilityCode ==117)
                    ability.T_Passive(event, player);
            }
        }
    }

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        if(DiamondSet.choosingTeam!=null){
            if(!event.getClickedBlock().getType().equals(Material.AIR)){
                DiamondSet.sender.sendMessage(ChatColor.DARK_AQUA+DiamondSet.choosingTeam+ChatColor.WHITE+
                        " 팀의 다이아몬드 블럭이 이곳으로 설정되었습니다.");
                GameData.DiamondLocation.put(event.getClickedBlock().getLocation(), DiamondSet.choosingTeam);
                DiamondSet.choosingTeam=null;
            }else{
                DiamondSet.sender.sendMessage("선택하신 위치에는 블럭이 아예 없습니다.");
            }

        }

        if (GameHandler.Start)
        {
            String playerName = event.getPlayer().getName();
            Ability ability= GameData.PlayerAbility.get(playerName);
            if (ability != null && ability.activeType)
                ability.T_Active(event);
            if(ability != null)
                ability.T_Check(event);
        }
    }

    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event)
    {
        if (GameHandler.Start)
        {
            if (event.getEntity() instanceof Player)
            {
                String playerName = (event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).T_Passive(event);
            }
            if (event.getCause() == DamageCause.LIGHTNING && event.getEntity() instanceof LivingEntity)
            {
                LivingEntity le = (LivingEntity) event.getEntity();
                le.setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public static void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        try
        {
            if (GameHandler.Start)
            {
                if (event.getEntity() instanceof Player &&
                        event.getDamager() instanceof Player)
                {
                    String key1 = (event.getEntity()).getName();
                    String key2 = (event.getDamager()).getName();
                    Ability ability1 = GameData.PlayerAbility.get(key1);
                    Ability ability2 = GameData.PlayerAbility.get(key2);
                    if (ability1 != null)
                        ability1.T_Passive(event);
                    if (ability2 != null)
                        ability2.T_Passive(event);
                }
                else if (event.getDamager() instanceof Arrow &&
                        event.getEntity() instanceof Player)
                {
                    Arrow arrow = (Arrow) event.getDamager();
                    if (arrow.getShooter() instanceof Player)
                    {
                        Player player = (Player) arrow.getShooter();
                        String key = player.getName();
                        Ability ability = GameData.PlayerAbility.get(key);
                        if (ability != null && ability.abilityCode == 6 ||
                                ability.abilityCode == 101 || ability.abilityCode==136)
                            ability.T_Passive(event);
                    }
                }else if(event.getDamager() instanceof Snowball
                        &&event.getEntity() instanceof Player){
                    Snowball snow=(Snowball)event.getDamager();
                    if(snow.getShooter() instanceof Player){
                        Player player=(Player)snow.getShooter();
                        Ability ability=GameData.PlayerAbility.get(player.getName());
                        if(ability != null && ability.abilityCode==124)
                            ability.T_PassiveSnow(event);
                    }
                }
            }
        }
        catch(Exception e)
        {
            Theomachy.log.info(""+e.getLocalizedMessage());
        }
    }
    public static ArrayList<Ability> PlayerDeathEventList = new ArrayList<>();
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event)
    {
        if (GameHandler.Start)
        {
            for (Ability e : PlayerDeathEventList)
                e.T_Passive(event);
            Player player = event.getEntity();
            Ability ability = GameData.PlayerAbility.get(player.getName());
            if (ability != null)
                if (ability.abilityCode == 106 || ability.abilityCode == 2 || ability.abilityCode == 124)
                    ability.T_Passive(event);
            Player entity= event.getEntity();
            Player killer= entity.getKiller();
            if (killer != null) {
                if (killsMap.containsKey(killer.getUniqueId())) {
                    addKills(killer);
                }else {killsMap.put(killer.getUniqueId(),1);}
            }
            onSilentEnd(entity);
        }
    }
    @EventHandler
    public static void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (GameHandler.Start)
        {
            if (event.getEntity() instanceof Player)
            {
                String playerName = (event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).T_Passive(event);
            }
        }
    }
    @EventHandler
    public static void onEntityRegainHealth(EntityRegainHealthEvent event)
    {
        if (GameHandler.Start)
        {
            if (event.getEntity() instanceof Player)
            {
                String playerName = (event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).T_Passive(event);
            }
        }
    }
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event)
    {
        if (GameHandler.Start)
        {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null)
                ability.T_Passive(event);
        }
    }


    @EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent event)
    {
        if (GameHandler.Start)
        {
            Player player = event.getPlayer();
            if (Theomachy.IGNORE_BED)
            {
                if (GameData.PlayerTeam.containsKey(player.getName()))
                {
                    String teamName=GameData.PlayerTeam.get(player.getName());
                    Location respawnLocation = GameData.SpawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            }
            else
            {
                if (!event.isBedSpawn() && GameData.PlayerTeam.containsKey(player.getName()))
                {
                    String teamName=GameData.PlayerTeam.get(player.getName());
                    Location respawnLocation = GameData.SpawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            }
            Ability ability = GameData.PlayerAbility.get(player.getName());
            if (ability != null)
            {
                if (ability.buffType)
                    ability.buff();
                if (ability.abilityCode == 2 || ability.abilityCode == 122)
                    ability.T_Passive(event);
            }

        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event)
    {
        if (GameHandler.Start)
        {
            try {
                Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
                if (ability != null && ability.abilityCode == 118)
                    ability.T_Passive(event);
            } catch (NullPointerException e) {

            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (GameHandler.Start)
        {
            try {
                Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
                if (ability != null && ability.abilityCode == 118 || ability.abilityCode==137)
                    ability.T_Passive(event);
            } catch (Exception e) {
            }
        }
    }

    @EventHandler
    public static void onPlayerKick(PlayerKickEvent event)
    {
        Theomachy.log.info(event.getReason());
    }

    public static int diamond;

    @EventHandler
    public static void onBlockExplode(BlockExplodeEvent event){
        if(GodsWar.PROTECT){
           for(Block b:event.blockList()){
               if(b.getType().equals(Material.DIAMOND_BLOCK)){
                   diamond=Bukkit.getScheduler().scheduleSyncDelayedTask(CommandManager.main, new DiamondProtectingTimer(b), 20);
                   for(Entity e:b.getWorld().getNearbyEntities(b.getLocation(), 10, 10, 10)){
                       if(e instanceof Item){
                           if(e.getType().equals(Material.DIAMOND_BLOCK)){
                               e.remove();
                           }
                       }
                   }
               }
           }
        }
    }

    @EventHandler
    public static void onEntityExplode(EntityExplodeEvent event)
    {
        Entity entity = event.getEntity();
        if (GameHandler.Start && entity != null && entity.getType() == EntityType.FIREBALL)
            event.blockList().clear();

        if(GameHandler.Start){
            for(Player p:Bukkit.getOnlinePlayers()){
                Ability ability=GameData.PlayerAbility.get(p.getName());
                if(ability!=null&&ability.abilityCode==120){
                    ability.T_Passive(event);
                }
            }
        }

    }

    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent event) {

        if(GameHandler.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == 128)
                ability.T_Passive(event);
        }

    }

    @EventHandler
    public static void onPlayerChat(PlayerChatEvent event){
        if(GameHandler.Start){
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if(ability != null && (ability.abilityCode==133 || ability.abilityCode==134)){
                ability.T_Passive(event);
            }
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if(!ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(":: 블랙리스트 ::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(":::::::: 능력 정보 ::::::::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(":::::: 설정 ::::::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (신) ::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (인간) ::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (기타) ::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(":::::::: 도박 ::::::::") &&
                !ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(":::::::: 팁 ::::::::"))
            return;
        event.setCancelled(true);
        try {
            ItemStack wool=event.getCurrentItem();
            ItemMeta meta=wool.getItemMeta();

            if(ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 ::")) {
                String y = meta.getDisplayName();
                if (y.contains(":: 신 능력 ::")) {
                    ModuleGod((Player) event.getWhoClicked(), 1);
                }
                if (y.contains(":: 인간 능력 ::")) {
                    ModuleMan((Player) event.getWhoClicked(), 1);
                }
                if (y.contains(":: 기타 능력 ::")) {
                    ModuleMisc((Player) event.getWhoClicked(), 1);
                }

            }
            if(ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (신) ::")) {
                final ItemStack clicked = event.getCurrentItem();
                if (event.getSlot() + 1 == 54) {
                    com.magical.Manager.CommandModule.Blacklist.Module(event.getWhoClicked());
                }
                if(wool.getType().equals(Material.LIME_WOOL)) {
                    wool.setType(Material.RED_WOOL);
                    int num=event.getSlot()+1+((currentPage-1)*36);
                    Blacklist.add(num);
                    Bukkit.broadcastMessage(ChatColor.GREEN+"【 알림 】 "+ChatColor.WHITE+ ReturnAbilityName.name(num) +" 이(가) "+ChatColor.RED+"블랙리스트"+ChatColor.WHITE+"에 등록되었습니다.");
                    return;
                }else if(wool.getType().equals(Material.RED_WOOL)) {
                    wool.setType(Material.LIME_WOOL);
                    int num=event.getSlot()+1+((currentPage-1)*36);
                    Object o=num;
                    Blacklist.remove(o);
                    Bukkit.broadcastMessage(ChatColor.GREEN+"【 알림 】 "+ChatColor.WHITE+ReturnAbilityName.name(num)+" 이(가) "+ChatColor.RED+"블랙리스트"+ChatColor.WHITE+"에서 벗어났습니다.");
                    return;
                }



                    if (clicked != null && !clicked.getType().equals(Material.AIR) && clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
                        final String stripName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
                        if (clicked.getType().equals(Material.ARROW)) {
                            if (stripName.equals("이전 페이지")) {
                                ModuleGod((Player) event.getWhoClicked(), currentPage - 1);
                                return;
                            } else if (stripName.equals("다음 페이지")) {
                                ModuleGod((Player) event.getWhoClicked(), currentPage + 1);
                                return;
                            }
                        }
                    }


            }

            if(ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (인간) ::")) {
                final ItemStack clicked2 = event.getCurrentItem();
                if (event.getSlot() + 1 == 54) {
                    com.magical.Manager.CommandModule.Blacklist.Module(event.getWhoClicked());
                }
                if(wool.getType().equals(Material.LIME_WOOL)) {
                    wool.setType(Material.RED_WOOL);
                    int num=event.getSlot()+101+((currentPageM-1)*36);
                    Blacklist.add(num);
                    Bukkit.broadcastMessage(ChatColor.GREEN+"【 알림 】 "+ChatColor.WHITE+ ReturnAbilityName.name(num) +" 이(가) "+ChatColor.RED+"블랙리스트"+ChatColor.WHITE+"에 등록되었습니다.");
                    return;
                }else if(wool.getType().equals(Material.RED_WOOL)) {
                    wool.setType(Material.LIME_WOOL);
                    int num=event.getSlot()+101+((currentPageM-1)*36);
                    Object o=num;
                    Blacklist.remove(o);
                    Bukkit.broadcastMessage(ChatColor.GREEN+"【 알림 】 "+ChatColor.WHITE+ReturnAbilityName.name(num)+" 이(가) "+ChatColor.RED+"블랙리스트"+ChatColor.WHITE+"에서 벗어났습니다.");
                    return;
                }



                if (clicked2 != null && !clicked2.getType().equals(Material.AIR) && clicked2.hasItemMeta() && clicked2.getItemMeta().hasDisplayName()) {
                    final String stripName = ChatColor.stripColor(clicked2.getItemMeta().getDisplayName());
                    if (clicked2.getType().equals(Material.ARROW)) {
                        if (stripName.equals("이전 페이지")) {
                            ModuleGod((Player) event.getWhoClicked(), currentPageM - 1);
                            return;
                        } else if (stripName.equals("다음 페이지")) {
                            ModuleGod((Player) event.getWhoClicked(), currentPageM + 1);
                            return;
                        }
                    }
                }
            }
            if(ChatColor.stripColor(event.getView().getTitle()).equals(":: 블랙리스트 (기타) ::")) {
                final ItemStack clicked3 = event.getCurrentItem();
                if (event.getSlot() + 1 == 54) {
                    com.magical.Manager.CommandModule.Blacklist.Module(event.getWhoClicked());
                }
                if (wool.getType().equals(Material.LIME_WOOL)) {
                    wool.setType(Material.RED_WOOL);
                    int num = event.getSlot() + 1001 + ((currentPageC-1) * 36);
                    Blacklist.add(num);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + ReturnAbilityName.name(num) + " 이(가) " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에 등록되었습니다.");
                    return;
                } else if (wool.getType().equals(Material.RED_WOOL)) {
                    wool.setType(Material.LIME_WOOL);
                    int num = event.getSlot() + 1001 + ((currentPageC-1) * 36);
                    Object o = num;
                    Blacklist.remove(o);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + ReturnAbilityName.name(num) + " 이(가) " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에서 벗어났습니다.");
                    return;
                }


                if (clicked3 != null && !clicked3.getType().equals(Material.AIR) && clicked3.hasItemMeta() && clicked3.getItemMeta().hasDisplayName()) {
                    final String stripName = ChatColor.stripColor(clicked3.getItemMeta().getDisplayName());
                    if (clicked3.getType().equals(Material.ARROW)) {
                        if (stripName.equals("이전 페이지")) {
                            ModuleGod((Player) event.getWhoClicked(), currentPageC - 1);
                            return;
                        } else if (stripName.equals("다음 페이지")) {
                            ModuleGod((Player) event.getWhoClicked(), currentPageC + 1);
                            return;
                        }
                    }
                }
            }

            if(ChatColor.stripColor(event.getView().getTitle()).equals(":::::::: 도박 ::::::::")) {

                Player p=(Player)event.getWhoClicked();

                switch(ChatColor.stripColor(wool.getItemMeta().getDisplayName())) {
                    case "가챠 ★ 가챠":
                        GambManager.Gamb(p);
                        break;
                }
            }

            if(ChatColor.stripColor(event.getView().getTitle()).equals(":::::: 설정 ::::::")) {

                GUISetting.guiListener(wool);

            }
        }catch(NullPointerException e) {}

    }

}