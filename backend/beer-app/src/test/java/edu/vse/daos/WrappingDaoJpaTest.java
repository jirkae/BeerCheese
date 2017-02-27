package edu.vse.daos;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.WrappingEntity;

public class WrappingDaoJpaTest extends AbstractAppJpaTest {


    @Autowired
    private WrappingDao wrappingDao;

    @Test
    public void testFindOne() throws Exception {
        WrappingEntity wrappingEntity = wrappingDao.getOne(1);

        assertThat(wrappingEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("Paper box")),
                        hasProperty("price", is(new Float(0)))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<WrappingEntity> all = wrappingDao.findAll();

        assertThat(all, hasSize(3));
    }
}
