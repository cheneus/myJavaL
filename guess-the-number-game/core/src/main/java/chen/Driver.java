package chen;

import chen.config.AppConfig;
import chen.services.Game;
import chen.services.NumberGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {

    private static final Logger log = LoggerFactory.getLogger(Driver.class);
    private static final String CONFIG_LOCATION = "bean.xml";

    public static void main(String[] args) {
        log.info("Guess The Number Game");

        // create context
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        NumberGen numberGen = context.getBean(NumberGen.class);
        Game game = context.getBean(Game.class);
        log.info("Driver Starting");
        int number = numberGen.next();

        log.info("The number is  {}", number);
        // close context
        context.close();
    }
}
