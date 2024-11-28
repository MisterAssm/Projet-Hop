import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GameHandler implements KeyListener {

    private Axel axel;
    private final Map<Integer, Function<Boolean, Runnable>> keyActions = Map.of(
            KeyEvent.VK_UP, (isPressed) -> () -> axel.setJumping(isPressed),
            KeyEvent.VK_DOWN, (isPressed) -> () -> axel.setDiving(isPressed),
            KeyEvent.VK_LEFT, (isPressed) -> () -> axel.setMovingLeft(isPressed),
            KeyEvent.VK_RIGHT, (isPressed) -> () -> axel.setMovingRight(isPressed)
    );

    public GameHandler(Axel axel) {
        this.axel = axel;
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
