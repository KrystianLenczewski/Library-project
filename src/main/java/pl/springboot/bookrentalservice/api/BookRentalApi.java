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
        return bookRentalManager.findByIdBook(index);
    }

    @PostMapping
    public RentalBook addBook(@RequestBody RentalBook book) {
        return bookRentalManager.save(book);
    }

    @PutMapping
    public RentalBook updateBook(@RequestBody RentalBook rentalBook) {
        return bookRentalManager.save(rentalBook);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long index) {
        bookRentalManager.deleteById(index);
    }



}



