
import java.awt.Color;


public class EasyBehavior implements MeteorThreadBehavior {

    private GameData gameData;
    private int meteorTravelDistance = 1;
    
    
    public EasyBehavior(GameData gameData) {
        this.gameData = gameData;
    }
    
    public void runCommand() {
        Color color;
        Meteor f;
        int randomStartPoint = (int) (Math.random() * GamePanel.PWIDTH);
        int randomTarget = (int) (Math.random() * GamePanel.PWIDTH);
        
        double r = Math.random();
        if (r < 0.25) {
            color = Color.green;
            f = new Meteor(randomStartPoint, 25, color, gameData);
            f.setTarget(randomTarget, GamePanel.PHEIGHT);
            f.setTravelDistance(meteorTravelDistance);
            GameFigure z = f;
            z = new BonusMeteor(f, gameData, 1);
            synchronized (gameData.figures) { // shared data access
            gameData.figures.add(z);
        }
        }  
        else if (r >= 0.25 && r < 0.5) {
            color = Color.yellow;
            f = new Meteor(randomStartPoint, 25, color, gameData);
            f.setTarget(randomTarget, GamePanel.PHEIGHT);
            f.setTravelDistance(meteorTravelDistance);
            GameFigure z = f;
            z = new BonusMeteor(f, gameData, 2);
            synchronized (gameData.figures) { // shared data access
            gameData.figures.add(z);
            }
        }
        else if (r >= 0.5 && r < 0.75) {
            color = Color.red;
            f = new Meteor(randomStartPoint, 25, color, gameData);
            f.setTarget(randomTarget, GamePanel.PHEIGHT);
            f.setTravelDistance(meteorTravelDistance);
            GameFigure z = f;
            z = new BonusMeteor(f, gameData, 3);
            synchronized (gameData.figures) { // shared data access
            gameData.figures.add(z);
            }
        }
        else {
            color = Color.white;
            f = new Meteor(randomStartPoint, 25, color, gameData);
            f.setTarget(randomTarget, GamePanel.PHEIGHT);
            f.setTravelDistance(meteorTravelDistance);
            synchronized (gameData.figures) { // shared data access
            gameData.figures.add(f);
        }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        
    }
    
    public String getDifficultyName() {
        String temp = "Easy";
        return temp;
    }
}
