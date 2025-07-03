package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.mappers.BookMapper;

@Component
public abstract class TemplateSearchStrategy implements SearchStrategy {
	@Autowired 
	protected BookMapper bookMapper;
	
	public List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper){
		List<BookFormData> result = new ArrayList<BookFormData>();
		
		List<Book> books = makeInitialListOfBooks(searchFormData);
		for (Book book: books) {
			boolean authorsMatch = checkIfAuthorsMatch(searchFormData, book);   
	    	if (authorsMatch) {
	        	BookFormData bookFormData = new BookFormData();
	        	bookFormData.setBookId(book.getBookId());
	        	bookFormData.setTitle(book.getTitle());
	        	bookFormData.setSummary(book.getSummary());
	        	bookFormData.setBookAuthors(book.getBookAuthors());
	        	bookFormData.setBookCategory(book.getBookCategory());
	        	bookFormData.setRequestingUsers(book.getRequestingUsers());
	            result.add(bookFormData);
	        }
		}
		return result;
	}
	
	protected abstract List<Book> makeInitialListOfBooks(SearchFormData searchFormData);
	protected abstract boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book);
}
