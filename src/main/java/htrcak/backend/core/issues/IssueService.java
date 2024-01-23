package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssuePatchValidator;
import htrcak.backend.core.issues.data.IssuePostValidator;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {

    Page<IssueDTO> findAll(Pageable pageable);

    IssueDTO findById(long id);

    ResponseEntity<IssueDTO> saveNewIssue(Long projectID, IssuePostValidator issuePostValidator);

    ResponseEntity<?> deleteById(long issueId);

    ResponseEntity<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId);

    Page<IssueDTO> searchIssues(Long projectID, Pageable pageable, IssueSearchCommand isc, Long labelID, Long userId);

    Page<IssueDTO> getAllIssuesForProject(long id, Pageable pageable);
}
