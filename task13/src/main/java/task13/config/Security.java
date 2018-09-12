package task13.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import task13.domain.User;
import task13.service.UserService;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    // @Lazy prevents from cycle dependency
    public Security(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String email = (String) authentication.getPrincipal();
                String providedPassword = (String) authentication.getCredentials();
                User authenticatedUser = userService.findAndAuthenticate(email, providedPassword);
                if (authenticatedUser == null) {
                    throw new BadCredentialsException("Username/Password does not match for " + email);
                }
                return new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().loginPage("/login").permitAll()
                //.loginPage()
                .defaultSuccessUrl("/books").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .anyRequest().authenticated();
    }
}
