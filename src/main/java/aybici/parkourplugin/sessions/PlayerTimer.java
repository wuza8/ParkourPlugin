package aybici.parkourplugin.sessions;

public class PlayerTimer {
    private long time;

    public PlayerTimer() {
    }

    public void startTimer() {
        time = System.currentTimeMillis();
    }

    public long actualTime() {
        return System.currentTimeMillis() - time;
    }

    public void resetTimer() {
        time = 0;
    }
}
