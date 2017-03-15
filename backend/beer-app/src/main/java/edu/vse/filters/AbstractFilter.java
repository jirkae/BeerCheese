package edu.vse.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

abstract class AbstractFilter implements Filter {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public void destroy() {

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("action=init");
    }
}
