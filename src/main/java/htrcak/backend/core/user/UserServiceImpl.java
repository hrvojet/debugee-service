package htrcak.backend.core.user;

import htrcak.backend.core.user.data.UserRepositoryJPA;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryJPA userRepositoryJPA;

    public UserServiceImpl(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public void saveNewUser(User user) {
        userRepositoryJPA.save(user);
    }

    @Override
    public User getUserByID(long id) {
        return userRepositoryJPA.getById(id);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUserIssuesByProject(Long projectId) {
         return new ResponseEntity<>(userRepositoryJPA.getUserIssuesByProject(projectId).stream().map(UserDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}
