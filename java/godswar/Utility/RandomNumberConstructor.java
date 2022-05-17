package com.magical.Utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.magical.Theomachy.Theomachy;
import com.magical.Theomachy.DB.AbilityData;
import com.magical.Manager.CommandModule.Blacklist;

public class RandomNumberConstructor
{
    static public List<Integer> CanlistDump=new ArrayList<Integer>();
    public static int[] ndl(){
        Random random=new Random();
        int[] rn;
        rn=new int[CanlistDump.size()];
        Object[] rn1= CanlistDump.toArray();
        for(int i=0;i<rn1.length;i++) {
            rn[i]=(Integer) rn1[i];
        }
        if (CanlistDump.size() == 1) {
            return rn;
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
        Theomachy.log.info(String.valueOf(sb));
        return rn;
    }
    public static int[] nonDuplicate()
    {
        List<Integer> Canlist=new ArrayList<Integer>();
        Random random=new Random();
        CanlistDump = new ArrayList<Integer>();
        for(int e=0;e<AbilityData.GOD_ABILITY_NUMBER;e++){
            if(!Blacklist.Blacklist.contains(e+1)) {
                Canlist.add(e+1);
                CanlistDump.add(e+1);
            }
        } int a=101;
        for(int e=0;e<AbilityData.HUMAN_ABILITY_NUMBER;e++) {
            if(!Blacklist.Blacklist.contains(a)) {
                Canlist.add(a);
                CanlistDump.add(a);
            } a++;
        }int b=1001;
        for(int e=0;e<AbilityData.CITY_ABILITY_NUMBER;e++) {
            if(!Blacklist.Blacklist.contains(b)){
                Canlist.add(b);
                CanlistDump.add(b);
            } b++;
        }

        Blacklist.Canlist=Canlist.size();
        int[] rn;
        rn=new int[Canlist.size()];

        Object[] rn1= Canlist.toArray();

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
        Theomachy.log.info(String.valueOf(sb));
        return rn;
    }
}
