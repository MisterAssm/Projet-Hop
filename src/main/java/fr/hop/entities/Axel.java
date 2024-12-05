package fr.hop.entities;

import fr.hop.game.Field;
import fr.hop.ui.GamePanel;
import fr.hop.utilities.CoordinateUtilities;

import java.util.*;

import static fr.hop.ui.GamePanel.AXEL_WIDTH;

public class Axel {

    public static final double LATERAL_SPEED = 10.0; // Vitesse latérale
    public static final double MAX_JUMP_SPEED = 20.0; // Vitesse de saut
    public static final double MAX_FALL_SPEED = -20.0; // Vitesse maximale de chute
    public static final double GRAVITY_FORCE = 1;   // Force de gravité
    public static final double DIVE_SPEED = 3.0; // Accélération en piqué

    // Position et vitesse
    private double currentX;
    private double currentY;
    private double velocityX;   // Vitesse horizontale
    private double velocityY;   // Vitesse verticale
    private double nextX;
    private double nextY;

    // États du personnage
    private boolean isFalling;
    private boolean isJumping;
    private boolean isDiving;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean isAlive;

    private int highestAltitude = 0; // Altitude maximale atteinte
    private final Field gameField;

    private boolean lastFacingLeft;

    private Queue<AbstractMap.SimpleEntry<Integer, Integer>> drawPositions; // FIFO

    public Axel(Field field, int startX, int startY) {
        this.gameField = field;
        this.currentX = startX;
        this.currentY = startY;
        this.isAlive = true;
        this.drawPositions = new ArrayDeque<>();
        this.lastFacingLeft = false;
    }

    // Mise à jour de la vitesse en fonction des actions
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

    private boolean nextXIsFalling(double x, Block block) {
        return x < block.getLeftPosition() || x > block.getRightPosition() + AXEL_WIDTH / 2.42; // 2.42 car j'ai mesuré
    }

    // Vérification des collisions avec un bloc
    private void performCollisionWithBlock(Block block) {
        // On considère les pieds - nez qui dépasse
        if (nextY > block.getAltitude() || nextXIsFalling(currentX, block)) {
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
            if (nextXIsFalling(nextX, block)) {
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

    public void update() {
        updateSpeed();
        checkCollision();

        this.drawPositions = CoordinateUtilities.generateCoordinates(3, (int) currentX, (int) currentY, (int) nextX, (int) nextY);

        currentX = nextX;
        currentY = nextY;

        // Vérification de la survie
        if (currentY < gameField.getBottom() - GamePanel.AXEL_HEIGHT) {
            isAlive = false;
        }
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getPosition() {
        return new AbstractMap.SimpleEntry<>((int) this.currentX, (int) this.currentY);
    }

    public AbstractMap.SimpleEntry<Integer, Integer> nextDrawPosition() {
        return Optional.ofNullable(drawPositions.poll()).orElse(getPosition());
    }

    public boolean hasFallen() {
        return !isAlive;
    }

    public int getScore() {
        return Math.max(highestAltitude - Field.START_ALTITUDE, 0);
    }

    public void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }

    public void setDiving(boolean diving) {
        this.isDiving = diving;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
        if (movingLeft) {
            this.lastFacingLeft = true;
        }
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
        if (movingRight) {
            this.lastFacingLeft = false;
        }
    }

    public boolean isLastFacingLeft() {
        return lastFacingLeft;
    }
}
