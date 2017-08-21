package name.legkodymov.order.management.persistence;

import name.legkodymov.order.management.persistence.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sergei on 19/08/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
