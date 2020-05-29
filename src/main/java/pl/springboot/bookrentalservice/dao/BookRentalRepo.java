package pl.springboot.bookrentalservice.dao;

import org.springframework.data.repository.CrudRepository;
import pl.springboot.bookrentalservice.dao.entity.RentalBook;

public interface BookRentalRepo extends CrudRepository<RentalBook, Long> {
}
