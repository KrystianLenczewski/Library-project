package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.api.BookRentalApi;
import pl.springboot.bookrentalservice.dao.RentalServiceRepo;

import pl.springboot.bookrentalservice.dao.entity.RentalService;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalServiceManager {

    private RentalServiceRepo rentalServiceRepo;
   // private BookRentalApi bookRentalApi;

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
}
