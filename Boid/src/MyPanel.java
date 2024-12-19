import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;

public class MyPanel extends JPanel implements ActionListener {

    int screenWidth;
    int screenHeight;
    Image bird = new ImageIcon("boid.png").getImage();
    boolean followMouseStop = true;


    Timer timer;
    JComboBox<String> cb;

    ArrayList<Boid> boids;
    Point oldMouse;


    public void setScreenWidth(int input) {screenWidth = input;}
    public void setScreenHeight(int input) {screenHeight = input;}
    public void setBoids(ArrayList<Boid> input) {boids = input;}

    MyPanel(int width, int height) {
        setScreenWidth(width);
        setScreenHeight(height);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        timer = new Timer(10, this);
        System.out.println(new File("boid.png").exists());

        String[] choises = {"Follow cursor", "Avoid cursor", " "};
        cb = new JComboBox<String>(choises);
        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        this.add(cb);

        JButton btn = new JButton("Apply");
        btn.addActionListener(this);
        btn.setActionCommand("apply");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
        this.add(btn);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.darkGray);
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        timer.start();
        //g2D.fillOval(mouse.x - 140, mouse.y - 90, 10, 10);

        for (Boid boid : boids) {
            AffineTransform old = g2D.getTransform();
            if (boid.getVelX() > 0) {
                g2D.rotate(Math.atan(boid.getVelY() / boid.getVelX()), (((int) boid.getPosX()) + 5), (((int) boid.getPosY()) + 2));
            } else {
                g2D.rotate((Math.atan(boid.getVelY() / boid.getVelX()) + Math.PI), (((int) boid.getPosX()) + 5), (((int) boid.getPosY()) + 2));
            }
            g2D.drawImage(bird, (int)boid.getPosX(), (int)boid.getPosY(), null);
            g2D.setTransform(old);
        }
    }

    public void actionPerformed(ActionEvent e) {
        //changes how the boids act based on what the user chooses in the drop down menu
        if ("apply".equals(e.getActionCommand())){
            if ("Follow cursor".equals(cb.getSelectedItem().toString())) {
                for (Boid boid : boids) {
                    boid.followMouse = true;
                    boid.avoidMouse = false;
                }
            } else if ("Avoid cursor".equals(cb.getSelectedItem())) {
                for (Boid boid : boids) {
                    boid.followMouse = false;
                    boid.avoidMouse = true;
                }
            } else if (" ".equals(cb.getSelectedItem())) {
                for (Boid boid : boids) {
                    boid.followMouse = false;
                    boid.avoidMouse = false;
                }
            }
        } else {
            //updates each boid each frame
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            for (Boid boid : boids) {
                if (boid.followMouse && followMouseStop) {
                    boid.updateFollowMouse(mouse.x - 140, mouse.y - 90);
                } else if (boid.avoidMouse) {
                    boid.updateAvoidMouse(mouse.x - 140, mouse.y - 90);
                }
                boid.updateBoid(boids);
                repaint();
            }
            //if the mouse stops moving then the boids stop following the mouse
            if (mouse.x == MouseInfo.getPointerInfo().getLocation().x) {
                followMouseStop = false;
            } else {
                followMouseStop = true;
            }
        }
    }
}