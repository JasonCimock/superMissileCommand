
public class Animator implements Runnable {
    boolean running;
    boolean paused = false;
    GamePanel gamePanel = null;
    GameData gameData = null;
    
    public Animator() {
    }
    
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (paused) {
                gameData.update();
                gamePanel.gameRender();
                gamePanel.printScreen();
            }
            else {
                gameData.update();
                if (gameData.getHealth().getHealth() <= 0) {
                    gamePanel.stopAnimation();
                    
                    
                }
                gamePanel.gameRender();
                gamePanel.printScreen();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                
            }
        }
        System.exit(0);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
    
    
    
}
