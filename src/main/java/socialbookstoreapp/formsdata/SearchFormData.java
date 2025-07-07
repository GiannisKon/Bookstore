package socialbookstoreapp.formsdata;

import java.util.List;

public class SearchFormData {
	private String bookTitle;
	private List<String> bookAuthors;
	private String searchStrategy;
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	public List<String> getBookAuthors() {
		return bookAuthors;
	}
	
	public void setBookAuthors(List<String> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public String getSearchStrategy() {
		return searchStrategy;
	}

	public void setSearchStrategy(String searchStrategy) {
		this.searchStrategy = searchStrategy;
	}
}
