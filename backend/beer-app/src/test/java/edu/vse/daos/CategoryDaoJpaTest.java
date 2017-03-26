package edu.vse.daos;

import edu.vse.AbstractAppJpaTest;
import edu.vse.models.CategoryEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CategoryDaoJpaTest  extends AbstractAppJpaTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testFindOne() throws Exception {
        CategoryEntity categoryEntity = categoryDao.getOne(2);

        assertThat(categoryEntity,
                allOf(
                        hasProperty("id", is(2)),
                        hasProperty("name", is("Lager")),
                        hasProperty("mainCategory",
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
        List<CategoryEntity> all = categoryDao.findAll();

        assertThat(all, hasSize(4));
    }

    @Test
    public void testFindByMainCategory_Id() throws Exception {
        List<CategoryEntity> byMainCategory_id = categoryDao.findByMainCategory_Id(1);

        assertThat(byMainCategory_id, hasSize(2));
    }

    @Test
    public void testFindByMainCategoryIsNull() throws Exception {
        List<CategoryEntity> byMainCategoryIsNull = categoryDao.findByMainCategoryIsNull();

        assertThat(byMainCategoryIsNull, hasSize(2));
        assertThat(byMainCategoryIsNull, hasItem(hasProperty("mainCategory", nullValue())));
    }


    @Test
    public void testFindByMainCategoryIsNotNull() throws Exception {
        List<CategoryEntity> byMainCategoryIsNull = categoryDao.findByMainCategoryIsNotNull();

        assertThat(byMainCategoryIsNull, hasSize(2));
        assertThat(byMainCategoryIsNull, hasItem(hasProperty("mainCategory", notNullValue())));
    }
}
