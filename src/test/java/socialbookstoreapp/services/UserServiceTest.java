package socialbookstoreapp.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import socialbookstoreapp.domainmodel.User;
import socialbookstoreapp.mappers.UserMapper;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:application.properties")
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	private User user;
	
	@BeforeEach
	void init() {
	    user = new User();
	    user.setUsername("testUser");
		user.setPassword("testPassword");
	}
	
	@AfterEach
    public void tearDown() {
		userMapper.deleteAll();
    }

	@Test
	void testIsUserPresentReturnsTrue(){
		userService.saveUser(user);
		boolean present = userService.isUserPresent(user);
		Assertions.assertTrue(present);
	}
}
