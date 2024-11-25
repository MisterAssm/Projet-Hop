import java.util.ArrayDeque;

public class Field {



    public static final int ALTITUDE_GAP = 80; //ecart d'altitude entre deux blocs
    public static final int START_ALTITUDE = 40; //altitude de premier bloc

    public final int width;
    public final int height;  //largeur et hauteur de terrain 

    private int bottom, top;  //bottom and top altitude

    private final ArrayDeque<Block> blocks ;
    private int level=0;
    private static final int maxLevel=6;



    public Field(int width, int height) {
        this.width = width; 
        this.height = height;
        this.top=height;
        this.blocks =new ArrayDeque<>();

        // Ajout du premier block 
        this.blocks.addFirst(new Block(this.width-100,40,100));

        // deuxieme bloc a partir d une altitude de 40+80
        for(int altitude=120; altitude<this.height;altitude+=80){
            this.blocks.addFirst(Block.blockAleatoire(this.level, altitude,this.width));
        }
    }

    public void update() { }
}
