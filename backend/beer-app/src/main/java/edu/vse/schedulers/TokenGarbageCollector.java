package edu.vse.schedulers;

import edu.vse.daos.TokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Eagerly initialize({@link Lazy} set to `false`) and make transactional({@link Transactional}).
 *
 * Purpose of this bean is to garbage collect unnecessary tokens in database.
 */
@Lazy(false)
@Component
@Transactional
public class TokenGarbageCollector {

    private static final Logger log = LoggerFactory.getLogger(TokenGarbageCollector.class);
    private static final int ONE_HOUR = 3600 * 1000;

    private final TokenDao tokenDao;

    @Autowired
    public TokenGarbageCollector(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Scheduled(fixedRate = ONE_HOUR)
    public void garbageCollectOldTokens() {
        Calendar expirationCalendar = Calendar.getInstance();
        expirationCalendar.setTime(new Date());
        expirationCalendar.add(5, Calendar.MINUTE);

        int deleted = tokenDao.deleteAllByExpirationBefore(expirationCalendar.getTime());
        log.info("action=token-garbage-collect deleted={}", deleted);
    }
}
