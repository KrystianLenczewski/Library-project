package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookRentalManager {
    private BookRentalRepo bookRentalRepo;

    @Autowired
    public BookRentalManager (BookRentalRepo bookRentalRepo){
        this.bookRentalRepo = bookRentalRepo;
    }

    public Iterable<RentalBook> findAll() {
      return  bookRentalRepo.findAll();
    }

    public Optional<RentalBook> findById(Long index) {
       return bookRentalRepo.findById(index);
    }

    public RentalBook save(RentalBook rentalBook) {
        return bookRentalRepo.save(rentalBook);
    }

    public void deleteById(Long index) {
        bookRentalRepo.deleteById(index);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB() {
//        save(new RentalBook(1L,1L,true));
//        save(new RentalBook(2L,2L,false));
//
//    }
}
