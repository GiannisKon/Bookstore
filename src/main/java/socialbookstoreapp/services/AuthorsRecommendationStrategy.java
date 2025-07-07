package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.formsdata.RecommendationFormData;

@Component
public class AuthorsRecommendationStrategy extends TemplateRecommendationStrategy {
		
	@Override
	protected List<Book> retrieveBooks(RecommendationFormData recommendationDTO) {
		List<Book> books = new ArrayList<Book>();
		
		for (BookAuthor author: recommendationDTO.getAuthors()) {
			BookAuthor bookAuthor = bookAuthorMapper.findByName(author.getName()); 
			books.addAll(bookAuthor.getBooks());
		}
		return books;
	}
}
