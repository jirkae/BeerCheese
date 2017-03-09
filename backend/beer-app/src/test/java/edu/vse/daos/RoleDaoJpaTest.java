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
import edu.vse.models.RoleEntity;

public class RoleDaoJpaTest extends AbstractAppJpaTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testFindOne() throws Exception {
        RoleEntity roleEntity = roleDao.findOne(1);

        assertThat(roleEntity,
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("admin"))
                )
        );
    }

    @Test
    public void testFindAll() throws Exception {
        List<RoleEntity> all = roleDao.findAll();

        assertThat(all, hasSize(2));
    }

    @Test
    public void testDelete() throws Exception {
        roleDao.delete(1);
    }

    @Test
    public void testFindUsersRoles() throws Exception {
        List<RoleEntity> usersRoles = roleDao.findUsersRoles(1);

        assertThat(usersRoles, hasSize(1));
        assertThat(usersRoles, hasItem(
                allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("admin"))
                )
        ));
    }
}
