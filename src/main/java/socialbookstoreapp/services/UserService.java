package socialbookstoreapp.services;

import org.springframework.stereotype.Service;

import socialbookstoreapp.domainmodel.User;

@Service
public interface UserService {

	public void saveUser(User user);
	public boolean isUserPresent(User user);
}