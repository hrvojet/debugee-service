package htrcak.backend.core.user;

import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    private final SecurityContextHolderUtils securityContextHolderUtils;

    public UserController(UserService userService, SecurityContextHolderUtils securityContextHolderUtils) {
        this.userService = userService;
        this.securityContextHolderUtils = securityContextHolderUtils;
    }

    @GetMapping("/{id}")
    UserDTO getUserByID(@PathVariable final Long id) {
        return mapUserToDTO(userService.getUserByID(id));
    }

    @GetMapping
    UserDTO getCurrentUser() {
       return mapUserToDTO(securityContextHolderUtils.getCurrentUser());
    }

    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getWebUrl()
        );
    }

}
