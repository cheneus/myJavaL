package chen.config;

import chen.services.*;
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

    @Bean
    public MessageGen messageGen() {
        return new MessageGenImpl();
    }
}
