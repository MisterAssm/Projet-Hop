import java.util.ArrayDeque;

public class Field {

    public static final int ALTITUDE_GAP = 80; // ecart d'altitude entre deux blocs
    public static final int START_ALTITUDE = 40; // altitude de premier bloc

    public static final int[] levelAltitude = new int[]{80, 800, 2000, 3200, 4800, 7200};
    public static final int maxLevel = levelAltitude.length;

    public final int width, height; // largeur et hauteur de terrain
    private int bottom, top;  // bottom and top altitude
    private int currentLevel = 0;
    private final ArrayDeque<Block> blocks; // permet d'agir comme une file d'attente

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.top = height;
        this.bottom = 0;
        this.blocks = new ArrayDeque<>();

        // Ajout du premier block 
        this.blocks.addFirst(new Block(this.width - Block.INITIAL_MAX_WIDTH, START_ALTITUDE, Block.INITIAL_MAX_WIDTH));

        // deuxieme bloc a partir d une altitude de 40+80
        for (int altitude = START_ALTITUDE + ALTITUDE_GAP; altitude < this.height; altitude += ALTITUDE_GAP) {
            this.blocks.addFirst(Block.randomBlock(this.currentLevel, altitude, this.width));
            altitude += ALTITUDE_GAP;
        }
    }

    public void updateCurrentLevel(int altitude) {
        if (currentLevel < maxLevel && altitude > levelAltitude[currentLevel]) {
            currentLevel++; // TODO: Faire une animation ?
        }
    }
}
