package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.ShippingEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

public class ShippingDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private ShippingDao shippingDao;

    @Test
    public void testFindOne() throws Exception {
        ShippingEntity shippingEntity = shippingDao.getOne(1);

        assertThat(shippingEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("Pick up")),
                        hasProperty("price", is(new Float(0))),
                        hasProperty("deliveryTime", is(new Float(0)))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<ShippingEntity> all = shippingDao.findAll();

        assertThat(all, hasSize(4));
    }
}
