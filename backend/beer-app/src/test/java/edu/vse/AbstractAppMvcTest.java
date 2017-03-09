package edu.vse;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(scripts = "classpath:sql/init.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/truncate.sql", executionPhase = AFTER_TEST_METHOD)
public abstract class AbstractAppMvcTest extends AbstractAppComponentTest {
}
