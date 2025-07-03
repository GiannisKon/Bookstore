package socialbookstoreapp.domainmodel;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int bookId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="summary")
	private String summary;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "book_write", 
			joinColumns = @JoinColumn(name="book_id"), 
			inverseJoinColumns = @JoinColumn(name="book_author_id"))
	private List<BookAuthor> bookAuthors;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="book_category_id")
	private BookCategory bookCategory;
	
	@ManyToMany(mappedBy="requestedBooks")
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
	
	public void addRequestingUser(UserProfile userProfile) {
		requestingUsers.add(userProfile);
	}
	
	public void deleteRequestingUser(UserProfile userProfile) {
		requestingUsers.remove(userProfile);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		Book other = (Book) obj;
		if (bookId != other.getBookId())
			return false;
		return true;
	}
}
