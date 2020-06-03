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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public Optional<RentalBook> findByIdBook(Long index) {
        Iterable<RentalBook> rentalBooks = bookRentalRepo.findAll();

        return StreamSupport.stream(rentalBooks.spliterator(),false)
                .filter(x -> x.getIdBook().equals(index))
                .findFirst();

    }

    public RentalBook save(RentalBook rentalBook) {
        return bookRentalRepo.save(rentalBook);
    }

    public void deleteById(Long index) {
        bookRentalRepo.deleteById(index);
    }


}
