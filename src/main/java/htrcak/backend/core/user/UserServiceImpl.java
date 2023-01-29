package htrcak.backend.core.user;

import htrcak.backend.core.user.data.UserRepositoryJPA;
import htrcak.backend.core.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

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
}
