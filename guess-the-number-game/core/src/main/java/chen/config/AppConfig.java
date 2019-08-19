package chen.config;

import chen.services.Game;
import chen.services.GameImpl;
import chen.services.NumberGen;
import chen.services.NumberGenImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "chen.config")
public class AppConfig {

    @Bean
    public NumberGen numberGen() {
        return new NumberGenImpl();
    }

    @Bean
    public Game game() {
        return new GameImpl();
    }
}
