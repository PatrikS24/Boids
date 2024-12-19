import java.util.ArrayList;

//https://people.ece.cornell.edu/land/courses/ece4760/labs/s2021/Boids/Boids.html#:~:text=Boids%20is%20an%20artificial%20life,very%20simple%20set%20of%20rules.

public class Boid extends Space{
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    double speed;

    //Constructor method that creates a boid with a random velocity and position
    public Boid() {
        posX = (int) (Math.random() * (screenWidth - 50 - 50) + 50);
        posY = (int) (Math.random() * (screenHeight - 50 - 50) + 50);

        int rangeMin = -20;
        int rangeMax = 20;


        double randomValue = rangeMin + (rangeMax - rangeMin) * rng.nextDouble();
        velX = randomValue;
        randomValue = rangeMin + (rangeMax - rangeMin) * rng.nextDouble();
        velY = randomValue;
    }

    //Updates boids position and velocity
    void updateBoid(ArrayList<Boid> boids) {
        ArrayList<Boid> neighbors = neighboringBoids(boids, visionRadius);
        ArrayList<Boid> closeNeighbors = neighboringBoids(boids, separationRadius);
        updateSeparation(closeNeighbors);
        updateAlignment(neighbors);
        updateCohesion(neighbors);
        avoidScreenEdge();
        updateSpeed();
        updatePosition();
    }

    //Updates the speed of the boid
    void updateSpeed() {
        speed = Math.sqrt(velX * velX + velY * velY);
        if (speed > maxSpeed) {
            velX = (velX/speed) * maxSpeed;
            velY = (velY/speed) * maxSpeed;
        }
        if (speed < minSpeed) {
            velX = (velX/speed) * minSpeed;
            velY = (velY/speed) * minSpeed;
        }
    }

    //Updates the boids position based of off the boids velocity
    void updatePosition() {
        posX = posX + velX;
        posY = posY + velY;
    }

    //changes the velocity vector so that the boid avoids the screen edge
    void avoidScreenEdge() {
        if (posX < leftMargin) {
            velX = velX + turnFactor;
        }
        if (posX > rightMargin) {
            velX = velX - turnFactor;
        }
        if (posY > bottomMargin) {
            velY = velY - turnFactor;
        }
        if (posY < topMargin) {
            velY = velY + turnFactor;
        }
    }

    //changes the velocity vector so that the boid turns toward the mouse
    void updateFollowMouse(int mouseX, int mouseY) {
        if (followMouse && Math.sqrt(((posX - mouseX) * (posX - mouseX)) + ((posY - mouseY) * (posY - mouseY))) < followMouseRadius) {
            velX += (mouseX - posX) * followMouseFactor;
            velY += (mouseY - posY) * followMouseFactor;
        }
    }

    //changes the velocity vector so that the boid turns away from the mouse
    void updateAvoidMouse(int mouseX, int mouseY) {
        if (avoidMouse && Math.sqrt(((posX - mouseX) * (posX - mouseX)) + ((posY - mouseY) * (posY - mouseY))) < 80) {
            velX -= (mouseX - posX) * followMouseFactor;
            velY -= (mouseY - posY) * followMouseFactor;
        }
    }

    //Loops through other boids and adjust direction to keep similar direction
    void updateAlignment(ArrayList<Boid> neighboringBoids){
        double xVelAvg = 0;
        double yVelAvg = 0;

        int numOfBoids = neighboringBoids.size();

        for (Boid boid : neighboringBoids) {
            xVelAvg += boid.getVelX();
            yVelAvg += boid.getVelY();
        }

        if (numOfBoids > 0) {
            xVelAvg = xVelAvg / numOfBoids;
            yVelAvg = yVelAvg / numOfBoids;
        }

        velX += (xVelAvg - velX) * matchingFactor;
        velY += (yVelAvg - velY) * matchingFactor;
    }

    //Loops through other boids and adjust direction to not fly in to another boid
    void updateSeparation(ArrayList<Boid> neighboringBoids) {
        double close_dx = 0;
        double close_dy = 0;
        for (Boid boid : neighboringBoids) {
            close_dx += (posX - boid.getPosX());
            close_dy += (posY - boid.getPosY());
        }
        velX += close_dx * avoidFactor;
        velY += close_dy * avoidFactor;

    }

    //Loops through other boids and adjusts direction towards the center of mass of nearby boids
    void updateCohesion(ArrayList<Boid> neighboringBoids) {
        double xPosAvg = 0;
        double yPosAvg = 0;
        int numOfBoids = neighboringBoids.size();

        for (Boid boid : neighboringBoids) {
            xPosAvg += boid.getPosX();
            yPosAvg += boid.getPosY();
        }
        if (numOfBoids > 0) {
            xPosAvg = xPosAvg / numOfBoids;
            yPosAvg = yPosAvg / numOfBoids;
        }
        velX += (xPosAvg - posX) * cohesionFactor;
        velY += (yPosAvg - posY) * cohesionFactor;
    }

    //creates an arraylist of boids that are nearby
    ArrayList<Boid> neighboringBoids(ArrayList<Boid> boids, double radius) {
        ArrayList<Boid> result = new ArrayList<>();
        for (Boid boid : boids) {
            double distance = distanceBetweenBoids(boid);
            if (this != boid && distance < radius) {
                result.add(boid);
            }

        }
        return result;
    }

    //Returns distance between this boid and input boid
    double distanceBetweenBoids(Boid otherBoid) {
        return Math.sqrt(((posX - otherBoid.getPosX()) * (posX - otherBoid.getPosX()))
                        + ((posY - otherBoid.getPosY()) * (posY - otherBoid.getPosY())));
    }

    double getVelX() {return velX;}

    double getVelY() {return velY;}

    double getPosX() {return posX;}

    double getPosY() {return posY;}
}
