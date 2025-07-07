package socialbookstoreapp.formsdata;

import java.util.List;

import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;

public class RecommendationFormData {
	private List<BookAuthor> authors;
	
	private List<BookCategory> categories;
	
	private String recommendationStrategy;

	public String getRecommendationStrategy() {
		return recommendationStrategy;
	}

	public void setRecommendationStrategy(String recommendationStrategy) {
		this.recommendationStrategy = recommendationStrategy;
	}

	public List<BookAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(List<BookAuthor> authors) {
		this.authors = authors;
	}

	public List<BookCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<BookCategory> categories) {
		this.categories = categories;
	}
}
