
public class BonusThread implements Runnable {

    private GameData gameData;
    private Integer bonusState;
    
    
    public BonusThread(GameData gameData, Integer bonusState) {
        this.gameData = gameData;
        this.bonusState = bonusState;
    }
    
    @Override
    public void run() {
        try {
                Thread.sleep(10000); 
            } catch (InterruptedException e) {
                return;
            }
        gameData.getBonusStateList().remove(bonusState);

        
    }
    
    
    
}
