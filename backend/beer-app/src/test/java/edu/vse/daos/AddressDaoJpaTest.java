package edu.vse.daos;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.AddressEntity;

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

    @Test
    public void testFindUsersRoles() throws Exception {
        List<AddressEntity> addressEntities = addressDao.findByUser(1);

        assertThat(addressEntities, hasSize(1));
        assertThat(addressEntities, hasItem(
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
        ));
    }
}
