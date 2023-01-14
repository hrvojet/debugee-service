package htrcak.backend.issues.data;

import htrcak.backend.issues.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepositoryJPA extends JpaRepository<Issue, Long> {

    List<Issue> findAll();

    List<Issue> findAllByProjectId(Long id);

    List<Issue> findById(long id);

}
