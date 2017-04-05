package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.PackageEntity;
import edu.vse.models.ProductEntity;
import edu.vse.models.ProductPackageEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ProductPackageDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private ProductPackageDao productPackageDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindOne() throws Exception {
        ProductPackageEntity productPackageEntity = productPackageDao.findOne(1);

        assertThat(productPackageEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("product", notNullValue()),
                        hasProperty("packageEntity", notNullValue()),
                        hasProperty("quantity", is(1)),
                        hasProperty("price", is(1F))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<ProductPackageEntity> all = productPackageDao.findAll();

        assertThat(all, hasSize(1));
    }

    @Test
    public void testSave() throws Exception {
        ProductEntity productEntity = testEntityManager.find(ProductEntity.class, 1);
        PackageEntity packageEntity = testEntityManager.find(PackageEntity.class, 1);

        ProductPackageEntity productPackageEntity = new ProductPackageEntity(
                productEntity,
                packageEntity,
                1,
                1F,
                null
        );

        productPackageDao.saveAndFlush(productPackageEntity);
    }

    @Test
    public void testFindByPackageEntity_Id() throws Exception {
        List<ProductPackageEntity> list = productPackageDao.findByPackageEntity_Id(1);

        assertThat(list, hasSize(1));
    }

    @Test
    public void testFindByPackageEntity_IdIn() throws Exception {
        List<ProductPackageEntity> list = productPackageDao.findByPackageEntity_IdIn(asList(1));

        assertThat(list, hasSize(1));
    }

    @Test
    public void testDeleteByPackageEntity_Id() throws Exception {
        int i = productPackageDao.deleteAllByPackageEntity_Id(1);

        assertEquals(i, 1);
    }
}
