package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.formsdata.RecommendationFormData;

@Component
public class CompositeRecommendationStrategy extends TemplateRecommendationStrategy {
	
	@Override
	protected List<Book> retrieveBooks(RecommendationFormData recommendationDTO) {
		List<Book> books = new ArrayList<Book>();
		
		for (BookAuthor author: recommendationDTO.getAuthors()) {
			BookAuthor bookAuthor = bookAuthorMapper.findByName(author.getName()); 
			books.addAll(bookAuthor.getBooks());
		}
		for (BookCategory category: recommendationDTO.getCategories()) {
			BookCategory bookCategory = bookCategoryMapper.findByName(category.getName());
			books.addAll(bookCategory.getBooks());
		}
		return books;
	}
}
