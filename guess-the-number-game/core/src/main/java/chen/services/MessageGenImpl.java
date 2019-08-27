package chen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageGenImpl implements MessageGen {
    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);

    @Autowired
    private Game game;
    private int guessCount = 5;

    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }

    @Override
    public String getMainMessage() {
        return "Number is between " + game.getSmallest() + " and " + game.getBiggest() + " Can you guess it?";
    }

    @Override
    public String getResultMessage() {
        if (game.isGameWon()) {
            return "You guessed it, the number is {}" + game.getNumber();
        } else if  (game.isGameLost()) {
            return "You lost";
        } else if (game.isValidNumRange()) {
            return "Please input a valid number range";
        } else if (game.getRemainingGuesses() == guessCount) {
            return "What is your first guess";
        } else {
            String direction = "Lower";
            if (game.getGuess() < game.getNumber()) {
                direction = "Higher";
            }
            return direction + "!, You have" + game.getRemainingGuesses() + " guess left";
        }
    }

}
