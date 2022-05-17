package com.magical.Utility;

import com.magical.Timer.CoolTime;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Skill
{
    public static void Use(Player player, Material material, int stack, int abilityCase, int coolTime)
    {
        player.getInventory().removeItem(new ItemStack(material, stack));
        if (coolTime>0)
            switch(abilityCase)
            {
                case 0:
                    CoolTime.COOL0.put(player.getName(), coolTime);
                    break;
                case 1:
                    CoolTime.COOL1.put(player.getName(), coolTime);
                    break;
                case 2:
                    CoolTime.COOL2.put(player.getName(), coolTime);
                    break;
            }
        T_Message.Skill_Used(player, abilityCase);
    }
}


