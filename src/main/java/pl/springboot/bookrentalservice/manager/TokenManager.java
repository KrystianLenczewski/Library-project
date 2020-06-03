package pl.springboot.bookrentalservice.manager;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;
import pl.springboot.bookrentalservice.dao.modelWrappers.TokenWrapper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class TokenManager {
    public TokenManager() {
    }

    public ResponseEntity<String> getExpirationTime(HttpServletRequest request) throws ParseException {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String result = "c";
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        if (jwtToken != null) {
            String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)

            if (parts.length == 3) {
                result = new String(decoder.decode(parts[1]));

                TokenWrapper tokenAttributes = new Gson().fromJson(result, TokenWrapper.class);

                String epochString = Integer.toString(tokenAttributes.getExp());
                long epoch = Long.parseLong( epochString );
                String expiry= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" ).format(new Date(epoch * 1000L));
                return ResponseEntity
                        .ok()
                        .body(expiry);
            }


        }
        return ResponseEntity
                .badRequest()
                .body("nie podano Tokena");
    }
}
