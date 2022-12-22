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

    private IssueDTO mapIssueToDTO(Issue issue) {
        return new IssueDTO(issue.getId(), issue.getTitle(), issue.getCommentNumber(), issue.getIssueType());
    }

    private Set<IssueDTO> mapIssuesToDTO(Set<Issue> issues) {
        Set<IssueDTO> issueDTOS = new HashSet<>();

        for(Issue i : issues) {
            issueDTOS.add(new IssueDTO(i.getId(), i.getTitle(), i.getCommentNumber(), i.getIssueType()));
        }

        return issueDTOS;
    }
}
