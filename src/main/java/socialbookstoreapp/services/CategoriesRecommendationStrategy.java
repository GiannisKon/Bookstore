package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.formsdata.RecommendationFormData;

@Component
public class CategoriesRecommendationStrategy extends TemplateRecommendationStrategy {
	
	@Override
	protected List<Book> retrieveBooks(RecommendationFormData recommendationDTO) {
		List<Book> books = new ArrayList<Book>();
		
		for (BookCategory category: recommendationDTO.getCategories()) {
			BookCategory bookCategory = bookCategoryMapper.findByName(category.getName());
			books.addAll(bookCategory.getBooks());
		}
		return books;
	}
}
