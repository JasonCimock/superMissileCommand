
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GameData {

    final List<GameFigure> figures;
    private Health health;
    private Score score;
    private StatusBox statusBox;
    private MeteorThread meteorThread;
    private int bonusState = 0;
    private final ArrayList<Integer> bonusStateList;
    private Thread redThread;
    private Thread greenThread;
    private Thread yellowThread;
    private static GameData singleGameData = new GameData();
    
    private GameData() {
        health = new Health();
        score = new Score();
        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
        bonusStateList = new ArrayList<Integer>();
        
    }
    
    public static GameData getInstance() {
        return singleGameData;
    }
    
    public void update() {
        List<GameFigure> remove = new ArrayList<GameFigure>();
        GameFigure f, g;
        synchronized (figures) {
            for (int i = 0; i < figures.size(); i++) {
                f = figures.get(i);
                f.update();
                
                if (f.getState() == GameFigure.STATE_DONE) {  
                    remove.add(f);
                }
                else if (f.getState() == GameFigure.STATE_EXPLODED) {
                    remove.add(f);
                    getScore().incrementScore(10);
                    if (getScore().getScore() >= 50 && getScore().getScore() < 100) {
                        meteorThread.setBehavior(new MediumBehavior(this));
                        
                    }
                    else if (getScore().getScore() >= 100) {
                        meteorThread.setBehavior(new HardBehavior(this));
                    }
                }
            }
            figures.removeAll(remove);
        }
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setStatusBox(StatusBox statusBox) {
        this.statusBox = statusBox;
        figures.add(statusBox);
    }

    public void setMeteorThread(MeteorThread meteorThread) {
        this.meteorThread = meteorThread;
    }

    public List<GameFigure> getFigures() {
        return figures;
    }

    public StatusBox getStatusBox() {
        return statusBox;
    }

    public MeteorThread getMeteorThread() {
        return meteorThread;
    }
    
    public void addLauncher() {
        figures.add(new Launcher(GamePanel.PWIDTH/2-30, GamePanel.PHEIGHT-85));
    }
    
    public void resetGameFigures() {
        
        synchronized (figures) {
            figures.add(new Launcher(GamePanel.PWIDTH / 2 - 30, GamePanel.PHEIGHT - 85));
            this.statusBox = new StatusBox(this);
            //statusBox.setGameData(this);
            health = new Health();
            score = new Score();
        }
    }
    
    public void clearMeteors() {
        synchronized (figures) {
            GameFigure temp;
            for (int i = 0; i < figures.size(); i++) {
                temp = figures.get(i);
                if (temp.getExplodable()) {
                    figures.remove(i);
                }
            }
        }
    }

    public int getBonusState() {
        return bonusState;
    }

    public void setBonusState(int bonusState) {
        
        //bonusStateList.clear();
        bonusStateList.add(bonusState);
        
        
    }

    public ArrayList<Integer> getBonusStateList() {
        return bonusStateList;
    }

    public Thread getYellowThread() {
        return yellowThread;
    }

    public void setYellowThread(Thread blueThread) {
        this.yellowThread = blueThread;
    }

    public Thread getGreenThread() {
        return greenThread;
    }

    public void setGreenThread(Thread greenThread) {
        this.greenThread = greenThread;
    }

    public Thread getRedThread() {
        return redThread;
    }

    public void setRedThread(Thread redThread) {
        this.redThread = redThread;
    }

    
}
