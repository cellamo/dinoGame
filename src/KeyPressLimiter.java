import java.util.Timer;
import java.util.TimerTask;

public class KeyPressLimiter {
    private static final int KEY_PRESS_INTERVAL = 1000; // 1 second

    private Timer timer;
    private boolean keyEventsEnabled = true;

    public KeyPressLimiter() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                keyEventsEnabled = true;
            }
        }, KEY_PRESS_INTERVAL, KEY_PRESS_INTERVAL);
    }

    public boolean isKeyEventsEnabled() {
        return keyEventsEnabled;
    }

    public void keyPressed() {
        if (keyEventsEnabled) {
            keyEventsEnabled = false;
        }
    }
}
