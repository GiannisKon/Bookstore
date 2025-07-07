package socialbookstoreapp.mapppers;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.mappers.BookCategoryMapper;
import socialbookstoreapp.mappers.BookMapper;
import socialbookstoreapp.mappers.UserMapper;
import socialbookstoreapp.mappers.UserProfileMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class BookMapperTest {
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private BookCategoryMapper bookCategoryMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@AfterEach
    public void tearDown() {
		bookMapper.deleteAll();
		bookCategoryMapper.deleteAll();
		userMapper.deleteAll();
		userProfileMapper.deleteAll();
    }
			
	@Test
	void testBookMapperJpaImplIsNotNull() {
		Assertions.assertNotNull(bookMapper);
	} 
	
	@Test
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByTitle() {
		List<Book> books = bookMapper.findByTitle("book");
		Book book = books.get(0);
		Assertions.assertEquals(book.getTitle(), "book");
	}
	
	@Test
	void testFindByTitleReturnsNull() {
		List<Book> books = bookMapper.findByTitle("zzzzz");
		Assertions.assertEquals(0, books.size());
	}
	
	@Test 
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByTitleContaining() {
		List<Book> books = bookMapper.findByTitleContaining("book");
		Assertions.assertEquals(2, books.size());
	}
	
	@Test
	void testFindByTitleContainingReturnsNull() {
		List<Book> books = bookMapper.findByTitleContaining("zzzzz");
		Assertions.assertEquals(0, books.size());
	}
}
