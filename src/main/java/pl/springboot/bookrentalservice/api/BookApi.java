package pl.springboot.bookrentalservice.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.modelWrappers.SearchWrapper;
import pl.springboot.bookrentalservice.manager.BookManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookApi {


    private BookManager bookManager ;

    @Autowired
    public BookApi(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    @GetMapping("/all")
    public Iterable<Book> getAll() {

        return bookManager.findAll();
    }

    @GetMapping
    public Optional<Book> getById(@RequestParam Long index) {
        return bookManager.findById(index);
    }

    @PostMapping
    public Object addBook(@RequestBody Book book) {

        return bookManager.save(book);
    }

    @PutMapping
    public Object updateBook(@RequestBody Book book) {
        return bookManager.update(book);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long id) {
        bookManager.deleteById(id);
    }

    @GetMapping("/search")
    public Iterable<Book> serach (SearchWrapper searchWrapper){
        return bookManager.search(searchWrapper);
    }

}
