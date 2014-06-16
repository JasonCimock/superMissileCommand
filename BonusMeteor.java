
import java.awt.Graphics;


public class BonusMeteor extends GameFigureDecorator{

    private GameData gameData;
    private Integer bonusState;
    private final float BAR_WIDTH = 40;

    public BonusMeteor(GameFigure gameFigure, GameData gameData, int bonusState) {
        super(gameFigure);
        this.gameData = gameData;
        this.bonusState = bonusState;
        
    }

    @Override
    public void render(Graphics g) {
        gameFigure.render(g);
    }

    @Override
    public void update(){
        gameFigure.update();
        
    }
    
    @Override
    public int getState() {
        int x = gameFigure.getState();
        return x;
    }

    @Override
    public int getCurrentX() {
        int x = gameFigure.getCurrentX();
        return x;
    }

    @Override
    public int getCurrentY() {
        int y = gameFigure.getCurrentY();
        return y;
    }

    @Override
    public boolean getExplodable() {
        return gameFigure.getExplodable();
    }

    @Override
    public int getSize() {
        return gameFigure.getSize();
    }

    @Override
    public void setCollision() {
        gameFigure.setCollision();
        if (bonusState == 1) {
                if (gameData.getBonusStateList().contains(bonusState)) {
                    gameData.getGreenThread().interrupt();             
                }
                else {
                    gameData.setBonusState(bonusState);
                }
                gameData.getStatusBox().setGreenWidth(BAR_WIDTH);
                BonusThread bonusThread = new BonusThread(gameData, bonusState);
                gameData.setGreenThread(new Thread(bonusThread));
                gameData.getGreenThread().start();
            }
            else if (bonusState == 2) {
                if (gameData.getBonusStateList().contains(bonusState)) {
                    gameData.getYellowThread().interrupt();
                }
                else {
                    gameData.setBonusState(bonusState);    
                }
                
                gameData.getStatusBox().setYellowWidth(BAR_WIDTH);
                BonusThread bonusThread = new BonusThread(gameData, bonusState);
                gameData.setYellowThread(new Thread(bonusThread));
                gameData.getYellowThread().start();
            }
            else if (bonusState == 3) {
                if (gameData.getBonusStateList().contains(bonusState)) {
                    gameData.getRedThread().interrupt();
                }
                else {
                    gameData.setBonusState(bonusState);  
                }
                gameData.getStatusBox().setRedWidth(BAR_WIDTH);
                BonusThread bonusThread = new BonusThread(gameData, bonusState);
                gameData.setRedThread(new Thread(bonusThread));
                gameData.getRedThread().start();
            }
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
