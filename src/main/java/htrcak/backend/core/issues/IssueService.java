package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssuePatchValidator;
import htrcak.backend.core.issues.data.IssuePostValidator;
import htrcak.backend.core.issues.data.IssueSearchCommand;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<IssueDTO> findAll();

    IssueDTO findById(long id);

    Optional<IssueDTO> saveNewIssue(IssuePostValidator issuePostValidator);

    void deleteById(long issueId);

    Optional<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId);

    List<IssueDTO> searchIssues(Long id, IssueSearchCommand isc);
}
