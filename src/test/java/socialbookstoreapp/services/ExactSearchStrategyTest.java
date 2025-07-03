package socialbookstoreapp.services;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.mappers.BookMapper;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
public class ExactSearchStrategyTest {
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private ExactSearchStrategy exactSeachStrategy;
	
	private SearchFormData searchFormData;
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testSearchReturnsBook() {
		searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("exact");
		searchFormData.setBookTitle("book");
		searchFormData.setBookAuthors(List.of("author"));
		List<BookFormData> books = exactSeachStrategy.search(searchFormData, bookMapper);
		Assertions.assertEquals("book", books.get(0).getTitle());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testSearchReturnsEmptyList() {
		searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("exact");
		searchFormData.setBookTitle("no book");
		searchFormData.setBookAuthors(List.of("no author"));
		List<BookFormData> books = exactSeachStrategy.search(searchFormData, bookMapper);
		Assertions.assertEquals(0, books.size());
	}
}
