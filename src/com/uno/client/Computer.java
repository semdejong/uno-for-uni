package com.uno.client;

import java.util.Random;

public class Computer extends Player {
    public Computer() {
        super("Computer player" + new Random().nextInt(99999));
    }

    // Will be implemented after creating some game logic as it is not completely clear how the computer will play
}
