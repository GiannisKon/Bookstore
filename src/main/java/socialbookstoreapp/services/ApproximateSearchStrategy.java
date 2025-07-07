package socialbookstoreapp.services;

import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.formsdata.SearchFormData;

@Component
public class ApproximateSearchStrategy extends TemplateSearchStrategy {

	@Override
	protected List<Book> makeInitialListOfBooks(SearchFormData searchFormData) {
		return bookMapper.findByTitleContaining(searchFormData.getBookTitle());
	}

	@Override
	protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
		boolean authorsMatch = true;
        for (String author: searchFormData.getBookAuthors()) {
            boolean authorFound = false;
            for (BookAuthor bookAuthor: book.getBookAuthors()) {
                if (bookAuthor.getName().equals(author)) {
                    authorFound = true;
                    break;
                }
            }
            if (!authorFound) {
                authorsMatch = false;
                break;
            }
        }
        return authorsMatch;
	}
}
