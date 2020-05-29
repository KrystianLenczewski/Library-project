package pl.springboot.bookrentalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class BookRentalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRentalServiceApplication.class, args);
    }

}
