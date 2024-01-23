package htrcak.backend.core.user;

import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    void saveNewUser(User user);

    User getUserByID(long id);

    ResponseEntity<List<UserDTO>> getUserIssuesByProject(Long projectId);
}
