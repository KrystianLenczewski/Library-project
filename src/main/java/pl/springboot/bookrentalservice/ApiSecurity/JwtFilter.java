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
}