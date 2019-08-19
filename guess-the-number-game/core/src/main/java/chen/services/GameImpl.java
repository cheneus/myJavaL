package chen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class GameImpl implements Game {

    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);

    @Autowired
    private NumberGen numberGen;
    private int guessCount = 5;
    private int number;
    private int guess;
    private int smallest;
    private int biggest;
    private int remainingGuess;
    private boolean validNumberRange = true;

    // initialize
    @PostConstruct
    @Override
    public void reset() {
        smallest = 0;
        guess = 0;
        remainingGuess = guessCount;
        biggest = numberGen.getMaxNumber();
        number = numberGen.next();
        log.info("the number(post) is {}", number);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("in Game preDestory()");
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
