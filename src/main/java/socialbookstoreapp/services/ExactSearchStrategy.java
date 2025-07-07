package socialbookstoreapp.services;

import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.formsdata.SearchFormData;

@Component
public class ExactSearchStrategy extends TemplateSearchStrategy {

	@Override
	protected List<Book> makeInitialListOfBooks(SearchFormData searchFormData) {
		return bookMapper.findByTitle(searchFormData.getBookTitle()); 
	}

	@Override
	protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
		boolean authorsMatch = true;
        for (BookAuthor bookAuthor: book.getBookAuthors()) {
            if (!searchFormData.getBookAuthors().contains(bookAuthor.getName())) {
                authorsMatch = false;
                break;
            }
        }
        return authorsMatch;
	}

}
