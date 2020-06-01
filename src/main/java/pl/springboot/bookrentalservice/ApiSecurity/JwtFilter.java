package pl.springboot.bookrentalservice.ApiSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.springboot.bookrentalservice.manager.AdminManager;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class JwtFilter extends BasicAuthenticationFilter {


    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getServletPath();
        if (url.equals("/api/login/"))
            chain.doFilter(request,response);

        String header = request.getHeader("Authorization");
        if (header!=null) {
            UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
            if (authResult!=null) {
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request,response);
            }
            else
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No permissions");
        }
        else
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No permissions");
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("mDIXtIbhh>0NXa5".getBytes())
                    .parseClaimsJws(header.replace("Bearer ",""));

            String username = claimsJws.getBody().get("login").toString();
            String role = claimsJws.getBody().get("role").toString();

            Set<SimpleGrantedAuthority> roleCollection = Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username,null ,roleCollection);
            return result;
        }
        catch (Exception e){
            return null;
        }

    }

    public void decodeUsernameAndRoleFromJWT (HttpServletRequest request){
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOiJST0xFX0FETUlOIiwiaXNzIjoibXlzZWxmIiwiZXhwIjoxNDcxMDg2MzgxfQ.1EI2haSz9aMsHjFUXNVz2Z4mtC0nMdZo6bo3-x-aRpw";
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);

    }
}