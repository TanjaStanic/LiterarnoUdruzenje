package upp.la;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
// @EnableWebSecurity(debug = true)
@SpringBootApplication
public class LaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaApplication.class, args);
    }
}
