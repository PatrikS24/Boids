import java.util.ArrayList;
import java.util.Random;

public class Space {
    final static double avoidFactor = 0.04;
    final static double matchingFactor = 0.11;
    final static double cohesionFactor = 0.0004;
    final static int visionRadius = 30;
    final static int separationRadius = 14;
    final static double turnFactor = 0.3;
    final double followMouseFactor = 0.005;

    static final int screenWidth = 1000;
    static final int screenHeight = 600;
    final static int leftMargin = 50;
    final static int rightMargin = screenWidth - 50;
    final static int topMargin = 50;
    final static int bottomMargin = screenHeight - 50;
    final static int maxSpeed = 2;
    final static int minSpeed = 1;
    public static boolean followMouse = false;
    public static boolean avoidMouse = false;
    final static int followMouseRadius = 150;

    Random rng = new Random();

    ArrayList<Boid> boids = new ArrayList<Boid>();

    MyFrame frame;


    void createBoids(int numOfBoids) {
        for (int i = 0; i < numOfBoids; i++) {
            boids.add(new Boid());
        }
    }


    void makeGui() {
        frame = new MyFrame(screenWidth, screenHeight);
        frame.setBoids(boids);
    }




}
