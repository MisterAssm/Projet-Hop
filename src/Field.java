public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    public final int width, height;
    private int bottom, top; // bottom and top altitude

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update() { }
}
