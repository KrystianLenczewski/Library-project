package pl.springboot.bookrentalservice.api;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.springboot.bookrentalservice.dao.AdminRepo;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;
import pl.springboot.bookrentalservice.manager.AdminManager;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api/login")
public class LoginApi {
    private AdminManager adminManager;

    @Autowired
    public LoginApi(AdminManager adminManager){
        this.adminManager=adminManager;
    }

    @GetMapping
    public String login(@RequestBody UserLibrary userLibrary){
        UserLibrary userToLogin = adminManager.findUserByLoginAndPassword(userLibrary.getLogin(),userLibrary.getPassword());
        if (userToLogin==null)
            return "User not exists in the system";

        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        Long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiSecretKey = "mDIXtIbhh>0NXa5".getBytes();
        Key signingKey = new SecretKeySpec(apiSecretKey,algorithm.getJcaName());

        return Jwts.builder()
                .claim("role","ROLE_"+userToLogin.getRole())
                .claim("login",userToLogin.getLogin())
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis+(60*60000)))
                .signWith(algorithm,signingKey)
                .compact();
    }
}
