package fr.hop.inputs;

import fr.hop.entities.Axel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.function.Function;

public class GameHandler implements KeyListener {

    private Map<Integer, Function<Boolean, Runnable>> keyActions;

    public GameHandler(List<Axel> axelList) {
        keyActions = new HashMap<>(generateKeyActions(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, axelList.get(0)));

        if (axelList.size() > 1) {
            keyActions = new HashMap<>(keyActions);
            keyActions.putAll(generateKeyActions(KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_D, axelList.get(1)));
        }
    }

    private Map<Integer, Function<Boolean, Runnable>> generateKeyActions(Integer up, Integer down, Integer left, Integer right, Axel axel) {
        return Map.of(
                up, (isPressed) -> () -> axel.setJumping(isPressed),
                down, (isPressed) -> () -> axel.setDiving(isPressed),
                left, (isPressed) -> () -> axel.setMovingLeft(isPressed),
                right, (isPressed) -> () -> axel.setMovingRight(isPressed)
        );
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void executeKeyAction(KeyEvent e, boolean isPressed) {
        Optional.ofNullable(keyActions.get(e.getKeyCode()))
                .map(action -> action.apply(isPressed))
                .ifPresent(Runnable::run);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        executeKeyAction(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        executeKeyAction(e, false);
    }
}
