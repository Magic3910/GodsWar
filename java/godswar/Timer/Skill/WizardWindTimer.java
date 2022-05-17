package com.magical.Timer.Skill;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WizardWindTimer implements Runnable
{
    final private List<Player> targetList;
    final private Vector v;

    public WizardWindTimer(List<Player> targetList, Vector v)
    {
        this.targetList=targetList;
        this.v=v;
    }

    public void run()
    {
        for (Player e : targetList)
            e.setVelocity(v);
    }
}
