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
import edu.vse.models.SupplierEntity;

public class SupplierDatoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private SupplierDao supplierDao;

    @Test
    public void testFindOne() throws Exception {
        SupplierEntity supplierEntity = supplierDao.getOne(1);

        assertThat(supplierEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("YoloCorp")),
                        hasProperty("phoneNumber", is("+420789098678")),
                        hasProperty("deliveryTime", is(3L))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<SupplierEntity> all = supplierDao.findAll();

        assertThat(all, hasSize(1));
    }

    @Test
    public void testDelete() throws Exception {
        supplierDao.delete(1);
    }
}
