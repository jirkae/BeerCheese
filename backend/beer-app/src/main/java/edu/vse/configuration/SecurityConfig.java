package edu.vse.configuration;

import edu.vse.daos.UserDao;
import edu.vse.filters.BasicAuthFilter;
import edu.vse.filters.JwtFilter;
import edu.vse.services.SecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import static org.springframework.boot.autoconfigure.security.SecurityProperties.ACCESS_OVERRIDE_ORDER;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Order(ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${env.production}")
    private boolean inProduction;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private BasicAuthFilter basicAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // only prod needs SSL
        if (inProduction) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
        // disable CSRF
        http.csrf().disable();

        // disable caching
        http.headers().cacheControl();

        // disable session creation
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // add JwtFilter after SecurityContextPersistenceFilter, so context is not overwritten by the new context
        http.addFilterAfter(jwtFilter, SecurityContextPersistenceFilter.class);
        http.addFilterAfter(basicAuthFilter, JwtFilter.class);

        // set access rules
        http.authorizeRequests()
                .requestMatchers(r -> r.getRequestURI().startsWith("/images/") && r.getMethod().equals(POST.name())).hasAuthority("admin")
                .requestMatchers(r -> r.getRequestURI().startsWith("/api/products/") && r.getMethod().equals(POST.name())).hasAuthority("admin")
                .requestMatchers(r -> r.getRequestURI().startsWith("/api/orders/") && r.getMethod().equals(PUT.name())).hasAuthority("admin")
                .requestMatchers(r -> r.getRequestURI().startsWith("/api/supplier/") && r.getMethod().equals(PUT.name())).hasAuthority("admin")
                .requestMatchers(r -> r.getRequestURI().startsWith("/api/supplier/") && r.getMethod().equals(POST.name())).hasAuthority("admin")
                .antMatchers("/api/users/current").authenticated()
                .antMatchers("/api/users").hasAuthority("admin")
                .antMatchers("/api/messages").hasAuthority("admin")
                .antMatchers("/api/addresses**").authenticated()
                .antMatchers("/api/packages**").authenticated()
                .antMatchers("/api/orders**").authenticated()
                .antMatchers("/api/packages**").authenticated()
                .antMatchers("/api/ping/secure").authenticated()
                .antMatchers("/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceInit());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsServiceInit() {
        return new SecurityUserDetailService(userDao);
    }
}
