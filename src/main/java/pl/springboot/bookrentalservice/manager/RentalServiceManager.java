package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.api.BookRentalApi;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.RentalServiceRepo;

import pl.springboot.bookrentalservice.dao.entity.RentalBook;
import pl.springboot.bookrentalservice.dao.entity.RentalService;
import pl.springboot.bookrentalservice.dao.modelWrappers.RentBookWrapper;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalServiceManager {

    private RentalServiceRepo rentalServiceRepo;
    private BookRentalRepo bookRentalRepo;

    @Autowired
    private RentalServiceManager(RentalServiceRepo rentalServiceRepo) {
        this.rentalServiceRepo = rentalServiceRepo;
    }

    public Iterable<RentalService> findAll() {
        return rentalServiceRepo.findAll();
    }

    public Optional<RentalService> findById(Long index) {
        return rentalServiceRepo.findById(index);
    }

    public RentalService save(RentalService rentalService) {
        return rentalServiceRepo.save(rentalService);
    }

    public void deleteById(Long index) {
        rentalServiceRepo.deleteById(index);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        save(new RentalService(1L,1L,1L, LocalDate.now(),LocalDate.now()));

    }

    public RentalService rentBook(RentBookWrapper rentBookWrapper) {
        System.out.println(rentBookWrapper.getIdBook());
        System.out.println(rentBookWrapper.getIdUser());

        Optional<RentalBook> rentalBook = bookRentalRepo.findById(rentBookWrapper.getIdBook());
        System.out.println(rentalBook);
        //getting user here
        RentalService rentalService = new RentalService(1L, rentalBook.get().getIdBook().longValue(),rentalBook.get().getIdBook().longValue() ,LocalDate.now(),LocalDate.now());
        return rentalServiceRepo.save(rentalService);
    }
}
