package com.kyterescue.services;

import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service("authenticationService")
public class AuthenticationService {
    private User loggedInUser;
    private final UserRepository usersDao;


    AuthenticationService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    public void sendAuthenticatedUserDetails(Model model) {
        Object authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(authenticatedUser instanceof UserDetails) {
            User castedUser = (User) authenticatedUser;
            long userId = castedUser.getId();
            User loginUser = usersDao.getUserById(userId);
            model.addAttribute("user", loginUser);
        }
    }

    public User grabAuthenticationUserDetails(Model model) {
        User loginUser = null;
        Object authenticationDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(authenticationDetails instanceof UserDetails) {
            User castedUser = (User) authenticationDetails;
            long userId = castedUser.getId();
            loginUser = usersDao.getUserById(userId);
        }
        return loginUser;
    }


}
