package htrcak.backend.issues;

import htrcak.backend.issues.data.IssuePostValidator;
import htrcak.backend.issues.data.IssueRepositoryJPA;
import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.projects.data.ProjectRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepositoryJPA issueRepositoryJPA;

    @Autowired
    private ProjectRepositoryJPA projectRepositoryJPA;

    public IssueServiceImpl(IssueRepositoryJPA issueRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
    }

    @Override
    public List<IssueDTO> findAll() {
        return issueRepositoryJPA.findAll().stream().map(this::mapIssueToDTO).collect(Collectors.toList());
    }

    @Override
    public IssueDTO findById(long id) {
        return issueRepositoryJPA.findById(id).stream().map(this::mapIssueToDTO).findAny().orElse(null);
    }

    @Override
    public Optional<IssueDTO> saveNewIssue(long id, IssuePostValidator issuePostValidator) {
        return Optional.of(mapIssueToDTO(issueRepositoryJPA.save(new Issue(projectRepositoryJPA.getById(id), issuePostValidator.getTitle(), issuePostValidator.getIssueType()))));
    }

    private IssueDTO mapIssueToDTO(Issue issue) {
        return new IssueDTO(issue.getId(), issue.getProject().getId(), issue.getTitle(), issue.getCommentNumber(), issue.getIssueType());
    }
}
