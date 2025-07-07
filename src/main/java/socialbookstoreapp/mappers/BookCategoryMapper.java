package socialbookstoreapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstoreapp.domainmodel.BookCategory;

@Repository
public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	
	BookCategory findByName(String name);
	
}
