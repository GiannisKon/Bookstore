package socialbookstoreapp.formsdata;

import java.util.List;
import java.util.Objects;

import socialbookstoreapp.domainmodel.BookAuthor;
import socialbookstoreapp.domainmodel.BookCategory;
import socialbookstoreapp.domainmodel.UserProfile;

public class BookFormData {
	private int bookId;

	private String title;
	
	private String summary;
	
	private List<BookAuthor> bookAuthors;
	
	private BookCategory bookCategory;
	
	private List<UserProfile> requestingUsers;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		BookFormData other = (BookFormData) obj;
		if (bookId != other.getBookId())
			return false;
		return true;
	}
}
