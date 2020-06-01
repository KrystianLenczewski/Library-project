package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.BookRentalRepo;
import pl.springboot.bookrentalservice.dao.RentalServiceRepo;

import pl.springboot.bookrentalservice.dao.entity.RentalBook;
import pl.springboot.bookrentalservice.dao.entity.RentalService;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;
import pl.springboot.bookrentalservice.dao.modelWrappers.RentBookWrapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class RentalServiceManager {

    private RentalServiceRepo rentalServiceRepo;
    private BookRentalRepo bookRentalRepo;
    private BookRentalManager bookRentalManager;
    private AdminManager adminManager;

    @Autowired
    private RentalServiceManager(RentalServiceRepo rentalServiceRepo, BookRentalRepo bookRentalRepo, BookRentalManager bookRentalManager, AdminManager adminManager) {
        this.rentalServiceRepo = rentalServiceRepo;
        this.bookRentalRepo = bookRentalRepo;
        this.bookRentalManager = bookRentalManager;
        this.adminManager = adminManager;
    }

    public Iterable<RentalService> findAll() {
        return rentalServiceRepo.findAll();
    }

    public Optional<RentalService> findById(Long index) {
        return rentalServiceRepo.findById(index);
    }

    public Optional<RentalService> findByUserAndBook(Long book, Long user) {
        Iterable<RentalService> rentalServices = rentalServiceRepo.findAll();
        return StreamSupport.stream(rentalServices.spliterator(),false)
                .filter(x -> x.getIdBook().equals(book))
                .filter(x -> x.getIdUser().equals(user))
                .filter(x -> x.getDateOfReturn() == null)
                .findFirst();
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

    public Object rentBook(RentBookWrapper rentBookWrapper) {
        Optional<RentalBook> rentalBook = bookRentalManager.findByIdBook(rentBookWrapper.getIdBook());
        //getting user here

        if(rentalBook.isPresent() && !rentalBook.get().isRent()){

            rentalBook.get().setRent(true);
            bookRentalRepo.save(rentalBook.get());

            RentalService rentalService = new RentalService(0L, rentalBook.get().getIdBook().longValue(),rentBookWrapper.getIdUser() ,LocalDate.now(),null  );
             rentalServiceRepo.save(rentalService);
             return 200;
        }
        return 400;
    }

    public Boolean returnBook(RentBookWrapper rentBookWrapper) {
        Optional<RentalBook> rentalBook = bookRentalManager.findByIdBook(rentBookWrapper.getIdBook());
        Optional<UserLibrary> user = adminManager.findUsersById(rentBookWrapper.getIdUser());


        if(rentalBook.isPresent() && user.isPresent() && rentalBook.get().isRent()){
            {
                Optional<RentalService> rentalService = this.findByUserAndBook(rentalBook.get().getIdBook(),user.get().getId());


                if(rentalService.isPresent())
                {
                    rentalService.get().setDateOfReturn(LocalDate.now());
                    rentalBook.get().setRent(false);

                    rentalServiceRepo.save(rentalService.get());
                    bookRentalRepo.save(rentalBook.get());
                    return true;
                }

            }}

        return false;
    }



}
