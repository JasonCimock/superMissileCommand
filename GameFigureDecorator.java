
public abstract class GameFigureDecorator implements GameFigure {
    
    protected GameFigure gameFigure;
    
    public GameFigureDecorator(GameFigure gameFigure) {
        this.gameFigure = gameFigure;
    }
    
}
