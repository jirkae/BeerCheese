package edu.vse.filters;

import edu.vse.context.CallContext;
import edu.vse.daos.UserDao;
import edu.vse.models.RoleEntity;
import edu.vse.models.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BasicAuthFilter extends AbstractFilter {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;

    public BasicAuthFilter(AuthenticationManager authenticationManager, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        final String basicAuth = httpServletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.isNoneEmpty(basicAuth)) {
            final String[] loginAndPassword;
            try {
                loginAndPassword = getLoginAndPassword(basicAuth);
            } catch (RuntimeException e) {
                log.info("action=basic-auth status=invalid-header", e);
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginAndPassword[0], loginAndPassword[1]);

            try {
                authenticationManager.authenticate(authenticationToken);
            } catch (AuthenticationException e) {
                log.info("action=basic-auth status=invalid login={}", loginAndPassword[0]);
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            final UserEntity userEntity = userDao.findOne(Example.of(new UserEntity(loginAndPassword[0])));

            //side affect - sets CallContext in thread local
            List<String> roles = userEntity.getRoles().stream().map(RoleEntity::getName).collect(toList());
            setUpCallContext(userEntity.getId(), userEntity.getLogin(), roles);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .filter(StringUtils::isNotEmpty)
                    .map(r -> new SimpleGrantedAuthority(r))
                    .collect(toList());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginAndPassword[0], null, authorities);
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);

            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private String[] getLoginAndPassword(String header) {
        String withoutPrefixHeader = header.substring(6);

        byte[] base64Token = withoutPrefixHeader.getBytes(StandardCharsets.UTF_8);
        byte[] decoded = Base64.decode(base64Token);

        String token = new String(decoded, StandardCharsets.UTF_8);

        int split = token.indexOf(":");
        return new String[]{token.substring(0, split), token.substring(split + 1)};
    }

    private void setUpCallContext(Integer userId, String username, List<String> roles) {
        CallContext callContext = new CallContext(username, userId, roles);
        CallContext.setContext(callContext);
    }
}
