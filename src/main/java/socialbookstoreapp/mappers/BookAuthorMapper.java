package socialbookstoreapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstoreapp.domainmodel.BookAuthor;

@Repository
public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer>{

	BookAuthor findByName(String name);
	
}
