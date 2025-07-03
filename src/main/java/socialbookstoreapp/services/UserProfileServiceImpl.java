package socialbookstoreapp.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import socialbookstoreapp.domainmodel.Book;
import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.domainmodel.UserProfile;
import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.RecommendationFormData;
import socialbookstoreapp.formsdata.SearchFormData;
import socialbookstoreapp.formsdata.UserProfileFormData;
import socialbookstoreapp.mappers.BookAuthorMapper;
import socialbookstoreapp.mappers.BookCategoryMapper;
import socialbookstoreapp.mappers.BookMapper;
import socialbookstoreapp.mappers.UserProfileMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired 
	private UserProfileMapper userProfileMapper;
	
	@Autowired 
	private BookAuthorMapper bookAuthorMapper;
	
	@Autowired 
	private BookCategoryMapper bookCategoryMapper;
	
	@Autowired 
	private BookMapper bookMapper;
	
	@Autowired
	private SearchStrategyFactory searchStrategyFactory;
	
	@Autowired 
	private RecommendationStrategyFactory recommendationStrategyFactory;
    
	@Override
	public UserProfileFormData retrieveProfile(String username) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		UserProfileFormData userProfileFormData = new UserProfileFormData();
		
		userProfileFormData.setUsername(username);
		userProfileFormData.setFullName(userProfile.getFullName());
		userProfileFormData.setAge(userProfile.getAge());
		userProfileFormData.setAddress(userProfile.getAddress());
		userProfileFormData.setPhonenumber(userProfile.getPhonenumber());
		userProfileFormData.setBookOffers(userProfile.getBookOffers());
		userProfileFormData.setFavouriteBookAuthors(userProfile.getFavouriteBookAuthors());
		userProfileFormData.setFavouriteBookCategories(userProfile.getFavouriteBookCategories());
		userProfileFormData.setRequestedBooks(userProfile.getRequestedBooks());

		return userProfileFormData;
	}

	@Override
	@Transactional
	public void save(UserProfileFormData userProfileFormData) {
		UserProfile userProfile;
		if (userProfileMapper.findByUsername(userProfileFormData.getUsername()) == null) {
			userProfile = new UserProfile();
			userProfile.setUsername(userProfileFormData.getUsername());
		} else {
			userProfile = userProfileMapper.findByUsername(userProfileFormData.getUsername());
		}
		
		userProfile.setFullName(userProfileFormData.getFullName());
		userProfile.setAge(userProfileFormData.getAge());
		userProfile.setAddress(userProfileFormData.getAddress());
		userProfile.setPhonenumber(userProfileFormData.getPhonenumber());
		userProfile.setBookOffers(userProfileFormData.getBookOffers());
		userProfile.setRequestedBooks(userProfileFormData.getRequestedBooks());
		List<BookAuthor> favouriteAuthors = new ArrayList<>();
	    List<BookCategory> favouriteCategories = new ArrayList<>();
	    
	    if (userProfileFormData.getFavouriteBookAuthors() != null) {
		    for (BookAuthor author: userProfileFormData.getFavouriteBookAuthors()) {
		        BookAuthor existingAuthor = bookAuthorMapper.findByName(author.getName());
		        if (existingAuthor == null) {
		            bookAuthorMapper.save(author);
		            favouriteAuthors.add(author);
		        } else {
		            favouriteAuthors.add(existingAuthor);
		        }
		    }
	    }
	    if (userProfileFormData.getFavouriteBookCategories() != null) {
		    for (BookCategory category: userProfileFormData.getFavouriteBookCategories()) {
		        BookCategory existingCategory = bookCategoryMapper.findByName(category.getName());
		        if (existingCategory == null) {
		            bookCategoryMapper.save(category);
		            favouriteCategories.add(category);
		        } else {
		            favouriteCategories.add(existingCategory);
		        }
		    }
	    }
	    userProfile.setFavouriteBookAuthors(favouriteAuthors);
	    userProfile.setFavouriteBookCategories(favouriteCategories);
	    userProfileMapper.save(userProfile);
	}	

	@Override
	public List<BookFormData> retrieveBookOffers(String username) {
		List<Book> bookOffers = userProfileMapper.findByUsername(username).getBookOffers();
		
		List<BookFormData> bookOffersDTO = new ArrayList<>();
	    for (Book book: bookOffers) {
	        BookFormData bookFormData = convertToBookFormData(book);
	        bookOffersDTO.add(bookFormData);
	    }
	    return bookOffersDTO;
	}
	
	private BookFormData convertToBookFormData(Book book) {
	    BookFormData bookFormData = new BookFormData();
	    bookFormData.setBookId(book.getBookId());
	    bookFormData.setTitle(book.getTitle());
	    bookFormData.setSummary(book.getSummary());
	    bookFormData.setBookAuthors(book.getBookAuthors());
	    bookFormData.setBookCategory(book.getBookCategory());
	    bookFormData.setRequestingUsers(book.getRequestingUsers());
	    
	    return bookFormData;
	}

	@Override
	public void addBookOffer(String username, BookFormData bookFormData) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		List<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();
		for (BookAuthor author: bookFormData.getBookAuthors()) {	
	        BookAuthor existingAuthor = bookAuthorMapper.findByName(author.getName()); 
	        if (existingAuthor == null) {	//checks if author already exists in database to not have duplicate entries
	            bookAuthorMapper.save(author);
	            bookAuthors.add(author);
	        } else {
	        	bookAuthors.add(existingAuthor);
	        }
	    }
		bookFormData.setBookAuthors(bookAuthors);
		BookCategory bookCategory = bookCategoryMapper.findByName(bookFormData.getBookCategory().getName());
		if (bookCategory == null) {		//checks if category already exists in database to not have duplicate entries
			bookCategoryMapper.save(bookFormData.getBookCategory());
		} else {
			bookFormData.setBookCategory(bookCategory);
		}
		Book bookOffer = convertToBook(bookFormData);
		userProfile.addBookOffer(bookOffer);
		userProfileMapper.save(userProfile);
	}

	private Book convertToBook(BookFormData bookFormData) {
	    Book book = new Book();
	    book.setTitle(bookFormData.getTitle());
	    book.setSummary(bookFormData.getSummary());
	    book.setBookAuthors(bookFormData.getBookAuthors());
	    book.setBookCategory(bookFormData.getBookCategory());
	    book.setRequestingUsers(bookFormData.getRequestingUsers());
	    
	    return book;
	}
	
	@Override
	public List<BookFormData> searchBooks(SearchFormData searchFormData) {
		SearchStrategy searchStrategy = searchStrategyFactory.getSearchStrategy(searchFormData.getSearchStrategy());
		return searchStrategy.search(searchFormData, bookMapper);
	}

	@Override
	public List<BookFormData> recommendBooks(String username, RecommendationFormData recomFormData) {
		RecommendationStrategy recommendationStrategy = recommendationStrategyFactory.getRecommendationStrategy(recomFormData.getRecommendationStrategy());
		return recommendationStrategy.recommend(username, recomFormData);
	}

	@Override
	public void requestBook(int bookId, String username) {
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		Book book = bookMapper.findById(bookId).get();
		if (!userProfile.getRequestedBooks().contains(book)) {
			userProfile.addRequest(book);
			userProfileMapper.save(userProfile);
		}
	}

	@Override
	public List<BookFormData> retrieveBookRequests(String username) {
		List<Book> bookRequests = userProfileMapper.findByUsername(username).getRequestedBooks();
		
		List<BookFormData> bookRequestsDTO = new ArrayList<>();
	    for (Book book: bookRequests) {
	        BookFormData bookFormData = convertToBookFormData(book);
	        bookRequestsDTO.add(bookFormData);
	    }
		return bookRequestsDTO;
	}

	@Override
	public List<UserProfileFormData> retrieveRequestingUsers(int bookld) {
		List<UserProfileFormData> userProfilesDTO = new ArrayList<UserProfileFormData>();
		
		List<UserProfile> userProfiles = bookMapper.findById(bookld).get().getRequestingUsers();
		for (UserProfile userProfile: userProfiles) {
			UserProfileFormData userProfileFormData = retrieveProfile(userProfile.getUsername());
			userProfilesDTO.add(userProfileFormData);
		}
		return userProfilesDTO;
	}

	@Override
	public void removeRequests(int bookId) {
		List<UserProfile> userProfiles = bookMapper.findById(bookId).get().getRequestingUsers();
		
		for (UserProfile userProfile: userProfiles) {
			deleteBookRequest(userProfile.getUsername(), bookId);
		}
	}
	
	@Override
	public void deleteBookOffer(String username, int bookId) {
		removeRequests(bookId); 
		bookMapper.deleteById(bookId);
	}
	
	@Override
	public void deleteBookRequest(String username, int bookId) {
		Book book = bookMapper.findById(bookId).get();
		UserProfile userProfile = userProfileMapper.findByUsername(username);
		userProfile.deleteRequest(book);
		userProfileMapper.save(userProfile);
	}
}
