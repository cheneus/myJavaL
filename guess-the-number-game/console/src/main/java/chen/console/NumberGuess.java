package chen.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NumberGuess {
    private static final Logger log = LoggerFactory.getLogger(NumberGuess.class);


    @EventListener // you can rename is needed
    public void start(ContextRefreshedEvent event) {
        log.info("Container ready to use");
    }
}
