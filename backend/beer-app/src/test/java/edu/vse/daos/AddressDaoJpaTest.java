package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.AddressEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

public class AddressDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private AddressDao addressDao;

    @Test
    public void testFindOne() throws Exception {
        AddressEntity addressEntity = addressDao.findOne(1);

        assertThat(addressEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("street", is("shitty street 7")),
                        hasProperty("city", is("shittown")),
                        hasProperty("note", is("yolo")),
                        hasProperty("country", allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("Afghanistan"))
                        ))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<AddressEntity> all = addressDao.findAll();

        assertThat(all, hasSize(2));
    }

    @Test
    public void testDelete() throws Exception {
        addressDao.delete(1);
    }
}
