package htrcak.backend.core.utilities;

import htrcak.backend.core.exceptions.UserNotFoundException;
import htrcak.backend.core.user.UserService;
import htrcak.backend.core.user.model.User;
import htrcak.backend.security.ResourceRequester;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextHolderUtils {

    private final UserService userService;

    public SecurityContextHolderUtils(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() throws UserNotFoundException {
        ResourceRequester resourceRequester = (ResourceRequester) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserByID(Long.parseLong(resourceRequester.getId()));
    }
}
