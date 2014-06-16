
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public static final int PWIDTH = 600; // size of the game panel
    public static final int PHEIGHT = 425;
    private Animator animator;
    private GameData gameData;
    // off screen rendering
    private Graphics graphics;
    private Image dbImage = null;
    private Image titleScreen;
    private Thread difficulty;
    private Thread animate;
    private MeteorThread meteorThread;

    public GamePanel(Animator animator, GameData gameData, MeteorThread meteorThread) {
        this.animator = animator;
        this.gameData = gameData;
        this.meteorThread = meteorThread;
        //String imagePath = System.getProperty("user.dir");
        String imagePath = "http://cimock.atwebpages.com/missileCommand/images/titleScreen.png";
        // separator: Windows '\', Linux '/'
        //String separator = System.getProperty("file.separator");
        //titleScreen = getImage(imagePath + separator + "images" + separator
        //+ "titleScreen.png");
        //titleScreen = ImageIO.read(new URL(imagePath));
        try {
            titleScreen = ImageIO.read(new URL("http://cimock.atwebpages.com/missileCommand/images/titleScreen.png"));
        } catch (Exception e) {
            System.out.println("Error: Cannot open image:");
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:");
        }

        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
    }

    public void startGame() {
        animate = new Thread(animator);
        difficulty = new Thread(meteorThread);
        animate.start();
        difficulty.start();
    }

    public void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = dbImage.getGraphics();
            }
        }

        graphics.clearRect(0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT);

        synchronized (gameData.figures) {
            GameFigure f;
            for (int i = 0; i < gameData.figures.size(); i++) {
                f = (GameFigure) gameData.figures.get(i);
                f.render(graphics);
            }
        }

    }

    public void printScreen() { // use active rendering to put the buffered image on-screen
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }

    public void stopAnimation() {
        meteorThread.setRunning(false);
        gameData.clearMeteors();
        gameData.getStatusBox().setDisplayLoseMessage(true);

    }

    public void restartGame() {
        //gameData.resetGameFigures();
        //System.out.println(difficulty.isAlive());
        //animate = new Thread(animator);
        gameData.getStatusBox().setDisplayLoseMessage(false);
        meteorThread = new MeteorThread(gameData);
        gameData.setMeteorThread(meteorThread);
        //animator.setGamePanel(this);
        //animator.setGameData(gameData);
        gameData.getScore().setScore(0);
        gameData.getHealth().setHealth(5);
        difficulty = new Thread(meteorThread);
        //animate.start();
        difficulty.start();

        //System.out.println();
        //System.out.println(difficulty.isAlive());
        //System.out.println(meteorThread.isRunning());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // use g2d.drawImage methods to paint your background image here...
        g2d.drawImage(titleScreen, 0, 0, null);
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (Exception ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }
}
