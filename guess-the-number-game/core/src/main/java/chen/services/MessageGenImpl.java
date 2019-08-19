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
    private int GuessCount = 5;

    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }

    @Override
    public String getMainMessage() {
        return "Main";
    }

    @Override
    public String getResultMessage() {
        return "Result";
    }
}
