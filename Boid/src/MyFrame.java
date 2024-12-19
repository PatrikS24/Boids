import javax.swing.*;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    int screenWidth;
    int screenHeight;

    public void setScreenWidth(int input) {screenWidth = input;}
    public void setScreenHeight(int input) {screenHeight = input;}
    public void setBoids(ArrayList<Boid> input) {panel.setBoids(input);}

    MyPanel panel;


    MyFrame(int width, int height){

        setScreenWidth(width);
        setScreenHeight(height);

        panel = new MyPanel(screenWidth, screenHeight);

        this.setTitle("Boids");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void repaint() {
        super.repaint();
        panel.repaint();
    }
}
