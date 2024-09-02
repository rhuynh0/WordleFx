public class GameData {
    Boolean winOrNot;
    int numGuesses;

    GameData(Boolean win, int guesses) {
        winOrNot = win;
        numGuesses = guesses;
    }

    public Boolean getWinOrNot() {
        return winOrNot;
    }
    
    public int getNumGuesses() {
        return numGuesses;
    }
}
