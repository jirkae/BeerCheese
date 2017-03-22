package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.OrderStatusEntity;
import edu.vse.models.PaymentTypeEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

public class PaymentTypeDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private PaymentTypeDao paymentTypeDao;

    @Test
    public void testFindByName() throws Exception {
        PaymentTypeEntity paypal = paymentTypeDao.findByName("Paypal").orElseThrow(IllegalStateException::new);

        assertThat(paypal, allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", is("Paypal"))
        ));
    }

    @Test
    public void testFindAll() throws Exception {
        List<PaymentTypeEntity> all = paymentTypeDao.findAll();

        assertThat(all, hasSize(4));
    }
}
