package socialbookstoreapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstoreapp.domainmodel.Book;

@Repository
public interface BookMapper extends JpaRepository<Book, Integer> {

	List<Book> findByTitle(String title);
	List<Book> findByTitleContaining(String title);
}
