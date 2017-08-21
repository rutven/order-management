package name.legkodymov.order.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by sergei on 05/07/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@EnableJpaRepositories("name.legkodymov.order.management.persistence")
@EntityScan("name.legkodymov.order.management.persistence")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
