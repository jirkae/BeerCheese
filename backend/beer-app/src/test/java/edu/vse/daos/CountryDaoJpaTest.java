package edu.vse.daos;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.CountryEntity;

public class CountryDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void testFindAll() throws Exception {
        List<CountryEntity> all = countryDao.findAll();

        assertThat(all, hasSize(245));
    }
}
