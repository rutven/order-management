package name.legkodymov.order.management;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergei on 05/07/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@RestController
public class OrderController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/order")
    public Order order(@RequestParam(value = "code", defaultValue = "ABCD") String code) {
        return new Order(counter.incrementAndGet(), code);
    }
}
