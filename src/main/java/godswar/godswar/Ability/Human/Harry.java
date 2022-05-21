package godswar.godswar.Ability.Human;

import godswar.godswar.Utility.CoolTimeChecker;
import godswar.godswar.Utility.GetPlayerList;
import godswar.godswar.Utility.PlayerInventory;
import godswar.godswar.Utility.Skill;
import godswar.godswar.Ability.Ability;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Harry extends Ability {

    private final static String[] des= {
            "다양한 마법 스펠을 능숙하게 사용합니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"기초 마법",
            "채팅으로 주문을 입력하여 마법을 사용합니다.",
            ChatColor.RED+"【고급】 "+ChatColor.WHITE+"고급 마법",
            "어려운 마법이라 확률에 따라 성공합니다.",
            "스펠 시전 방식은 기초 마법과 동일합니다.",
            "게임이 시작되면 헤르미온느에게서 빌려온",
            "마법 스펠 암기장이 제공됩니다.",
            "헤르미온느보다 마법을 잘 사용하므로",
            "책에 적힌 확률보다 높은 확률로 발동됩니다."};

    private String abilitytarget=null;
    private final List<String> aspell=new ArrayList<>();
    private final List<String> bspell=new ArrayList<>();

    public Harry(String playerName)
    {
        super(playerName,"해리포터", 134, true, false, false, des);

        this.cool1=5;
        this.cool2=10;
        this.sta1=5;
        this.sta2=8;

        this.rank=4;

        aspell.add("루모스"); aspell.add("Lumos"); aspell.add("녹스"); aspell.add("Nox");
        aspell.add("봄바르다"); aspell.add("Bombarda"); aspell.add("옵스큐로"); aspell.add("Obscuro");
        bspell.add("스투페파이"); bspell.add("Stupefy");
        bspell.add("엑스펠리아무스"); bspell.add("Expelliarmus");
        bspell.add("아바다 케다브라"); bspell.add("Avada Kedavra");

    }

    @Override
    public void T_Passive(PlayerChatEvent event) {
        Player p=event.getPlayer();

        if(this.aspell.contains(event.getMessage())){
            if(PlayerInventory.ItemCheck(p, co, sta1) && CoolTimeChecker.Check(p, 1)){
                switch(event.getMessage()){
                    case "루모스": case "Lumos":
                        Skill.Use(p, co, sta1, 1, cool1);
                        p.getWorld().setTime(1000); break;
                    case "녹스": case "Nox":
                        Skill.Use(p, co, sta1, 1, cool1);
                        p.getWorld().setTime(18000); break;
                    case "봄바르다": case "Bombarda":
                        Skill.Use(p, co, sta1, 1, cool1);
                        p.getWorld().createExplosion(p.getLocation(), 1.0f); break;
                    case "옵스큐로": case "Obscuro":
                        List<Player> target= GetPlayerList.getNearByNotTeamMembers(p, 5, 5, 5);
                        if(target.isEmpty()){
                            p.sendMessage("「옵스큐로」를 적용할 사람이 없습니다.");
                            return;
                        }
                        Skill.Use(p, co, sta1, 1, cool1);
                        p.sendMessage(ChatColor.GRAY+"옵스큐로! "+ChatColor.WHITE+"마법이 발동되었습니다!");
                        for(Player e: target){
                            e.sendMessage(ChatColor.GRAY+"옵스큐로!"+ChatColor.WHITE+" 시야 제한 마법에 걸렸습니다!");
                            e.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 20*5,0)));
                        }
                        break;
                }
            }
        }if(this.bspell.contains(event.getMessage())){
            if(PlayerInventory.ItemCheck(p, co, sta2) && CoolTimeChecker.Check(p, 2)){
                switch(event.getMessage()){
                    case "스투페파이": case "Stupefy":
                        Skill.Use(p, co, sta2, 2, cool2);
                        Stupefy(p); break;
                    case "엑스펠리아무스": case "Expelliarmus":
                        Skill.Use(p, co, sta2, 2, cool2);
                        Expelliarmus(p); break;
                    case "아바다 케다브라": case "Avada Kedavra":
                        Skill.Use(p, co, sta2, 2, cool2);
                        AvadaKedavra(p); break;
                }
            }
        }

    }

    private void Stupefy(Player p){
        Random r=new Random();
        List<Player> list=GetPlayerList.getNearByNotTeamMembers(p, 10, 10, 10);
        if(list.isEmpty()){
            p.sendMessage("「스투페파이」를 사용할 대상이 없습니다.");
            return;
        }
        for(Player e:list){
            if(r.nextBoolean()){
                e.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getLocation(), 1);
                e.sendMessage(ChatColor.RED+"스투페파이!"+ChatColor.WHITE+" 기절 마법에 걸렸습니다!");
                e.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 20*8,1)));
            }
        }
    }

    private void Expelliarmus(Player p){
        Random r=new Random();
        List<Player> list=GetPlayerList.getNearByNotTeamMembers(p, 10, 10, 10);
        if(abilitytarget==null){
            p.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
            return;
        }

        if(Bukkit.getPlayer(abilitytarget)==null){
            p.sendMessage("타깃으로 등록하신 분이 지금은 서버에 없는 것 같습니다.");
        }else{
            Player target=Bukkit.getPlayer(abilitytarget);
            if(!list.contains(target)){
                p.sendMessage("「엑스펠리아무스」를 사용할 대상이 없습니다.");
            }else{
                if(r.nextInt(100)<=24){
                    p.sendMessage(ChatColor.DARK_AQUA+"엑스펠리아무스! "+ChatColor.WHITE+"마법에 성공했습니다!");
                    ItemStack[] temp=target.getInventory().getArmorContents();

                    target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 1);
                    target.sendMessage(ChatColor.DARK_AQUA+"엑스펠리아무스!"+ChatColor.WHITE+" 무장이 해제되었습니다!");

                    target.setItemInHand(new ItemStack(Material.AIR));
                    target.getInventory().setHelmet(new ItemStack(Material.AIR));
                    target.getInventory().setChestplate(new ItemStack(Material.AIR));
                    target.getInventory().setLeggings(new ItemStack(Material.AIR));
                    target.getInventory().setBoots(new ItemStack(Material.AIR));

                    for(ItemStack i:temp){
                        if(i!=null)
                            target.getInventory().addItem(i);
                    }
                }else{
                    p.sendMessage("아차.. 마법에 실패했습니다.");
                }
            }
        }

    }
    @Override
    public void T_Passive(PlayerRespawnEvent event) {
        this.conditionSet();
    }
    private void AvadaKedavra(Player p){
        Random r=new Random();
        List<Player> list=GetPlayerList.getNearByNotTeamMembers(p, 10, 10, 10);
        if(abilitytarget==null){
            p.sendMessage("목표를 설정해주십시오. (목표 설정법: /x <목표>)");
            return;
        }
        Player target=Bukkit.getPlayer(abilitytarget);

        if(target==null){
            p.sendMessage("타깃으로 등록하신 분이 지금은 서버에 없는 것 같습니다.");
        }else{
            if(!list.contains(target)){
                p.sendMessage("「아바다 케다브라」를 사용할 대상이 없습니다.");
            }else{
                if(r.nextInt(100)<=19){
                    p.sendMessage(ChatColor.DARK_GREEN+"아바다 케다브라!"+ChatColor.WHITE+" 마법에 성공했습니다!");
                    target.setHealth(0);

                    target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 1);
                    target.sendMessage(ChatColor.DARK_GREEN+"아바다 케다브라!"+ChatColor.WHITE+" 죽고 말았습니다...");
                }else{
                    p.sendMessage("아차.. 마법에 실패했습니다.");
                }
            }
        }

    }

    @Override
    public void conditionSet() {
        ItemStack book=new ItemStack(Material.WRITTEN_BOOK);
        BookMeta m=(BookMeta) book.getItemMeta();
        m.setTitle("마법 스펠 암기장");
        m.setAuthor("헤르미온느 진 그레인저");

        m.addPage(ChatColor.BOLD+"기초 마법 일람\n"+
                "루모스(Lumos): 낮으로 만든다.\n"+
                "녹스(Nox): 밤으로 만든다.\n"+
                "봄바르다(Bombarda): 작은 폭발을 일으킨다.\n"+
                "옵스큐로(Obscuro): 주변 적들에게 블라인드를 준다.\n");
        m.addPage(ChatColor.BOLD+"고급 마법 일람 1\n"+
                ChatColor.ITALIC+"스투페파이(Stupefy)\n"+
                ChatColor.BLACK+"기절 마법. 주변에 있는 적들에게 "+
                "구속을 걸 수 있기는 한데 사람마다 "+
                "50%의 확률로 걸린다. "+
                "조금 더 연습하는 것이 좋겠다.");
        m.addPage(ChatColor.BOLD+"고급 마법 일람 2\n"+
                ChatColor.ITALIC+"엑스펠리아무스(Expelliarmus)\n"+
                ChatColor.BLACK+"무장 해제 마법. /x <이름>을 통해 미리 등록한 사람이 주변에 있을 때 사용 가능하다. "+
                "그 사람이 들고 있는 물건을 없애고 "+
                "입고 있는 갑옷을 벗길 수 있다. "+
                "각각의 사람마다 20%의 확률로 걸린다.");
        m.addPage(ChatColor.BOLD+"고급 마법 일람 3\n"+
                ChatColor.ITALIC+"아바다 케다브라(Avada Kedavra)\n"+
                ChatColor.BLACK+"살인 마법. 매드아이 무디 교수가 알려준 "+
                "스펠이다. 용서 받을 수 없는 저주에 해당하며 "+
                "이름을 말할 수 없는 자와 대면했을 때 빼고는 "+
                "사용해서는 안될 것 같다... "+
                "/x <이름>을 통해 미리 등록한 사람이 주변에 있을 때 사용 가능하다. "+
                "15%의 확률로 그 사람은 즉사한다고 한다.");

        m.setGeneration(BookMeta.Generation.COPY_OF_ORIGINAL);

        book.setItemMeta(m);

        Bukkit.getPlayer(playerName).getInventory().addItem(book);

    }

    @Override
    public void targetSet(CommandSender sender, String targetName) {

        if (!playerName.equals(targetName))
        {
            this.abilitytarget = targetName;
            sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
        }
        else
            sender.sendMessage("자기 자신을 목표로 등록 할 수 없습니다.");

    }

}
