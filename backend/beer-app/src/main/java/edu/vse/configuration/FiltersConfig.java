package edu.vse.configuration;

import edu.vse.filters.LoggingContextFilter;
import edu.vse.filters.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
