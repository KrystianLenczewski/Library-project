package pl.springboot.bookrentalservice.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import pl.springboot.bookrentalservice.manager.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/api/token")
public class TokenApi {

    TokenManager tokenManager;
    @Autowired
    public TokenApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @GetMapping
    public ResponseEntity<String> getExpirationTime (HttpServletRequest request) throws ParseException {
       return tokenManager.getExpirationTime(request);
    }
}