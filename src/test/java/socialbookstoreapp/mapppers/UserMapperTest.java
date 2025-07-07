package socialbookstoreapp.mapppers;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.User;
import socialbookstoreapp.mappers.UserMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class UserMapperTest {
	@Autowired
	private UserMapper userMapper;
	
	@AfterEach
    public void tearDown() {
		userMapper.deleteAll();
    }
	
	@Test
	void testUserMapperJpaImplIsNotNull() {
		Assertions.assertNotNull(userMapper);
	} 

	@Test
	@Sql(scripts="insert-user-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByUsernameReturnsUser() {
		Optional<User> storedUser = userMapper.findByUsername("user");
		Assertions.assertNotNull(storedUser);
	}
}
