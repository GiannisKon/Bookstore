package socialbookstoreapp.mapppers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.mappers.BookCategoryMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class BookCategoryMapperTest {
	@Autowired
	private BookCategoryMapper bookCategoryMapper;
	
	@AfterEach
    public void tearDown() {
		bookCategoryMapper.deleteAll();
    }
	
	@Test
	void testBookCategoryMapperJpaImplIsNotNull() {
		Assertions.assertNotNull(bookCategoryMapper);
	} 

	@Test
	@Sql(scripts="insert-bookcategory-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testFindByName() {
		BookCategory bookCategory = bookCategoryMapper.findByName("category");
		Assertions.assertEquals(1, bookCategory.getCategoryId());
		Assertions.assertEquals("category", bookCategory.getName());
	}
	
	@Test
	void testFindByNameReturnsNull() {
		BookCategory bookCategory = bookCategoryMapper.findByName("zzzzz");
		Assertions.assertNull(bookCategory);
	}

}
