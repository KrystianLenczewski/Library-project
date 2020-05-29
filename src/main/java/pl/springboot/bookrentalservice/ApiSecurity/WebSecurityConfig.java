package pl.springboot.bookrentalservice.ApiSecurity;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/login/").permitAll()
                .antMatchers("/api/books/*").authenticated()
                .antMatchers("/api/admin/*").hasRole("Administrator")
                .and()
                .addFilter(new JwtFilter(authenticationManager()));

    }
}
