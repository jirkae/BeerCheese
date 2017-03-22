package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.OrderEntity;
import edu.vse.models.PackageEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PackageDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private PackageDao packageDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindOne() throws Exception {
        PackageEntity packageEntity = packageDao.findOne(1);

        assertThat(packageEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("order", notNullValue())
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<PackageEntity> all = packageDao.findAll();

        assertThat(all, hasSize(1));
    }

    @Test
    public void testSave() throws Exception {
        OrderEntity orderEntity = testEntityManager.find(OrderEntity.class, 1);

        PackageEntity packageEntity = new PackageEntity(orderEntity);

        packageDao.saveAndFlush(packageEntity);
    }
}
