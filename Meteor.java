
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector2f;

public class Meteor extends Line2D.Float implements GameFigure {

    GameData gameData;
    static int SIZE = 2;
    Color color;
    Point2D.Float target;
    private int state = STATE_TRAVELING;
    private int travelDistance = 1;
    private int explosionSize = SIZE;
    private int explosionMaxSize;
    private boolean explodable = true;

    public Meteor(int x, int y, Color color, GameData gameData) {
        x1 = x;
        y1 = y;
        x2 = x;
        y2 = y;
        this.color = color;
        this.gameData = gameData;
    }

    public void setTarget(float x, float y) {
        target = new Point2D.Float(x, y);
    }

    public void setExplosionMaxSize(int size) {
        explosionMaxSize = size;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.drawLine((int)x1, (int)y1, (int)getX2(), (int)getY2());
    }

    @Override
    public void update() {
        updateState();
        if (state == STATE_TRAVELING) {
            updateLocation();
            
        } 
    }

    // Vector arithmetic
    // A: current position
    // B: target position
    // d: distance to travel along the line from A to B
    //     A_moved = A + |B - A| * d where |  | represents 'norm'
    public void updateLocation() {
        Vector2f currentLoc = new Vector2f((float) getX2(), (float) getY2());
        Vector2f update = new Vector2f(target.x, target.y);
        update.sub(currentLoc); // B - A
        update.normalize(); // |B - A|
        update.scale(travelDistance); // |B - A| x dist
        currentLoc.add(update); // A + |B - A| x d
        setLine(x1, y1, currentLoc.x, currentLoc.y);
    }

    public void updateSize() {
        double x = target.getX();
        double y = target.getY();
        explosionSize += 2;
        
    }

    public void updateState() {
        if (state == STATE_TRAVELING) {
            double distance = target.distance(getX2(), getY2());
            boolean targetReached = distance <= 6.0 ? true : false;

            if (targetReached) {
                state = STATE_DONE;
                gameData.getHealth().decrementHealth();
            }             
        }
    }

    public Color getColor() {
        return color;
    }

    public int getState() {
        return state;
    }

    public int getCurrentX() {
        return (int)x2;
    }

    public int getCurrentY() {
        return (int)y2;
    }

    public boolean getExplodable() {
        return explodable;
    }

    public int getSize() {
        return SIZE;
    }

    @Override
    public void setCollision() {
        state = STATE_EXPLODED;
        explodable = false;
        
    }

    public void setTravelDistance(int travelDistance) {
        this.travelDistance = travelDistance;
    }

    

    

}
