package htrcak.backend.core.user.data;

import htrcak.backend.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM gitlab_user where id in (SELECT DISTINCT original_poster FROM ISSUE where project_id = :project_id)", nativeQuery = true)
    List<User> getUserIssuesByProject(@Param("project_id") Long projectId);
}
