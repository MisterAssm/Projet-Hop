package fr.hop.game;

import fr.hop.entities.Block;

import java.util.ArrayDeque;
import java.util.Comparator;

public class Field {

    public static final int ALTITUDE_GAP = 80; // ecart d'altitude entre deux blocs
    public static final int START_ALTITUDE = 40; // altitude de premier bloc

    public static final int[] levelAltitude = new int[]{80, 800, 2000, 3200, 4800, 7200};
    public static final int maxLevel = levelAltitude.length;

    private int bottom, top;  // bottom and top altitude
    private int currentLevel = 0;

    public final int width, height; // largeur et hauteur de terrain
    private final ArrayDeque<Block> blocks; // permet d'agir comme une file d'attente

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.top = height;
        this.bottom = 0;
        this.blocks = new ArrayDeque<>();

        // Ajout du premier block
        this.blocks.addFirst(new Block((this.width / 2) - (Block.INITIAL_MAX_WIDTH / 2), Field.START_ALTITUDE - 15, Block.INITIAL_MAX_WIDTH));

        // deuxieme bloc a partir d une altitude de 40+80
        for (int altitude = START_ALTITUDE + ALTITUDE_GAP; altitude < this.height; altitude += ALTITUDE_GAP) {
            addBlockAtAltitude(altitude);
            altitude += ALTITUDE_GAP;
        }
    }

    public void updateCurrentLevel(int altitude) {
        if (currentLevel < maxLevel && altitude > levelAltitude[currentLevel]) {
            currentLevel++; // TODO: Faire une animation ?
        }
    }

    public void update() {
        this.bottom += currentLevel;
        this.top += currentLevel;

        blocks.removeIf(block -> block.getAltitude() < this.bottom);

        blocks.stream().max(Comparator.comparingInt(Block::getAltitude)).ifPresent(highestBlock -> {
            if (this.top - highestBlock.getAltitude() >= ALTITUDE_GAP)
                addBlockAtAltitude(highestBlock.getAltitude() + ALTITUDE_GAP);
        });

    }

    private void addBlockAtAltitude(int altitude) {
        this.blocks.addFirst(Block.randomBlock(this.currentLevel, altitude, this.width));
    }

    public ArrayDeque<Block> getBlocks() {
        return blocks;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
