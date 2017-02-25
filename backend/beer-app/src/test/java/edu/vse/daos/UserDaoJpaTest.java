package edu.vse.daos;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.UserEntity;

public class UserDaoJpaTest extends AbstractAppJpaTest {

    private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private UserDao userDao;


    @Before
    public void setUp() throws Exception {
        df.setLenient(false);
    }

    @Test
    public void testFindOne() throws Exception {
        UserEntity userEntity = userDao.findOne(1);

        assertThat(userEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("login", is("dummy")),
                        hasProperty("firstName", is("Jan")),
                        hasProperty("lastName", is("Jiri")),
                        hasProperty("phoneNumber", is("+420777888777")),
                        hasProperty("email", is("dummy@dummy.io"))
                )
        );

        Calendar calExpected = Calendar.getInstance();
        Calendar calEntity = Calendar.getInstance();
        calExpected.setTime(df.parse("20111231"));
        calEntity.setTime(userEntity.getBirthday());

        assertEquals(calExpected.get(Calendar.MONTH), calEntity.get(Calendar.MONTH));
        assertEquals(calExpected.get(Calendar.DATE), calEntity.get(Calendar.DATE));
        assertEquals(calExpected.get(Calendar.YEAR), calEntity.get(Calendar.YEAR));
    }

    @Test
    public void testFindAll() throws Exception {
        List<UserEntity> all = userDao.findAll();

        assertThat(all, hasSize(1));
    }

    @Test
    public void testDelete() throws Exception {
        userDao.delete(1);
    }
}
