package chen.services;

public interface Game {
    int getNumber();
    int getGuess();
    int getSmallest();
    int getBiggest();
    void setGuess(int guess);

    void reset();
    void check();

    boolean isValidNumRange();
    boolean isGameWon();
    boolean isGameLost();

    int getRemainingGuesses();
}
