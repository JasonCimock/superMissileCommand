
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.vecmath.Vector2f;

public class Missile extends Ellipse2D.Float implements GameFigure {

    private GameData gameData;
    static int SIZE = 2;
    Color color;
    Point2D.Float target;
    private int state = STATE_TRAVELING;
    private int unitTravelDistance = 4;
    private int explosionSize = SIZE;
    private int explosionMaxSize;
    private boolean explodable = false;
    private double targetReachedThreshold = 2.0;

    public Missile(int x, int y, Color color, GameData gameData) {
        setFrameFromCenter(x, y, x + SIZE, y + SIZE);
        this.color = color;
        this.gameData = gameData;
    }
    
    public Missile(int x, int y, GameData gameData) {
        setFrameFromCenter(x, y, x + SIZE, y + SIZE);
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
        g.fillOval((int)super.x, (int)super.y, (int)super.width, (int)super.height);
    }

    @Override
    public void update() {
        updateState();
        if (state == STATE_TRAVELING) {
            updateLocation();
        } else if (state == STATE_EXPLODING) {
            updateSize();
        }
    }

    // Vector arithmetic
    // A: current position
    // B: target position
    // d: distance to travel along the line from A to B
    //     A_moved = A + |B - A| * d where |  | represents 'norm'
    public void updateLocation() {
        Vector2f currentLoc = new Vector2f((float) getCenterX(), (float) getCenterY());
        Vector2f update = new Vector2f(target.x, target.y);
        update.sub(currentLoc); // B - A
        update.normalize(); // |B - A|
        update.scale(unitTravelDistance); // |B - A| x dist
        currentLoc.add(update); // A + |B - A| x d

        setFrameFromCenter(currentLoc.x, currentLoc.y,
                currentLoc.x + SIZE, currentLoc.y + SIZE);
    }

    public void updateSize() {
        double x = target.getX();
        double y = target.getY();
        explosionSize += 2;
        setFrameFromCenter(x, y, x + explosionSize, y + explosionSize);
        for (int i = 0; i < gameData.figures.size(); i++) {
            GameFigure g = gameData.figures.get(i);
            if (g.getExplodable()) {
                boolean collided = checkCollision(g.getCurrentX(), g.getCurrentY());
                if (collided) {
                    g.setCollision();
                    
                }
            }
        }
    }

    public void updateState() {
        if (state == STATE_TRAVELING) {
            double distance = target.distance(getCenterX(), getCenterY());
            boolean targetReached = distance <= targetReachedThreshold ? true : false;
            if (targetReached) {
                state = STATE_EXPLODING;
            }
        } else if (state == STATE_EXPLODING) {
            if (explosionSize >= explosionMaxSize) {
                state = STATE_DONE;
            }
        }
    }
    
    public boolean checkCollision(int x, int y) {
        if (this.contains((int)x, (int)y)) {
            return true;
        }
        else {
            return false;
        }
    }

    public Color getColor() {
        return color;
    }

    public int getState() {
        return state;
    }

    public int getCurrentX() {
        return (int)getCenterX();
    }

    public int getCurrentY() {
        return (int)getCenterY();
    }

    public boolean getExplodable() {
        return explodable;
    }

    public int getSize() {
        return explosionSize;
    }

    @Override
    public void setCollision() {
        
    }

    public void setUnitTravelDistance(int unitTravelDistance) {
        this.unitTravelDistance = unitTravelDistance;
    }

    public double getTargetReachedThreshold() {
        return targetReachedThreshold;
    }

    public void setTargetReachedThreshold(double targetReachedThreshold) {
        this.targetReachedThreshold = targetReachedThreshold;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    


}
