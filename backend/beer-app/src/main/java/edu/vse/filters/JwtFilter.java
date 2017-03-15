package edu.vse.filters;

import edu.vse.context.CallContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JwtFilter extends AbstractFilter {

    private static final String X_AUTH = "X-Auth";
    private static final String X_USERNAME = "X-Username";
    private static final String X_USER = "X-User";
    private static final String X_ROLES = "X-Roles";
    private static final String X_EXPIRATION = "X-Expiration";

    private final String accessJwtSecret;

    public JwtFilter(String accessJwtSecret) {
        this.accessJwtSecret = accessJwtSecret;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        final String xAuthValue = httpServletRequest.getHeader(X_AUTH);

        if (xAuthValue != null) {
            Jwt<Header, Claims> accessJwt = Jwts.parser().setSigningKey(accessJwtSecret).parse(xAuthValue);
            final Integer userId = accessJwt.getBody().get(X_USER, Integer.class);
            final String username = accessJwt.getBody().get(X_USERNAME, String.class);
            final String roles = accessJwt.getBody().get(X_ROLES, String.class);
            final Date expirationDate = accessJwt.getBody().get(X_EXPIRATION, Date.class);

            MDC.put("userId", userId.toString());
            if (expirationDate.before(new Date())) {
                log.info("action=jwt status=expired-access-token");
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            //side affect - sets CallContext in thread local
            setUpCallContext(userId, username, roles);

            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                    .filter(StringUtils::isNotEmpty)
                    .map(r -> new SimpleGrantedAuthority(r))
                    .collect(toList());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void setUpCallContext(Integer userId, String username, String roles) {
        CallContext callContext = new CallContext(username, userId, Arrays.asList(roles.split(",")));
        CallContext.setContext(callContext);
    }
}
