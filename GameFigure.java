
import java.awt.Graphics;

public interface GameFigure {
    public void render(Graphics g);
    public void update();
    public int getState();
    public int getCurrentX();
    public int getCurrentY();
    public boolean getExplodable();
    public int getSize();
    public void setCollision();
    static final int STATE_TRAVELING = 1;
    static final int STATE_EXPLODING = 2;
    static final int STATE_DONE = 0;
    static final int STATE_EXPLODED = 3;
    
    
}
