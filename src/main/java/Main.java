import Gen.Game;


public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Starting new game");
            Game game = new Game();
            game.run();
        }
    }
}


