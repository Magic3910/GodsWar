package com.magical.Utility;

public class loop implements Runnable{
    private int count=1;

    @Override
    public void run() {
        count++;
        return;
    }
}
