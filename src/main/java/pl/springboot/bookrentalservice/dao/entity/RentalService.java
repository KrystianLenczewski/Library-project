package pl.springboot.bookrentalservice.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class RentalService {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long idBook;
    private Long idUser;
    private LocalDate dateOfRent;
    private LocalDate dateOfReturn;

    public RentalService()
    {

    }

    public RentalService(Long id, Long idBook, Long idUser, LocalDate dateOfRent, LocalDate dateOfReturn) {
        this.id = id;
        this.idBook = idBook;
        this.idUser = idUser;
        this.dateOfRent = dateOfRent;
        this.dateOfReturn = dateOfReturn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(LocalDate dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}
