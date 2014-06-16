
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Launcher implements GameFigure {
    
    Image launcherImage;
    float x, y;
    private boolean explodable = false;

    public Launcher(float x, float y) {
        this.x = x;
        this.y = y;
        
        
        //String imagePath = System.getProperty("user.dir");
        String imagePath = "http://cimock.atwebpages.com/missileCommand";        
        
        // separator: Windows '\', Linux '/'
        //String separator = System.getProperty("file.separator");

        // put images in 'images' folder, which is on the top level of
        // the NetBeans project folder.
        // Using "Files" tab of the NetBeans explorer window, right click on
        // the project folder name, and create a folder named "image"
        // You cannot see "images" folder in 'Project' tab, though
        //launcherImage = getImage(imagePath + separator + "images" + separator
        //       + "missilelauncher.png");
        
        try {
            launcherImage = ImageIO.read(new URL("http://cimock.atwebpages.com/missileCommand/images/missilelauncher.png"));
        } catch (Exception e) {
            System.out.println("Error: Cannot open image:");
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:");
        }
        
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

    @Override
    public void render(Graphics g) {
        g.drawImage(launcherImage, (int)x, (int)y, null);
    }

    @Override
    public void update() {
    }

    @Override
    public int getState() {
        return STATE_TRAVELING;
    }

    public int getCurrentX() {
        return (int)x;
    }

    public int getCurrentY() {
        return (int)y;
    }

    public boolean getExplodable() {
        return explodable;
    }

    public int getSize() {
        return -1;
    }

    
    public void setCollision() {
        
    }

    

}
