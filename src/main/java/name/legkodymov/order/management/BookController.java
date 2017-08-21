package name.legkodymov.order.management;

import name.legkodymov.order.management.persistence.Book;
import name.legkodymov.order.management.persistence.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sergei on 20/08/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) throws BookNotFoundException {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws BookNotFoundException {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new BookNotFoundException();
        }
        bookRepository.delete(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException, BookNotFoundException {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        Book oldBook = bookRepository.findOne(id);
        if (oldBook == null) {
            throw new BookNotFoundException();
        }
        return bookRepository.save(book);
    }

}
