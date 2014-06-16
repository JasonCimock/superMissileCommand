
import java.awt.Color;
import java.util.List;


public class MeteorThread implements Runnable{
    
    boolean running;
    GameData gameData;
    MeteorThreadBehavior behavior;
    
    public MeteorThread(GameData gameData) {
        this.gameData = gameData;
        behavior = new EasyBehavior(this.gameData);
        running = true;
    }
    
    public void run() {
        running = true;
        while (running) {
            behavior.runCommand();
        }

    }

    public MeteorThreadBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(MeteorThreadBehavior behavior) {
        this.behavior = behavior;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    
    
}
