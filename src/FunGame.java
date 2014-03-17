/**
 * Created by lasaldan on 3/14/14.
 */
public class FunGame {

    public static void main (String[] args) {
        String msg = "Howdy Everyone! This game is gonna rock!";
        for(int i=0; i<5; i++) {
            msg += "!";
            printMsg(msg);
        }

        System.out.println("Output complete :) Starting Game...");

        Platformer game = new Platformer();
        game.run();

    }

    private static void printMsg(String msg) {
        System.out.println(msg);
    }
}
