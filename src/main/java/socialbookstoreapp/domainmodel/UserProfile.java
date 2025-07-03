package socialbookstoreapp.domainmodel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_profiles")
public class UserProfile {
	@Id
	@Column(name="user_name")
	private String username;

	@Column(name="fullname")
	private String fullName;

	@Column(name="age")
	private int age;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phonenumber")
	private String phonenumber;
	
	@ManyToMany
	@JoinTable(
			name = "favourite_authors",
            joinColumns = @JoinColumn(name="user_profile_id"),
            inverseJoinColumns = @JoinColumn(name="book_author_id"))
	private List<BookAuthor> favouriteBookAuthors;

	@ManyToMany
	@JoinTable(
            name = "favourite_categories",
            joinColumns = @JoinColumn(name="user_profile_id"),
            inverseJoinColumns = @JoinColumn(name="book_category_id"))
	private List<BookCategory> favouriteBookCategories;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_profile_id")
	private List<Book> bookOffers;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL) 
	@JoinTable(
			name = "book_request", 
			joinColumns = @JoinColumn(name="user_profile_id"), 
			inverseJoinColumns = @JoinColumn(name="book_id"))
	private List<Book> requestedBooks; //books that user has requested

	public List<Book> getRequestedBooks() {
		return requestedBooks;
	}

	public void setRequestedBooks(List<Book> requestedBooks) {
		this.requestedBooks = requestedBooks;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public List<BookAuthor> getFavouriteBookAuthors() {
		return favouriteBookAuthors;
	}

	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}

	public List<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}

	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}

	public List<Book> getBookOffers() {
		return bookOffers;
	}

	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}
	
	public void addBookOffer(Book bookOffer) {
		bookOffers.add(bookOffer);
	}
	
	public void deleteBookOffer(Book bookOffer) {
		bookOffers.remove(bookOffer);
	}
	
	public void addRequest(Book book) {
		requestedBooks.add(book);
	}
	
	public void deleteRequest(Book book) {
		requestedBooks.remove(book);
	}
}
