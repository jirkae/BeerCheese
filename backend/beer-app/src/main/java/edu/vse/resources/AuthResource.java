package edu.vse.resources;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.daos.TokenDao;
import edu.vse.daos.UserDao;
import edu.vse.dtos.Login;
import edu.vse.models.TokenEntity;
import edu.vse.models.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthResource {

    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);

    private static final long FIVE_MINUTES = 300 * 1000;
    private static final int EXPIRATION_IN_HOURS = 6;
    private static final String X_AUTH = "X-Auth";
    private static final String X_TOKEN = "X-Token";
    private static final String X_ROLES = "X-Roles";
    private static final String X_USER = "X-User";
    private static final String X_USERNAME = "X-Username";
    private static final String X_EXPIRATION = "X-Expiration";

    private final String accessJwtSecret;
    private final String refreshJwtSecret;
    private final String domain;
    private final AuthenticationManager authenticationManager;
    private final TokenDao tokenDao;
    private final UserDao userDao;

    @Autowired
    public AuthResource(@Value("${security.jwt.secret.access}") String accessJwtSecret,
                        @Value("${security.jwt.secret.refresh}") String refreshJwtSecret,
                        @Value("${security.domain}") String domain,
                        AuthenticationManager authenticationManager,
                        TokenDao tokenDao,
                        UserDao userDao) {
        this.accessJwtSecret = accessJwtSecret;
        this.refreshJwtSecret = refreshJwtSecret;
        this.domain = domain;
        this.authenticationManager = authenticationManager;
        this.tokenDao = tokenDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity<Void> login(@RequestBody Login login, ServletResponse servletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("action=invalid-login username={}", login.getUsername());
            return ResponseEntity.status(UNAUTHORIZED).build();
        }
        final UserEntity userEntity = userDao.findOne(Example.of(new UserEntity(login.getUsername())));

        Date expiration = new Date(new Date().getTime() + FIVE_MINUTES);

        String token = createToken(userEntity, expiration);
        setXAuthCookie(httpServletResponse, (int) expiration.getTime(), token);
        return ResponseEntity.status(OK).build();
    }

    @RequestMapping(value = "/logout", method = DELETE)
    @Transactional
    public ResponseEntity<Void> logout(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        final String xAuthValue = httpServletRequest.getHeader(X_AUTH);

        if (xAuthValue != null) {
            Jwt<Header, Claims> accessJwt = Jwts.parser().setSigningKey(accessJwtSecret).parse(xAuthValue);

            final Integer accessUserId = accessJwt.getBody().get(X_USER, Integer.class);
            final String refreshToken = accessJwt.getBody().get(X_TOKEN, String.class);

            tokenDao.deleteAllByUserAndToken(accessUserId, refreshToken);
            setXAuthCookie(httpServletResponse, 0, "");
            log.info("action=logout-attempt status=success");
            return ResponseEntity.status(OK).build();
        } else {
            log.info("action=logout-attempt status=failed reason=no-auth-header");
            return ResponseEntity.status(UNAUTHORIZED).build();
        }
    }

    @RequestMapping(value = "/token", method = GET)
    public ResponseEntity<Void> refreshToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        final String xAuthValue = httpServletRequest.getHeader(X_AUTH);
        final Date currentDate = new Date();

        if (xAuthValue != null) {
            Jwt<Header, Claims> accessJwt = Jwts.parser().setSigningKey(accessJwtSecret).parse(xAuthValue);

            final Integer accessUserId = accessJwt.getBody().get(X_USER, Integer.class);
            final String refreshToken = accessJwt.getBody().get(X_TOKEN, String.class);

            Jwt<Header, Claims> refreshJwt = Jwts.parser().setSigningKey(refreshJwtSecret).parse(refreshToken);
            final Integer refreshUserId = refreshJwt.getBody().get(X_USER, Integer.class);

            if (!refreshUserId.equals(accessUserId)) {
                log.info("action=refresh-attempt status=failed reason=user-ids-are-not-equal");
                return ResponseEntity.status(UNAUTHORIZED).build();
            }

            final Date expiration = accessJwt.getBody().get(X_EXPIRATION, Date.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expiration);
            calendar.add(Calendar.MINUTE, 5);
            final Date expirationWithGracePeriod = calendar.getTime();
            if (expirationWithGracePeriod.before(currentDate)) {
                log.info("action=refresh-attempt status=failed reason=after-expiration-plus-grace-period grace-period={}", FIVE_MINUTES);
                return ResponseEntity.status(UNAUTHORIZED).build();
            }

            Optional<TokenEntity> maybeToken = tokenDao.findByUserAndToken(accessUserId, refreshToken);
            if (maybeToken.isPresent()) {
                TokenEntity token = maybeToken.get();

                if (token.getExpiration().after(currentDate)) {
                    Date newAccessExpiration = new Date(new Date().getTime() + FIVE_MINUTES);

                    setXAuthCookie(httpServletResponse, (int) newAccessExpiration.getTime(), refreshToken);

                    log.info("action=refresh-attempt status=success");
                    return ResponseEntity.status(OK).build();
                } else {
                    log.info("action=refresh-attempt status=failed reason=persistent-refresh-token-is-not-valid");
                    return ResponseEntity.status(UNAUTHORIZED).build();
                }
            } else {
                log.info("action=refresh-attempt status=failed reason=no-persistent-refresh-token");
                return ResponseEntity.status(UNAUTHORIZED).build();
            }
        } else {
            log.info("action=refresh-attempt status=failed reason=no-auth-header");
            return ResponseEntity.status(UNAUTHORIZED).build();
        }
    }

    private String createToken(UserEntity user, Date expiration) {
        // token for refresh
        Date created = new Date();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(X_USER, user.getId());
        claims.put(X_USERNAME, user.getLogin());

        String xToken = Jwts.builder()
                .signWith(HS512, refreshJwtSecret)
                .setSubject(user.getLogin())
                .setClaims(claims)
                .setIssuedAt(created)
                .compact();
        Calendar expirationCalendar = Calendar.getInstance();
        expirationCalendar.setTime(created);
        expirationCalendar.add(Calendar.HOUR, EXPIRATION_IN_HOURS);

        // save token for refresh
        TokenEntity xTokenEntity = new TokenEntity(user.getId(), xToken, created, expirationCalendar.getTime());
        tokenDao.saveAndFlush(xTokenEntity);

        return createToken(user, expiration, xToken);
    }

    private String createToken(UserEntity user, Date expiration, String xToken) {
        Date currentDate = new Date();
        String roles = String.join(",", user.getRoles().stream().map(r -> r.getName()).collect(toList()));

        // claims: refresh token, roles and user id
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(X_TOKEN, xToken);
        claims.put(X_ROLES, roles);
        claims.put(X_USER, user.getId());
        claims.put(X_USERNAME, user.getLogin());
        claims.put(X_EXPIRATION, expiration);

        // stateless token
        String token = Jwts.builder()
                .setIssuedAt(currentDate)
                .setSubject(user.getLogin())
                .setClaims(claims)
                .signWith(HS512, accessJwtSecret)
                .compact();

        return token;
    }

    private void setXAuthCookie(HttpServletResponse httpServletResponse, int expiration, String token) {
        Cookie tokenCookie = new Cookie(X_AUTH, token);
        tokenCookie.setMaxAge(expiration);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setDomain(domain);

        httpServletResponse.addCookie(tokenCookie);
    }
}
