package chen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameImpl implements Game {

    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);

    public GameImpl(NumberGen numberGen) {
        this.numberGen = numberGen;
    }

    private NumberGen numberGen;
    private int guessCount = 5;
    private int number;
    private int guess;
    private int smallest;
    private int biggest;
    private int remainingGuess;
    private boolean validNumberRange = true;

    @Override
    public void reset() {
        smallest = 0;
        guess = 0;
        remainingGuess = guessCount;
        biggest = numberGen.getMaxNumber();
        number = numberGen.next();
        log.debug("the number is {}", number);
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public int getGuess() {
        return 0;
    }

    @Override
    public int getSmallest() {
        return 0;
    }

    @Override
    public int getBiggest() {
        return 0;
    }

    @Override
    public void setGuess(int guess) {

    }

    @Override
    public void check() {
        checkValidNumRange();

        if (validNumberRange) {
            if (guess > number) {
                biggest = guess - 1;
            }
            if (guess < number) {
                biggest = guess + 1;
            }
        }

        remainingGuess--;
    }

    @Override
    public boolean isValidNumRange() {
        return false;
    }

    @Override
    public boolean isGameWon() {

        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuess <= 0;
    }

    //private method
    private void checkValidNumRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
