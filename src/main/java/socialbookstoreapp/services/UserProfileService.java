package socialbookstoreapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.RecommendationFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.formsdata.UserProfileFormData;

@Service
public interface UserProfileService {
	public UserProfileFormData retrieveProfile(String username);
	public void save(UserProfileFormData userProfileFormData);
	public List<BookFormData> retrieveBookOffers(String username);
	public void addBookOffer(String username, BookFormData bookFormData);
	public List<BookFormData> searchBooks(SearchFormData searchFormData);
	public List<BookFormData> recommendBooks(String username, RecommendationFormData recomFormData);
	public void requestBook(int bookId, String username);
	public List<BookFormData> retrieveBookRequests(String username);
	public List<UserProfileFormData> retrieveRequestingUsers(int bookld);
	public void removeRequests(int bookId);
	public void deleteBookOffer(String username, int bookId);
	public void deleteBookRequest(String username, int bookld);
}
