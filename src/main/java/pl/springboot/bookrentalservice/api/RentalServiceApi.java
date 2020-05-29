package pl.springboot.bookrentalservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springboot.bookrentalservice.dao.entity.Book;
import pl.springboot.bookrentalservice.dao.entity.RentalService;
import pl.springboot.bookrentalservice.dao.modelWrappers.RentBookWrapper;
import pl.springboot.bookrentalservice.manager.RentalServiceManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/service")
public class RentalServiceApi {
    private RentalServiceManager rentalServiceManager;

    @Autowired
    public RentalServiceApi(RentalServiceManager rentalServiceManager) {
        this.rentalServiceManager = rentalServiceManager;
    }

    @GetMapping("/all")
    public Iterable<RentalService> getAll() {
        return rentalServiceManager.findAll();
    }

    @GetMapping
    public Optional<RentalService> getById(@RequestParam Long index) {
        return rentalServiceManager.findById(index);
    }

    @PostMapping
    public RentalService addBook(@RequestBody RentalService rentalService) {
        return rentalServiceManager.save(rentalService);
    }

    @PostMapping("/rent")
    public Boolean rentBook(@RequestBody RentBookWrapper rentBookWrapper) {
        return rentalServiceManager.rentBook(rentBookWrapper);
    }

    @PostMapping("/return")
    public Boolean returnBook(@RequestBody RentBookWrapper rentBookWrapper) {
        return rentalServiceManager.returnBook(rentBookWrapper);
    }

    @PutMapping
    public RentalService updateBook(@RequestBody RentalService rentalService) {
        return rentalServiceManager.save(rentalService);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long index) {
        rentalServiceManager.deleteById(index);
    }



}



