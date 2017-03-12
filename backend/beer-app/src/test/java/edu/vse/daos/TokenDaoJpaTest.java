package edu.vse.daos;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.TokenEntity;

public class TokenDaoJpaTest extends AbstractAppJpaTest {
    private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private TokenDao tokenDao;

    @Test
    public void testSave() throws Exception {
        TokenEntity tokenEntity = new TokenEntity(2, "6sKz6QL4UKmLT5AMK2uFXduE0qTbJNUz", new Date(), new Date());

        tokenDao.saveAndFlush(tokenEntity);
    }

    @Test
    public void testFindByUserAndToken() throws Exception {
        Optional<TokenEntity> maybeToken = tokenDao.findByUserAndToken(1, "Lut9VKxv6nUWAAWI2yCvdD0z7FVTBQ5x");

        assertTrue(maybeToken.isPresent());

        TokenEntity token = maybeToken.get();
        assertThat(token, allOf(
                hasProperty("user", is(1)),
                hasProperty("token", is("Lut9VKxv6nUWAAWI2yCvdD0z7FVTBQ5x")),
                hasProperty("created", notNullValue()),
                hasProperty("expiration", notNullValue())
        ));

        Calendar calExpected = Calendar.getInstance();
        Calendar calEntity = Calendar.getInstance();
        calExpected.setTime(df.parse("20181201"));

        calEntity.setTime(token.getCreated());
        assertEquals(calExpected.get(Calendar.MONTH), calEntity.get(Calendar.MONTH));
        assertEquals(calExpected.get(Calendar.DATE), calEntity.get(Calendar.DATE));
        assertEquals(calExpected.get(Calendar.YEAR), calEntity.get(Calendar.YEAR));

        calEntity.setTime(token.getExpiration());
        assertEquals(calExpected.get(Calendar.MONTH), calEntity.get(Calendar.MONTH));
        assertEquals(calExpected.get(Calendar.DATE), calEntity.get(Calendar.DATE));
        assertEquals(calExpected.get(Calendar.YEAR), calEntity.get(Calendar.YEAR));
    }

    @Test
    public void testFindByUserAndTokenNotFound() throws Exception {
        Optional<TokenEntity> maybeToken = tokenDao.findByUserAndToken(3, "Lut9VKxv6nUWAAWI2yCvdD0z7FVTBQ5x");

        assertFalse(maybeToken.isPresent());
    }

    @Test
    public void testDeleteAllByExpirationBefore() throws Exception {
        List<TokenEntity> all = tokenDao.findAll();
        int deleted = tokenDao.deleteAllByExpirationBefore(new Date());
        List<TokenEntity> afterDelete = tokenDao.findAll();

        assertTrue(all.size() > afterDelete.size());
        assertTrue(all.size() - afterDelete.size() == deleted);
    }

    @Test
    public void testDeleteAllByUserAndToken() throws Exception {
        tokenDao.deleteAllByUserAndToken(3, "test");
    }
}
