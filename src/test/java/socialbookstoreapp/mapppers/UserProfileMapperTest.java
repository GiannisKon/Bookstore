package socialbookstoreapp.mapppers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.UserProfile;
import socialbookstoreapp.mappers.UserProfileMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class UserProfileMapperTest {
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@AfterEach
    public void tearDown() {
		userProfileMapper.deleteAll();
    }
	
	@Test
	void testUserProfileMapperJpaImplIsNotNull() {
		Assertions.assertNotNull(userProfileMapper);
	}
	
	@Test
	@Sql(scripts="insert-userprofile-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByUsernameReturnsUserProfile() {
		UserProfile storedUserProfile = userProfileMapper.findByUsername("user");
		Assertions.assertNotNull(storedUserProfile);
	}
	
	@Test
	void testFindByUsernameReturnsNull() {
		UserProfile userProfile = userProfileMapper.findByUsername("zzzzzz");
		Assertions.assertNull(userProfile);
	}
}
