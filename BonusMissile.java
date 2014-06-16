
import java.awt.Color;
import java.awt.Graphics;


public class BonusMissile extends GameFigureDecorator {

    private GameData gameData;
    private Integer bonusState;
    
    public BonusMissile(GameFigure gameFigure, GameData gameData) {
        super(gameFigure);
        this.gameData = gameData;
    }
    
    public void render(Graphics g) {
        gameFigure.render(g);
    }

    public void update() {
        int i = gameFigure.getState();
        gameFigure.update();
        int j = gameFigure.getState();

        if (i == 1 && j == 2) {
            int x = gameFigure.getCurrentX();
            int y = gameFigure.getCurrentY();

            Missile a[] = new Missile[8];

            for (int k = 0; k < 8; k++) {
                a[k] = new Missile(x, y, gameData);
                a[k].setUnitTravelDistance(4);
                a[k].setTargetReachedThreshold(2.0);
                a[k].setColor(Color.red);
                a[k].setExplosionMaxSize(50);
            }

            a[0].setTarget(x, y + 50);
            a[1].setTarget(x - 25, y + 50);
            a[2].setTarget(x - 50, y);
            a[3].setTarget(x - 25, y - 50);
            a[4].setTarget(x, y - 50);
            a[5].setTarget(x + 25, y - 50);
            a[6].setTarget(x + 50, y);
            a[7].setTarget(x + 25, y + 50);

            synchronized (gameData.figures) {
                for (int m = 0; m < 8; m++) {
                    gameData.figures.add(a[m]);
                }
            }
        }
    }

    public int getState() {
        int x = gameFigure.getState();
        return x;
    }

    public int getCurrentX() {
        int x = gameFigure.getCurrentX();
        return x;
    }

    public int getCurrentY() {
        int y = gameFigure.getCurrentY();
        return y;
    }

    public boolean getExplodable() {
        return gameFigure.getExplodable();
    }

    public int getSize() {
        return gameFigure.getSize();
    }

    @Override
    public void setCollision() {
        
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public int getBonusState() {
        return bonusState;
    }

    public void setBonusState(int bonusState) {
        this.bonusState = bonusState;
    }
}
