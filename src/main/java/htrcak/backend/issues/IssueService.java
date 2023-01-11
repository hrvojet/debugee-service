package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.issues.data.IssuePatchValidator;
import htrcak.backend.issues.data.IssuePostValidator;
import htrcak.backend.projects.data.ProjectDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<IssueDTO> findAll();

    IssueDTO findById(long id);

    Optional<IssueDTO> saveNewIssue(long id, IssuePostValidator issuePostValidator);

    void deleteById(long issueId);

    Optional<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId);
}
