package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.UserProfile;
import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.RecommendationFormData;
import socialbookstoreapp.mappers.BookAuthorMapper;
import socialbookstoreapp.mappers.BookCategoryMapper;
import socialbookstoreapp.mappers.UserProfileMapper;

@Component
public abstract class TemplateRecommendationStrategy implements RecommendationStrategy{
	@Autowired
	protected BookCategoryMapper bookCategoryMapper;
	
	@Autowired 
	protected BookAuthorMapper bookAuthorMapper;
	
	@Autowired
	protected UserProfileMapper userProfileMapper;
	
	public List<BookFormData> recommend(String username, RecommendationFormData recommendationDTO) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		List<Book> books = retrieveBooks(recommendationDTO);
		
		List<BookFormData> recommendations = new ArrayList<BookFormData>();
		for (Book book: books) {
			if (!userProfile.getBookOffers().contains(book)){
				BookFormData bookDTO = new BookFormData();
				bookDTO.setBookId(book.getBookId());
				bookDTO.setTitle(book.getTitle());
				bookDTO.setSummary(book.getSummary());
				bookDTO.setBookAuthors(book.getBookAuthors());
				bookDTO.setBookCategory(book.getBookCategory());
				bookDTO.setRequestingUsers(book.getRequestingUsers());
				if (!recommendations.contains(bookDTO))
					recommendations.add(bookDTO);
			}
		}
		return recommendations;
	}
	
	protected abstract List<Book> retrieveBooks(RecommendationFormData recommendationDTO);
}
