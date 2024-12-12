package fr.hop.entities;

public class BonusBlock extends Block {

    private final int direction; // Direction du mouvement : 1 pour droite, -1 pour gauche
    private static final int MOVE_SPEED = 2;

    public BonusBlock(int x, int y, int width, int direction) {
        super(x, y, width);
        this.direction = direction;
    }

    public BonusBlock updatePosition(int fieldWidth) {
        int newX = getLeftPosition() + (direction * MOVE_SPEED);
        if (newX < 0 || newX + getWidth() > fieldWidth) {
            return new BonusBlock(getLeftPosition(), getAltitude(), getWidth(), -direction);
        }
        return new BonusBlock(newX, getAltitude(), getWidth(), direction);
    }
}
