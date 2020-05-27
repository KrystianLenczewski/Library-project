package pl.springboot.bookrentalservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;
import pl.springboot.bookrentalservice.manager.BookRentalManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/rental")
public class BookRentalApi {
    private BookRentalManager bookRentalManager ;

    @Autowired
    public BookRentalApi(BookRentalManager bookRentalManager) {
        this.bookRentalManager = bookRentalManager;
    }

    @GetMapping("/all")
    public Iterable<RentalBook> getAll() {
        return bookRentalManager.findAll();
    }

    @GetMapping
    public Optional<RentalBook> getById(@RequestParam Long index) {
        return bookRentalManager.findById(index);
    }

//    @PostMapping
//    public Book addBook(@RequestBody Book book) {
//        return libraryManager.save(book);
//    }
//
//    @PutMapping
//    public Book updateBook(@RequestBody Book book) {
//        return libraryManager.save(book);
//    }
//
//    @DeleteMapping
//    public void deleteBook(@RequestParam Long index) {
//        libraryManager.deleteById(index);
//    }



}



