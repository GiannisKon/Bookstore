package socialbookstoreapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import socialbookstoreapp.domainmodel.Role;
import socialbookstoreapp.domainmodel.User;
import socialbookstoreapp.formsdata.UserProfileFormData;
import socialbookstoreapp.services.UserProfileService;
import socialbookstoreapp.services.UserService;

@Controller
public class AuthController {
	@Autowired
    private UserService userService;

	@Autowired
	private UserProfileService userProfileService;
	
    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
       
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
        
        userService.saveUser(user);
        if (user.getRole() == Role.USER) {
        	UserProfileFormData userProfileFormData = new UserProfileFormData();
        	userProfileFormData.setUsername(user.getUsername());
        	userProfileService.save(userProfileFormData);
        }

        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}
