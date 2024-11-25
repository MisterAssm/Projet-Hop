import java.util.Random;

public class Block {


    private final int x, y;
    private final int width;

    private final int minWidth=50;
    private final int maxWidth=100;


    public Block(int x, int y, int width) {
        this.x = x; // la position horizontal 
        this.y = y; // la postion vertical (altitude)
        this.width = width; // la largeur du bloc  
    }

    // des block de largeur aleatoire 

    // generer un nombre aleatoire dans un intervalle donneé 
    public static int nombreAleatoire  ( int min, int max){
            Random random = new Random();
            return min + random.nextInt(max-min);
    }

    public static Block blockAleatoire (int niveau,int y,int largeurField){
        int width= Block.nombreAleatoire(50-niveau*5,100-niveau*10);
        int x= Block.nombreAleatoire(0,largeurField-width); // pour ne pas depasser la zone
        return new Block (x,y,width);
    }

    public int getWidth (){
        return this.width;
    }

    public int getAltitude (){
        return this.y;
    }

    public  int getPosGauche(){
        return this.x;
    } 

    public int getPosDroite(){
        return this.x+ this.width;
    }



}
