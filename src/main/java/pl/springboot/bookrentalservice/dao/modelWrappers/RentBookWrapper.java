package pl.springboot.bookrentalservice.dao.modelWrappers;

public class RentBookWrapper {
    private Long idBook;
    private Long idUser;

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
}
