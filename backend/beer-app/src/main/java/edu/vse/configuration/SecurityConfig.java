package edu.vse.configuration;

import static org.springframework.boot.autoconfigure.security.SecurityProperties.ACCESS_OVERRIDE_ORDER;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${env.production}")
    private boolean inProduction;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // only prod needs SSL
        if (inProduction) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
        // disable CSRF
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/account/login").permitAll()
                .antMatchers("/api/ping").permitAll()
                .antMatchers("/api/**").permitAll();
//                .antMatchers("/api/**").authenticated();
    }

}
