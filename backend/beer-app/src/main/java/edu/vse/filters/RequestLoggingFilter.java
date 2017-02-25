package edu.vse.filters;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLoggingFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = currentTimeMillis();
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String requestURI = getRequestURI(servletRequest);
        try {
            if (log.isDebugEnabled()) {
                log.debug("action=processing-request status=start uri={} method={}", requestURI, servletRequest.getMethod());
            }
            chain.doFilter(request, response);
        } finally {
            log.info("action=processing-request status=end uri={} method={} httpStatus={} time={}", requestURI,
                    servletRequest.getMethod(), servletResponse.getStatus(), currentTimeMillis() - start);
        }
    }

    private String getRequestURI(HttpServletRequest servletRequest) {
        if (servletRequest.getQueryString() == null) {
            return servletRequest.getRequestURI();
        }
        return servletRequest.getRequestURI() + servletRequest.getQueryString();
    }
}
