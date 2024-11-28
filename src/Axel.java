import java.util.stream.Stream;

public class Axel {

    // --- Constantes physiques du jeu ---
    public static final double LATERAL_SPEED = 10.0; // Vitesse latérale
    public static final double MAX_JUMP_SPEED = 20.0; // Vitesse de saut
    public static final double MAX_FALL_SPEED = -20.0; // Vitesse maximale de chute
    public static final double GRAVITY_FORCE = 1;   // Force de gravité
    public static final double DIVE_SPEED = 3.0; // Accélération en piqué

    // --- Position et vitesse ---
    private double currentX;    // Position actuelle en X
    private double currentY;    // Position actuelle en Y
    private double velocityX;   // Vitesse horizontale
    private double velocityY;   // Vitesse verticale
    private double nextX;       // Position cible en X
    private double nextY;       // Position cible en Y

    // --- États du personnage ---
    private boolean isFalling;   // En chute
    private boolean isJumping;   // En saut
    private boolean isDiving;    // En piqué
    private boolean movingLeft;  // Déplacement à gauche
    private boolean movingRight; // Déplacement à droite
    private boolean isAlive;     // Personnage vivant ou non

    private int highestAltitude = 0; // Altitude maximale atteinte
    private final Field gameField;

    public Axel(Field field, int startX, int startY) {
        this.gameField = field;
        this.currentX = startX;
        this.currentY = startY;
        this.isAlive = true;
    }

    // --- Mise à jour de la vitesse en fonction des actions ---
    private void calculateVerticalSpeed() {
        if (isJumping && !isFalling) {
            velocityY = MAX_JUMP_SPEED;
            isFalling = true;
        }

        if (isFalling) {
            velocityY -= GRAVITY_FORCE;
            if (isDiving) velocityY -= DIVE_SPEED;
            velocityY = Math.max(velocityY, MAX_FALL_SPEED);
        }
    }

    private void calculateHorizontalSpeed() {
        velocityX = (movingLeft ? -LATERAL_SPEED : 0) + (movingRight ? LATERAL_SPEED : 0);
    }

    public void updateSpeed() {
        calculateVerticalSpeed();
        calculateHorizontalSpeed();

        // Calcul des prochaines positions en fonction de la vitesse
        nextX = currentX + velocityX;
        nextY = currentY + velocityY;
    }

    // --- Vérification des collisions avec un bloc ---
    private void performCollisionWithBlock(Block block) {
        if (nextY > block.getAltitude() || currentX < block.getLeftPosition() || currentX > block.getRightPosition()) {
            isFalling = true; // Chute si on dépasse les limites du bloc
        } else {
            nextY = block.getAltitude();
            velocityY = 0;
            isFalling = false;

            // Mise à jour de l'altitude maximale
            if (nextY > highestAltitude) {
                highestAltitude = (int) nextY;
                gameField.updateCurrentLevel(highestAltitude);
            }

            // Si on dépasse les limites latérales, on retombe
            if (nextX < block.getLeftPosition() || nextX > block.getRightPosition()) {
                isFalling = true;
            }
        }
    }

    public void checkCollision() {
        gameField.getBlocks().stream()
                .filter(block -> block.getAltitude() <= currentY) // on recherche le bloc le plus proche
                .findFirst()
                .ifPresentOrElse(this::performCollisionWithBlock, () -> isFalling = true); // si il y en a pas, c'est qu'il est en train de tomber
    }

    // --- Mise à jour de l'état du personnage ---
    public void update() {
        updateSpeed();
        checkCollision();

        // Mise à jour des positions actuelles
        currentX = nextX;
        currentY = nextY;

        // Vérification de la survie
        if (currentY < gameField.getBottom() - GamePanel.AXEL_HEIGHT) {
            isAlive = false;
        }
    }

    public int getX() {
        return (int) currentX;
    }

    public int getY() {
        return (int) currentY;
    }

    public boolean hasFallen() {
        return !isAlive;
    }

    public int getScore() {
        return highestAltitude - Field.START_ALTITUDE;
    }

    public void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }

    public void setDiving(boolean diving) {
        this.isDiving = diving;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
