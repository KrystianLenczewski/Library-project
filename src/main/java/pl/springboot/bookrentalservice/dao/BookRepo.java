package pl.springboot.bookrentalservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.springboot.bookrentalservice.dao.entity.Book;

@Repository
public interface BookRepo extends CrudRepository<Book,Long> {
}
