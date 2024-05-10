package com.example.memorygamefinal;

import java.util.Random;

public class Pics {
    // Member variable to store pictures
    private int[] Pics;

    // Constructor to initialize Pics array with random values
    public Pics() {
        Random rd = new Random(); // Random object to generate random numbers
        int num; // Variable to store random number
        this.Pics = new int[8]; // Initialize Pics array with size 8
        int[] usedPics = new int[4]; // Array to keep track of used pictures
        // Loop to fill Pics array with random picture indices
        for (int i = 0; i < 8; i++) {
            num = rd.nextInt(4); // Generate a random number between 0 and 3
            // Ensure each picture is used only twice
            while (usedPics[num] == 2)
                num = rd.nextInt(4); // Generate a new random number if the current one is already used twice
            usedPics[num]++; // Increment the count of the chosen picture
            this.Pics[i] = num + 1; // Store the picture index in the Pics array
        }
    }

    // Method to get the Pics array
    public int[] getPics() {
        return this.Pics;
    }

    // Method to generate a new order of pictures
    public void newOrder() {
        Random rd = new Random(); // Random object to generate random numbers
        int num; // Variable to store random number
        int[] usedPics = new int[4]; // Array to keep track of used pictures
        // Loop to generate a new order of pictures
        for (int i = 0; i < 8; i++) {
            num = rd.nextInt(4); // Generate a random number between 0 and 3
            // Ensure each picture is used only twice
            while (usedPics[num] == 2)
                num = rd.nextInt(4); // Generate a new random number if the current one is already used twice
            usedPics[num]++; // Increment the count of the chosen picture
            this.Pics[i] = num + 1; // Store the picture index in the Pics array
        }
    }
}
