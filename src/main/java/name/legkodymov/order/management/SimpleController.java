package name.legkodymov.order.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sergei on 19/08/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@Controller
public class SimpleController {

    @Value("${spring.application.name}")
    String appName;

    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

}
