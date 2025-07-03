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
public class ApproximateSearchStrategyTest {
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private ApproximateSearchStrategy approximateSearchStrategy;
	
	private SearchFormData searchFormData;
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testApproximateSearchReturnsBooks() {
		searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("approximate");
		searchFormData.setBookTitle("book");
		searchFormData.setBookAuthors(List.of("author"));
		List<BookFormData> books = approximateSearchStrategy.search(searchFormData, bookMapper);
		Assertions.assertEquals(2, books.size());
	}
	
	@Test
	@Transactional
	@Sql(scripts="insert-book-data.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testSearchReturnsEmptyList() {
		searchFormData = new SearchFormData();
		searchFormData.setSearchStrategy("approximate");
		searchFormData.setBookTitle("no book");
		searchFormData.setBookAuthors(List.of("no author"));
		List<BookFormData> books = approximateSearchStrategy.search(searchFormData, bookMapper);
		Assertions.assertEquals(0, books.size());
	}
}
