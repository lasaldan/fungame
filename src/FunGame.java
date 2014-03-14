/**
 * Created by lasaldan on 3/14/14.
 */
public class FunGame {

    public static void main (String[] args) {
        String msg = "Howdy!";
        for(int i=0; i<5; i++) {
            msg += "!";
            printMsg(msg);
        }
    }

    private static void printMsg(String msg) {
        System.out.println(msg);
    }
}
