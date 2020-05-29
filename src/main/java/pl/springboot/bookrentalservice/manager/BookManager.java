package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.BookRepo;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookManager {
    private BookRepo bookRepo;
    private BookRentalRepo bookRentalRepo;

    @Autowired
    public BookManager(BookRepo bookRepo, BookRentalRepo bookRentalRepo) {
        this.bookRepo = bookRepo;
        this.bookRentalRepo = bookRentalRepo;
    }

    public Optional<Book> findById(Long id) {
        return bookRepo.findById(id);
    }

    public Iterable<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book save(Book videoCassette) {
        bookRentalRepo.save(new RentalBook(0L, videoCassette.getId(),false));
        return bookRepo.save(videoCassette);
    }

    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB() {
//        save(new Book(1L,"Historia Roja", LocalDate.of(1993,2,2),"Krystian Lenczewski"));
//        save(new Book(2L,"Trzy Wierzby",LocalDate.of(1999,2,2),"Kamil Lenczewski"));
//
//    }
}
