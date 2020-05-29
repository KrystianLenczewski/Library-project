package pl.springboot.bookrentalservice.dao;

import org.springframework.data.repository.CrudRepository;
import pl.springboot.bookrentalservice.dao.entity.RentalService;

public interface RentalServiceRepo extends CrudRepository<RentalService, Long> {
}
