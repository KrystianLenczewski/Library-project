package pl.springboot.bookrentalservice.dao.modelWrappers;

import java.time.LocalDate;

public class SearchWrapper {
    String author;
    String title;
    LocalDate date;
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
