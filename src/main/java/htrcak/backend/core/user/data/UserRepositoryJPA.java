package htrcak.backend.core.user.data;

import htrcak.backend.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {
}
