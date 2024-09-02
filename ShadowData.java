import java.util.ArrayList;

public class ShadowData {

    // SHADOW DATA STRUCTURE
    static ArrayList<GameData> userData = new ArrayList<GameData>();

    public static ArrayList<GameData> getUserData() {
        return userData;
    }

    public static void setGameData(Boolean winOrNot, int numberGuesses) {
        GameData newGame = new GameData(winOrNot, numberGuesses);
        System.out.println("ADDING NEW GAME DATA");
        userData.add(newGame);
        // System.out.println(userData);
    }

}
