package socialbookstoreapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstoreapp.domainmodel.UserProfile;

@Repository
public interface UserProfileMapper extends JpaRepository<UserProfile, String> {
	
	UserProfile findByUsername(String username);
	
}
