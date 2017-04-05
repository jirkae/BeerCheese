package edu.vse.configuration;

import edu.vse.daos.UserDao;
import edu.vse.filters.BasicAuthFilter;
import edu.vse.filters.JwtFilter;
import edu.vse.filters.LoggingContextFilter;
import edu.vse.filters.RequestLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FiltersConfig {

    @Bean
    public FilterRegistrationBean loggingContextFilter() {
        LoggingContextFilter loggingContextFilter = new LoggingContextFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loggingContextFilter);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean requestLoggingFilter() {
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(requestLoggingFilter);
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public JwtFilter jwtFilter(@Value("${security.jwt.secret.access}") String accessJwtSecret) {
        return new JwtFilter(accessJwtSecret);
    }

    //code smell with lazy init to avoid circular dependency
    @Bean
    public BasicAuthFilter basicAuthFilter(@Lazy AuthenticationManager authenticationManager, UserDao userDao) {
        return new BasicAuthFilter(authenticationManager, userDao);
    }
}
