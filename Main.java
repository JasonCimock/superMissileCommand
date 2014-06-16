
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame
        implements ActionListener, MouseListener {

    private GamePanel gamePanel;
    private final GameData gameData; // for 'synchronized'
    private Animator animator;
    private JButton startButton;
    private JButton quitButton;
    private JButton resetButton;
    
    private MeteorThread meteorThread;
    private StatusBox statusBox;

    public Main() {
        setSize(615, 480);
        setLocation(100, 100);
        Container c = getContentPane();
        animator = new Animator();
        gameData = GameData.getInstance();
        meteorThread = new MeteorThread(gameData);
        gameData.setMeteorThread(meteorThread);
        gamePanel = new GamePanel(animator, gameData, meteorThread);
        statusBox = new StatusBox(gameData);
        gameData.setStatusBox(statusBox);
        gameData.addLauncher();
        
        animator.setGamePanel(gamePanel);
        animator.setGameData(gameData);
        c.add(gamePanel, "Center");

        JPanel southPanel = new JPanel();
        startButton = new JButton("Start");
        southPanel.add(startButton);
        resetButton = new JButton("Reset");
        southPanel.add(resetButton);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        gamePanel.addMouseListener(this);
        startButton.addActionListener(this);
        resetButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == startButton) {
            gamePanel.startGame();
        }
        else if (ae.getSource() == resetButton) {
            meteorThread.running = true;
            gamePanel.restartGame();
            
        }
        else if (ae.getSource() == quitButton) {
            animator.running = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {

        int x = me.getX();
        int y = me.getY();
        Color color;
        boolean bonusMissile = false;
        
        Missile f = new Missile(GamePanel.PWIDTH / 2, GamePanel.PHEIGHT - 75, gameData);
        f.setTarget(x, y);
        int size = 50;
        f.setExplosionMaxSize(size);
        
        if (gameData.getBonusStateList().isEmpty()) {
            f.setUnitTravelDistance(4);
            f.setTargetReachedThreshold(2.0);
            f.setColor(Color.white);
        }
        else if (gameData.getBonusStateList().size() == 1) {
            if (gameData.getBonusStateList().get(0) == 1) {
                f.setUnitTravelDistance(8);
                f.setTargetReachedThreshold(4.0);
                f.setColor(Color.green);
            } else if (gameData.getBonusStateList().get(0) == 2) {
                f.setUnitTravelDistance(4);
                f.setTargetReachedThreshold(2.0);
                f.setColor(Color.yellow);
                f.setExplosionMaxSize(100);
            }
            else if (gameData.getBonusStateList().get(0) == 3) {
                f.setUnitTravelDistance(4);
                f.setTargetReachedThreshold(2.0);
                f.setColor(Color.red);
                f.setExplosionMaxSize(5);
                bonusMissile = true;
            } 
        }
        else if (gameData.getBonusStateList().size() > 1){
            if (gameData.getBonusStateList().contains(new Integer(1)) && gameData.getBonusStateList().contains(new Integer(2)) && gameData.getBonusStateList().contains(new Integer(3))) {
                f.setUnitTravelDistance(8);
                f.setTargetReachedThreshold(4.0);
                f.setColor(Color.CYAN);
                f.setExplosionMaxSize(100);
                bonusMissile = true;
            }
            else if (gameData.getBonusStateList().contains(new Integer(1)) && gameData.getBonusStateList().contains(new Integer(2))) {
                f.setUnitTravelDistance(8);
                f.setTargetReachedThreshold(4.0);
                f.setColor(Color.blue);
                f.setExplosionMaxSize(100);
            }
            else if (gameData.getBonusStateList().contains(new Integer(1)) && gameData.getBonusStateList().contains(new Integer(3))) {
                f.setUnitTravelDistance(8);
                f.setTargetReachedThreshold(4.0);
                f.setColor(Color.orange);
                f.setExplosionMaxSize(5);
                bonusMissile = true;
            }
            else if (gameData.getBonusStateList().contains(new Integer(2)) && gameData.getBonusStateList().contains(new Integer(3))) {
                f.setUnitTravelDistance(4);
                f.setTargetReachedThreshold(2.0);
                f.setColor(Color.magenta);
                f.setExplosionMaxSize(100);
                bonusMissile = true;
            }
        }

        if (bonusMissile) {
            GameFigure z = f;
            z = new BonusMissile(f, gameData);
            synchronized (gameData.figures) { // shared data access
                gameData.figures.add(z);
            }
        }
        else {
            synchronized (gameData.figures) { // shared data access
                gameData.figures.add(f);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public static void main(String[] args) {
        JFrame game = new Main();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
    }
}
