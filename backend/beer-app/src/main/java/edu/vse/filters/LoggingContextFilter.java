package edu.vse.filters;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;

public class LoggingContextFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            setUpRequestId(servletResponse);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private void setUpRequestId(HttpServletResponse response) {
        String requestId = RandomStringUtils.randomAlphanumeric(12);
        response.setHeader("X-RequestId", requestId);
        MDC.put("requestId", requestId);
    }
}
