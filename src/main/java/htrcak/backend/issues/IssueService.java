package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.issues.data.IssuePatchValidator;
import htrcak.backend.issues.data.IssuePostValidator;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<IssueDTO> findAll();

    List<IssueDTO> findAllByProjectId(Long projectId);

    IssueDTO findById(long id);

    Optional<IssueDTO> saveNewIssue(IssuePostValidator issuePostValidator);

    void deleteById(long issueId);

    Optional<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId);
}
