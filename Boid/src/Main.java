public class Main {
    public static void main(String[] args) {
        Space space = new Space();
        space.createBoids(600);
        space.makeGui();
    }
}