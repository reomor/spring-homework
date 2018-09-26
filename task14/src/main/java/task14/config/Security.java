package task14.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import task14.acl.domain.User;
import task14.service.UserService;

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
    public void configure(WebSecurity web) throws Exception {
        // allows access to swagger
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/**", "/swagger-ui.html", "/webjars/**", "/js/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable().and()// allow h2-console
                // store session or else authorization is lost with new request
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                .anonymous().authorities("ROLE_ANONYMOUS")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/books").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/login?logout"))
                .and()
                .authorizeRequests()
                //.antMatchers("/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.POST, "/rest/authors").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/rest/authors/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rest/authors/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/rest/books").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/rest/books/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rest/books/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/rest/books/**/comments").hasAuthority("ROLE_USER")
                .anyRequest().authenticated();
    }
}
