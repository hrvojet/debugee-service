package htrcak.backend.core.utilities;

import htrcak.backend.core.exceptions.UserNotFoundException;
import htrcak.backend.core.user.data.UserRepositoryJPA;
import htrcak.backend.core.user.model.User;
import htrcak.backend.security.ResourceRequester;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextHolderUtils {

    private UserRepositoryJPA userRepositoryJPA;

    public SecurityContextHolderUtils(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    public User getCurrentUser() throws UserNotFoundException {
        ResourceRequester resourceRequester = (ResourceRequester) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long l = Long.valueOf(resourceRequester.getId());
        return userRepositoryJPA.getById(l);
    }
}
