package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueRepositoryJPA;
import htrcak.backend.issues.data.IssueDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepositoryJPA issueRepositoryJPA;

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

    private IssueDTO mapIssueToDTO(Issue issue) {
        return new IssueDTO(issue.getId(), issue.getProject().getId(), issue.getTitle(), issue.getCommentNumber(), issue.getIssueType());
    }
}
