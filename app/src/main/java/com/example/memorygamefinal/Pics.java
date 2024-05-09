package com.example.memorygamefinal;

import java.util.Random;

public class Pics {
    private int[] Pics;

    public Pics() {

        Random rd = new Random();
        int num;
        this.Pics = new int[8];
        int[] usedPics = new int[4];
        for (int i = 0; i < 8; i++) {
            num = rd.nextInt(4);
            while (usedPics[num] == 2)
                num = rd.nextInt(4);
            usedPics[num]++;
            this.Pics[i] = num + 1;
        }
    }

    public int[] getPics() {
        return this.Pics;
    }

    public void newOrder() {
        Random rd = new Random();
        int num;
        int[] usedPics = new int[4];
        for (int i = 0; i < 8; i++) {
            num = rd.nextInt(4);
            while (usedPics[num] == 2)
                num = rd.nextInt(4);
            usedPics[num]++;
            this.Pics[i] = num + 1;
        }
    }
}