package com.kyterescue.services;
import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthenticationService {
    private final UserRepository usersDao;

    AuthenticationService(UserRepository usersDao) {
        this.usersDao = usersDao;
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
