package name.legkodymov.order.management;

/**
 * Created by sergei on 05/07/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
public class Order {

    private Long id;

    private String code;

    Order(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
