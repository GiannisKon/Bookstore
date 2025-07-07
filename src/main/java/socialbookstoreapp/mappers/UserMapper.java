package socialbookstoreapp.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstoreapp.domainmodel.User;

@Repository
public interface UserMapper extends JpaRepository<User, String> {  
	
	Optional<User> findByUsername(String username);
	
}
