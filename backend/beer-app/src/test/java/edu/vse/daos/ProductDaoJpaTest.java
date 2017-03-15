package edu.vse.daos;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.CategoryEntity;
import edu.vse.models.ProductEntity;

public class ProductDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindOne() throws Exception {
        ProductEntity productEntity = productDao.getOne(1);

        assertThat(productEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("supplier", is(1)),
                        hasProperty("name", is("Beer")),
                        hasProperty("price", is(new Float(100))),
                        hasProperty("quantity", is(2)),
                        hasProperty("priceAfterDiscount", is(new Float(90))),
                        hasProperty("active", is(true)),
                        hasProperty("image", is("guhppkyg5c")),
                        hasProperty("description", is("test description")),
                        hasProperty("category",
                                allOf(
                                        hasProperty("id", is(1)),
                                        hasProperty("name", is("Beer")),
                                        hasProperty("mainCategory", nullValue())
                                )
                        )
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<ProductEntity> all = productDao.findAll();

        assertThat(all, hasSize(1));
    }

    @Test
    public void testFindBySupplier() throws Exception {
        List<ProductEntity> bySupplier = productDao.findBySupplier(1);

        assertThat(bySupplier, hasSize(1));
    }

    @Test
    public void testFindByCategory() throws Exception {
        List<ProductEntity> byCategory = productDao.findByCategory_Id(1);

        assertThat(byCategory, hasSize(1));
    }

    @Test
    public void testDelete() throws Exception {
        productDao.delete(1);
    }

    @Test
    public void testSave() throws Exception {
        CategoryEntity categoryEntity = testEntityManager.find(CategoryEntity.class, 1);
        productDao.save(new ProductEntity(categoryEntity, "Beer2", new Float(101), 3, new Float(100), true, "/images/2.jpeg", 1, "troll"));
    }

    @Test
    public void testExistsTrue() throws Exception {
        boolean imageUriExists = productDao.imageUriExists("guhppkyg5c");

        assertTrue(imageUriExists);
    }

    @Test
    public void testExistsFalse() throws Exception {
        boolean imageUriExists = productDao.imageUriExists("guhpdsadsadsapkyg5c");

        assertFalse(imageUriExists);
    }
}
