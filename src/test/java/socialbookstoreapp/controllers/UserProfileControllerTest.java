package socialbookstoreapp.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.domainmodel.UserProfile;
import socialbookstoreapp.formsdata.RecommendationFormData;
import socialbookstoreapp.formsdata.SearchFormData;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class UserProfileControllerTest {
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserProfileController userProfileController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testUserProfileControllerIsNotNull() {
		Assertions.assertNotNull(userProfileController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}

	@Test 
	void testGetUserMainMenuReturnsPage() throws Exception {
		mockMvc.perform(get("/user/dashboard")).
			andExpect(status().isOk()).
			andExpect(view().name("user/dashboard"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testSetProfileInfoReturnsFormPage() throws Exception {
		mockMvc.perform(get("/user/createProfile")).
			andExpect(status().isOk()).
			andExpect(view().name("user/user-form"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testRetrieveProfileReturnPage() throws Exception {
		mockMvc.perform(get("/user/retrieveProfile")).
			andExpect(status().isOk()).
			andExpect(view().name("user/retrieve-profile"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Test 
	void testSaveProfileReturnsPage() throws Exception {
		UserProfile userProfile = new UserProfile();
		userProfile.setUsername("userProfileDummy");
		userProfile.setFullName("userProfileDummy");
		userProfile.setAddress("Ioannina");
		userProfile.setAge(18);
		String bookCategories = "Fiction, Science";
	    String bookAuthors = "Author1, Author2";
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("username", userProfile.getUsername());
	    multiValueMap.add("fullname", userProfile.getFullName());
	    multiValueMap.add("address", userProfile.getAddress());
	    multiValueMap.add("age", Integer.toString(userProfile.getAge()));
	    multiValueMap.add("bookCategories", bookCategories);
	    multiValueMap.add("bookAuthors", bookAuthors);
	    
		mockMvc.perform(
				post("/user/saveProfile")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/user/dashboard"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testListBookOffersReturnsPage() throws Exception {
		mockMvc.perform(get("/user/listBookOffers")).
			andExpect(status().isOk()).
			andExpect(model().attributeExists("bookOffersDTO")).
			andExpect(view().name("user/list-book-offers"));		
	}
	
	@WithMockUser(value = "user")
	@Test
	void testShowOfferFormReturnsPage() throws Exception {
		mockMvc.perform(get("/user/showOfferForm")).
			andExpect(status().isOk()).
			andExpect(view().name("user/book-offer-form"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testSaveOfferReturnsPage() throws Exception {
		Book book = new Book();
		book.setTitle("Book Title");
		book.setSummary("Book Summary");
		book.setBookCategory(new BookCategory());
		String authors = "Book Author";
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("bookId", Integer.toString(book.getBookId()));
	    multiValueMap.add("title", book.getTitle());
	    multiValueMap.add("summary", book.getSummary());
	    multiValueMap.add("bookCategory.name", book.getBookCategory().getName());
	    multiValueMap.add("authors", authors);
	    
		mockMvc.perform(
				post("/user/saveOffer")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/user/listBookOffers"));	
	}
    
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testDeleteBookOfferReturnsPage() throws Exception {
		mockMvc.perform(get("/user/deleteBookOffer?bookId=1")).
			andExpect(status().isFound()).
			andExpect(view().name("redirect:/user/listBookOffers"));
	}
	
	@WithMockUser(value = "user")
	@Test
	void testShowSearchFormReturnsPage() throws Exception {
		mockMvc.perform(get("/user/showSearchForm")).
			andExpect(status().isOk()).
			andExpect(view().name("user/search-form"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testSearchReturnsPage() throws Exception {
		SearchFormData searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("exact");
		searchFormData.setBookTitle("book");
		String authors = "author";
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("searchStrategy", searchFormData.getSearchStrategy());
	    multiValueMap.add("bookTitle", searchFormData.getBookTitle());
	    multiValueMap.add("authors", authors);
	    
	    mockMvc.perform(
				post("/user/search")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list-search-results"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testShowRecommendationFormReturnsPage() throws Exception {
		mockMvc.perform(get("/user/showRecommendationForm")).
			andExpect(status().isOk()).
			andExpect(view().name("user/recommendations-form"));		
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testRecommendBooksReturnsPage() throws Exception {
		RecommendationFormData recomFormData = new RecommendationFormData();
		recomFormData.setRecommendationStrategy("authors");
		
		BookCategory category = new BookCategory();
		category.setName("comedy");
		
		BookAuthor author = new BookAuthor();
		author.setName("author");
		
		recomFormData.setAuthors(List.of(author));
		recomFormData.setCategories(List.of(category));
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("recommendationStrategy", recomFormData.getRecommendationStrategy());
		multiValueMap.add("authors.name", recomFormData.getAuthors().get(0).getName());
	    multiValueMap.add("categories.name", recomFormData.getCategories().get(0).getName());
	    
	    mockMvc.perform(
				post("/user/recommendBooks")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list-recommend-results"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testRequestBookReturnsPage() throws Exception {
		mockMvc.perform(get("/user/requestBook?bookId=1")).
			andExpect(status().isFound()).
			andExpect(view().name("redirect:/user/dashboard"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testShowUserBookRequestsReturnsPage() throws Exception {
		mockMvc.perform(get("/user/showUserBookRequests")).
			andExpect(status().isOk()).
			andExpect(view().name("user/show-user-requests"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testShowRequestingUsersForBookOfferReturnsPage() throws Exception {
		mockMvc.perform(get("/user/showRequestingUsersForBookOffer?bookId=1")).
			andExpect(status().isOk()).
			andExpect(view().name("user/show-requesting-users"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testAcceptRequestReturnsPage() throws Exception {
		mockMvc.perform(get("/user/acceptRequest?username=user&bookId=1")).
			andExpect(status().isOk()).
			andExpect(view().name("user/show-contact-info"));
	}
	
	@WithMockUser(value = "user")
	@Transactional
	@Sql(scripts="insert-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test 
	void testDeleteBookRequestReturnsPage() throws Exception {
		mockMvc.perform(get("/user/deleteBookRequest?bookId=1")).
			andExpect(status().isFound()).
			andExpect(view().name("redirect:/user/showUserBookRequests"));
	}
}
