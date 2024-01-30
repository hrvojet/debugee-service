package htrcak.backend.core.issues.data;

import htrcak.backend.core.issues.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepositoryJPA extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    List<Issue> findAllByProjectId(Long id);

    @Query(value = "SELECT count(*) FROM issue WHERE is_opened = true and project_id = :project_id", nativeQuery = true)
    int openedIssuesForProject(@Param("project_id") Long projectId);
}
