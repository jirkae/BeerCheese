package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.OrderStatusEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

public class OrderStatusDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private OrderStatusDao orderStatusDao;

    @Test
    public void testFindByName() throws Exception {
        OrderStatusEntity shipped = orderStatusDao.findByName("Shipped").orElseThrow(IllegalStateException::new);

        assertThat(shipped, allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", is("Shipped"))
        ));
    }

    @Test
    public void testFindAll() throws Exception {
        List<OrderStatusEntity> all = orderStatusDao.findAll();

        assertThat(all, hasSize(5));
    }
}
