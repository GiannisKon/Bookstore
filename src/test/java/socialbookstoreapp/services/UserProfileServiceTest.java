package socialbookstoreapp.services;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.domainmodel.UserProfile;
import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.RecommendationFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.formsdata.UserProfileFormData;
import socialbookstoreapp.mappers.BookAuthorMapper;
import socialbookstoreapp.mappers.BookCategoryMapper;
import socialbookstoreapp.mappers.BookMapper;
import socialbookstoreapp.mappers.UserProfileMapper;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:application.properties")
public class UserProfileServiceTest {
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private BookAuthorMapper bookAuthorMapper;
	
	@Autowired 
	private BookCategoryMapper bookCategoryMapper;
	
	@Test
	void testUserProfileServiceImplIsNotNull(){
		Assertions.assertNotNull(userProfileService);
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRetrieveProfileReturnsUserProfileDTO() {
		Assertions.assertNotNull(userProfileService.retrieveProfile("user"));
	}
	
	@Test
	@Transactional
	void testSave(){
		UserProfileFormData userProfileDTO = new UserProfileFormData();
		userProfileDTO.setUsername("testUser");
		userProfileService.save(userProfileDTO);
		
		Assertions.assertNotNull(userProfileMapper.findByUsername("testUser"));
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRetrieveBookOffers() {
		List<BookFormData> books = userProfileService.retrieveBookOffers("user1");
		Assertions.assertEquals(2, books.size());
		Assertions.assertEquals("book", books.get(0).getTitle());
		Assertions.assertEquals("super book", books.get(1).getTitle());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testAddBookOffer() {
		BookFormData bookFormData = new BookFormData();
		bookFormData.setTitle("testBook");
		bookFormData.setSummary("testBook");
		
		BookAuthor author = new BookAuthor();
		author.setName("testAuthor");
		bookFormData.setBookAuthors(List.of(author));
		
		BookCategory category = new BookCategory();
		category.setName("testCategory");
		bookFormData.setBookCategory(category);
		
		userProfileService.addBookOffer("user1", bookFormData);
		
		List<BookFormData> books = userProfileService.retrieveBookOffers("user1");
		boolean found = false; 
		for (BookFormData bookDTO: books) {
			if (bookDTO.getTitle().equals("testBook")) {
				found = true;
				break;
			}
		}
		Assertions.assertTrue(found);
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testSearchBooks() {
		SearchFormData searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("exact");
		searchFormData.setBookTitle("book");
		searchFormData.setBookAuthors(List.of("author"));
		List<BookFormData> books = userProfileService.searchBooks(searchFormData);
		Assertions.assertEquals("book", books.get(0).getTitle());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRecommendBooks() {
		BookAuthor author = new BookAuthor(); 
		author.setName("author");
		
		BookCategory category = new BookCategory();
		category.setName("comedy");
		
		RecommendationFormData recomFormData = new RecommendationFormData();
		recomFormData.setRecommendationStrategy("authors");
		recomFormData.setAuthors(List.of(author));
		recomFormData.setCategories(List.of(category));
		
		List<BookFormData> books = userProfileService.recommendBooks("user", recomFormData);
		Assertions.assertEquals(2, books.size());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRequestBook() {
		userProfileService.requestBook(1, "user");
		List<Book> bookRequests = userProfileMapper.findByUsername("user").getRequestedBooks();
		Assertions.assertEquals("book", bookRequests.get(0).getTitle());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRetrieveBookRequests() {
		UserProfile userProfile = userProfileMapper.findByUsername("user");
		
		Book book = new Book();
		book.setTitle("testBook");
		book.setSummary("testBook");
		book.setBookAuthors(List.of(bookAuthorMapper.findByName("author")));
		book.setBookCategory(bookCategoryMapper.findByName("comedy"));
		
		userProfile.addRequest(book);
		userProfileMapper.save(userProfile);
		
		List<BookFormData> bookRequests = userProfileService.retrieveBookRequests("user");
		Assertions.assertEquals("testBook", bookRequests.get(0).getTitle());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testRetrieveRequestingUsers() {
		List<UserProfileFormData> requestingUsers = userProfileService.retrieveRequestingUsers(1);
		Assertions.assertTrue(requestingUsers.isEmpty());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteBookRequest() {
		UserProfile userProfile = userProfileMapper.findByUsername("user");
		
		Book book = new Book();
		book.setTitle("testBook");
		book.setSummary("testBook");
		book.setBookAuthors(List.of(bookAuthorMapper.findByName("author")));
		book.setBookCategory(bookCategoryMapper.findByName("comedy"));
		
		userProfile.addRequest(book);
		userProfileMapper.save(userProfile);
		
		userProfileService.deleteBookRequest("user", 2);
		Assertions.assertFalse(userProfile.getRequestedBooks().contains(book));
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteBookOffer() {
		userProfileService.deleteBookOffer("user1", 2);
		Assertions.assertEquals(1, bookMapper.findAll().size());
	}
}
