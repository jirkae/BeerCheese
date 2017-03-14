package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.dtos.Order;
import edu.vse.models.AddressEntity;
import edu.vse.models.OrderEntity;
import edu.vse.models.OrderStatusEntity;
import edu.vse.models.PaymentTypeEntity;
import edu.vse.models.ShippingEntity;
import edu.vse.models.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

public class OrderDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testFindOne() throws Exception {
        OrderEntity orderEntity = orderDao.getOne(1);

        assertThat(orderEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("user", notNullValue()),
                        hasProperty("shippingAddress", notNullValue()),
                        hasProperty("billingAddress", notNullValue()),
                        hasProperty("status",
                                allOf(
                                        hasProperty("id", is(1)),
                                        hasProperty("name", is("Preparing"))
                                )
                        ),
                        hasProperty("paymentType",
                                allOf(
                                        hasProperty("id", is(1)),
                                        hasProperty("name", is("Credit card"))
                                )
                        ),
                        hasProperty("shipping", notNullValue())
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<OrderEntity> all = orderDao.findAll();

        assertThat(all, hasSize(1));
    }
}
