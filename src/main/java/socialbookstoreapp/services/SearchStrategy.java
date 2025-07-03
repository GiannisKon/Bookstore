package socialbookstoreapp.services;

import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.mappers.BookMapper;

@Component
public interface SearchStrategy {
	
	public List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper);
}
