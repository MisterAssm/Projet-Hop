package fr.hop.entities;

import java.util.Random;

public class Block {

    public static final int INITIAL_MIN_WIDTH = 50;
    public static final int INITIAL_MAX_WIDTH = 100;

    private final int x, y;
    private final int width;

    private static final Random random = new Random();

    public Block(int x, int y, int width) {
        this.x = x; // la position horizontal 
        this.y = y; // la postion vertical (altitude)
        this.width = width; // la largeur du bloc  
    }

    // générer un nombre aléatoire dans un intervalle (inclus)
    public static int randomNumber(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static Block randomBlock(int niveau, int y, int largeurField) {
        int width = Block.randomNumber(INITIAL_MIN_WIDTH - niveau * 5, INITIAL_MAX_WIDTH - niveau * 10); // valeurs dépendant du niveau
        int x = Block.randomNumber(0, largeurField - width); // pour ne pas depasser la zone
        return new Block(x, y, width);
    }

    public int getWidth() {
        return this.width;
    }

    public int getAltitude() {
        return this.y;
    }

    public int getLeftPosition() {
        return this.x;
    }

    public int getRightPosition() {
        return this.x + this.width;
    }

}

