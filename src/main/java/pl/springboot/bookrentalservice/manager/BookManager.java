package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.BookRepo;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;
import pl.springboot.bookrentalservice.dao.entity.RentalService;
import pl.springboot.bookrentalservice.dao.modelWrappers.SearchWrapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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


    public Iterable<Book> search(SearchWrapper searchWrapper) {

        Iterable<Book> books = bookRepo.findAll();
        Stream<Book> bookStream = StreamSupport.stream(books.spliterator(),false);
        ;
        if (searchWrapper.getAuthor() != null)
        bookStream = bookStream.filter(x -> x.getAuthor().equals(searchWrapper.getAuthor()));
        if (searchWrapper.getTitle() != null)
        bookStream = bookStream.filter(x -> x.getTitle().equals(searchWrapper.getTitle()));
        if (searchWrapper.getDate() != null)
        bookStream = bookStream.filter(x -> x.getProductionYear()==(searchWrapper.getDate()));


        return bookStream.collect(Collectors.toList());
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB() {
//        save(new Book(1L,"Historia Roja", LocalDate.of(1993,2,2),"Krystian Lenczewski"));
//        save(new Book(2L,"Trzy Wierzby",LocalDate.of(1999,2,2),"Kamil Lenczewski"));
//
//    }
}
