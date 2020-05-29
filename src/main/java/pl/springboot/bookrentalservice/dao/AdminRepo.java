package pl.springboot.bookrentalservice.dao;

import org.springframework.data.repository.CrudRepository;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;

public interface AdminRepo extends CrudRepository<UserLibrary,Long> {
}
