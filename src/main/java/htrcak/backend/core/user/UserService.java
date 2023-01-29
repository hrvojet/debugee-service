package htrcak.backend.core.user;

import htrcak.backend.core.user.model.User;

public interface UserService {

    void saveNewUser(User user);

    User getUserByID(long id);
}
