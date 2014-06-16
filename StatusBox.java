
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class StatusBox extends Rectangle2D.Float implements GameFigure{
    
    private int state = STATE_TRAVELING;
    private GameData gameData;
    private boolean displayLoseMessage = false;
    private int greenX = 400;
    private int yellowX = 445;
    private int  redX = 490;
    private float greenWidth = 0;
    private float yellowWidth = 0;
    private float redWidth = 0;
    private final double BAR_TIMER_DECREMENT = 0.2;
    
    public StatusBox(GameData gameData) {
        this.gameData = gameData;
        x = 0;
        y = 0;
        width = 600;
        height = 425;
        
    }

    public void render(Graphics g){
        g.setColor(Color.black);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        g.setColor(Color.white);
        g.drawString("Health: ", 8, 18);
        g.setColor(Color.red);
        int healthX = 50;
        for (int i = 0; i < gameData.getHealth().getHealth(); i++) {
            g.fillRect((int)healthX, (int)9, (int)10, (int)10);
            healthX += 12;
        }
        g.setColor(Color.white);
        g.drawString("Score: " + gameData.getScore().getScore(), 115, 18);
        g.drawString("Difficulty: " + gameData.getMeteorThread().getBehavior().getDifficultyName(), 200, 18);
        g.drawString("Bonus: ", 350, 18);
        
        if (greenWidth > 0) {
            g.setColor(Color.green);
            g.fillRect((int) greenX, (int) 9, (int) greenWidth, (int) 10);
        }
        
        if (yellowWidth > 0) {
            g.setColor(Color.yellow);
            g.fillRect((int) yellowX, (int) 9, (int) yellowWidth, (int) 10);
        }
        
        if (redWidth > 0) {
            g.setColor(Color.red);
            g.fillRect((int) redX, (int) 9, (int) redWidth, (int) 10);
        }
        if (displayLoseMessage) {
            g.setColor(Color.red);
            g.drawString("You Lose! Click Reset to try again", 200, 250);
        }
    }

    public void update(){
        if (greenWidth > 0) {
            greenWidth -= BAR_TIMER_DECREMENT;
        }
        if (yellowWidth > 0) {
            yellowWidth -= BAR_TIMER_DECREMENT;
        }
        if (redWidth > 0) {
            redWidth -= BAR_TIMER_DECREMENT;
        }
        
    }
    public int getState(){
        return state;
    }
    public int getCurrentX(){
        return (int)getCenterX();
    }
    public int getCurrentY(){
        return (int)getCenterY();
    }

    public boolean getExplodable(){
        return false;
    }
    public int getSize(){
        return -1;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void setCollision() {
        
    }

    public boolean isDisplayLoseMessage() {
        return displayLoseMessage;
    }

    public void setDisplayLoseMessage(boolean displayLoseMessage) {
        this.displayLoseMessage = displayLoseMessage;
    }


    public float getGreenWidth() {
        return greenWidth;
    }

    public void setGreenWidth(float greenWidth) {
        this.greenWidth = greenWidth;
    }

    public float getYellowWidth() {
        return yellowWidth;
    }

    public void setYellowWidth(float yellowWidth) {
        this.yellowWidth = yellowWidth;
    }

    public float getRedWidth() {
        return redWidth;
    }

    public void setRedWidth(float redWidth) {
        this.redWidth = redWidth;
    }

   

    
    

}
