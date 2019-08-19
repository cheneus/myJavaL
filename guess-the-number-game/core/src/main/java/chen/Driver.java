package chen;

import chen.services.Game;
import chen.services.NumberGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {

    private static final Logger log = LoggerFactory.getLogger(Driver.class);
    private static final String CONFIG_LOCATION = "bean.xml";

    public static void main(String[] args) {
        log.info("Guess The Number Game");

        // create context
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);

        NumberGen numberGen = context.getBean("numberGen", NumberGen.class);

        int number = numberGen.next();
        log.info("test number = {}", number);

        // get bean
        Game game = context.getBean(Game.class);

        game.reset();

        context.close();
    }
}
