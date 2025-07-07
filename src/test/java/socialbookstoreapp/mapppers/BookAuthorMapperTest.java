package socialbookstoreapp.mapppers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.mappers.BookAuthorMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class BookAuthorMapperTest {
	@Autowired
	private BookAuthorMapper bookAuthorMapper;
	
	@AfterEach
    public void tearDown() {
		bookAuthorMapper.deleteAll();
    }
	
	@Test
	void testBookAuthorMapperJpaImplIsNotNull() {
		Assertions.assertNotNull(bookAuthorMapper);
	} 

	@Test
	@Sql(scripts="insert-bookauthor-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByName() {
		BookAuthor bookAuthor = bookAuthorMapper.findByName("author");
		Assertions.assertEquals(1, bookAuthor.getAuthorId());
		Assertions.assertEquals("author", bookAuthor.getName());
	}
	
	@Test
	void testFindByNameReturnsNull() {
		BookAuthor bookAuthor = bookAuthorMapper.findByName("zzzzz");
		Assertions.assertNull(bookAuthor);
	}
}
