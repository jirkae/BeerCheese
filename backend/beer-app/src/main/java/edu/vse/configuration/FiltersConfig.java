package edu.vse.configuration;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.vse.filters.LoggingContextFilter;
import edu.vse.filters.RequestLoggingFilter;

@Configuration
public class FiltersConfig {

    @Bean
    public Filter loggingContextFilter() {
        return new LoggingContextFilter();
    }

    @Bean
    public Filter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

}
