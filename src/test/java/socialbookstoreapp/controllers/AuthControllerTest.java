package socialbookstoreapp.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import socialbookstoreapp.domainmodel.Role;
import socialbookstoreapp.domainmodel.User;
import socialbookstoreapp.mappers.UserMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class AuthControllerTest {
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private UserMapper userMapper;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testAuthControllerIsNotNull() {
		Assertions.assertNotNull(authController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@WithMockUser(value = "user")
	@Test 
	void testLoginReturnsPage() throws Exception {
		mockMvc.perform(get("/login")).
		andExpect(status().isOk()).
		andExpect(view().name("auth/signin"));		
	}
	
	@WithMockUser(value = "user")
	@Test 
	void testRegisterReturnsPage() throws Exception {
		mockMvc.perform(get("/register")).
		andExpect(status().isOk()).
		andExpect(view().name("auth/signup"));		
	}
	
	@WithMockUser(value = "user")
	@Test 
	@Transactional
	void testRegisterUserReturnsPage() throws Exception {
		User user = new User();
	    user.setUsername("SUTuser");
	    user.setPassword("SUTuser");
	    user.setRole(Role.USER);
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("username", user.getUsername());
	    multiValueMap.add("password", user.getPassword());
	    multiValueMap.add("role", user.getRole().name());
	    
		mockMvc.perform(
				post("/save").
			    params(multiValueMap)).
				andExpect(status().isOk()).
				andExpect(view().name("auth/signin"));	
		
		userMapper.deleteById(user.getUsername());
	}
}
