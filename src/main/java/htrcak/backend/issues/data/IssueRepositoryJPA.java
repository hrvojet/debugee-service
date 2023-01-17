package htrcak.backend.issues.data;

import htrcak.backend.issues.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IssueRepositoryJPA extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    List<Issue> findAllByProjectId(Long id);

    List<Issue> findById(long id);

}
