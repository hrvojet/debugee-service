package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssuePatchValidator;
import htrcak.backend.core.issues.data.IssuePostValidator;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {

    List<IssueDTO> findAll();

    IssueDTO findById(long id);

    ResponseEntity<IssueDTO> saveNewIssue(IssuePostValidator issuePostValidator);

    ResponseEntity<?> deleteById(long issueId);

    ResponseEntity<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId);

    List<IssueDTO> searchIssues(Long id, IssueSearchCommand isc);
}
