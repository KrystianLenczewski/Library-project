package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.BookRepo;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;
import pl.springboot.bookrentalservice.dao.entity.RentalService;
import pl.springboot.bookrentalservice.dao.modelWrappers.SearchWrapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class BookManager {
    private BookRepo bookRepo;
    private BookRentalRepo bookRentalRepo;
    private BookRentalManager bookRentalManager;

    @Autowired
    public BookManager(BookRepo bookRepo, BookRentalRepo bookRentalRepo, BookRentalManager bookRentalManager) {
        this.bookRepo = bookRepo;
        this.bookRentalRepo = bookRentalRepo;
        this.bookRentalManager = bookRentalManager;
    }

    public Optional<Book> findById(Long id) {
        return bookRepo.findById(id);
    }

    public Iterable<Book> findAll() {
        return bookRepo.findAll();
    }

    public ResponseEntity<Object> save(Book book) {

        if(!book.hasNullValue())
        if(book.getId()==null || book.getId()==0  ){
            bookRepo.save(book);
            bookRentalRepo.save(new RentalBook(0L, book.getId(),false));
            return ResponseEntity
                    .ok()
                    .body(book);
        }
         return ResponseEntity
                 .badRequest()
                 .body("wszystkie parametry musza zostac podane");
    }

    public Object deleteById(Long id) {
        Optional<RentalBook> rentalBook = bookRentalManager.findByIdBook(id);

        if(rentalBook.isPresent()) {
            bookRentalRepo.delete(rentalBook.get());
            bookRepo.deleteById(id);
                return 200;
        }
        return 400;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Iterable<Book> search(SearchWrapper searchWrapper) {

        Iterable<Book> books = bookRepo.findAll();
        Stream<Book> bookStream = StreamSupport.stream(books.spliterator(),false);
        String[] params = searchWrapper.getDate().split("-");
        LocalDate localDate=null;
        ArrayList<Integer> dateParams = new ArrayList<Integer>();


        for (String param:params) {
            if(!tryParseInt(param)){
             searchWrapper.setDate(null);
            }
            else{
                dateParams.add(Integer.parseInt(param));
            }
        }
        if(dateParams.size() !=3)
        {
            searchWrapper.setDate(null);
        }
        else
         localDate =  LocalDate.of(dateParams.get(0),dateParams.get(1),dateParams.get(2));

        if (searchWrapper.getAuthor() != null)
        bookStream = bookStream.filter(x -> x.getAuthor().contains(searchWrapper.getAuthor()));
        if (searchWrapper.getTitle() != null)
        bookStream = bookStream.filter(x -> x.getTitle().contains(searchWrapper.getTitle()));
        if (localDate != null) {
            LocalDate finalLocalDate = localDate;
            bookStream = bookStream.filter(x -> x.getProductionYear().equals(finalLocalDate));
        }


        return bookStream.collect(Collectors.toList());
    }

    public Object update(Book book) {
        if(book.getId() != null && book.getId()>0)
        {
            bookRepo.save(book);
            return book;
        }
        return "400";
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB() {
//        save(new Book(1L,"Historia Roja", LocalDate.of(1993,2,2),"Krystian Lenczewski"));
//        save(new Book(2L,"Trzy Wierzby",LocalDate.of(1999,2,2),"Kamil Lenczewski"));
//
//    }
}
