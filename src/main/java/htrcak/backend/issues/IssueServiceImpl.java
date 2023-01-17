package htrcak.backend.issues;

import htrcak.backend.issues.data.*;
import htrcak.backend.projects.data.ProjectRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static htrcak.backend.utils.jpa.IssueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepositoryJPA issueRepositoryJPA;

    @Autowired
    private ProjectRepositoryJPA projectRepositoryJPA;

    public IssueServiceImpl(IssueRepositoryJPA issueRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
    }


    @Override
    public List<IssueDTO> searchIssues(Long id, IssueSearchCommand isc) {
        return convertToDTOList(issueRepositoryJPA.findAll(
                where(getById(id)
                        .and(findComment(isc)))
        ));
    }

    @Override
    public List<IssueDTO> findAll() {
        return convertToDTOList(issueRepositoryJPA.findAll());
    }

    @Override
    public IssueDTO findById(long id) {
        return issueRepositoryJPA.findById(id).stream().map(this::mapIssueToDTO).findAny().orElse(null);
    }

    @Override
    public Optional<IssueDTO> saveNewIssue(IssuePostValidator issuePostValidator) {
        return Optional.of(mapIssueToDTO(issueRepositoryJPA.save(new Issue(projectRepositoryJPA.getById(issuePostValidator.getProjectId()), issuePostValidator.getTitle(), issuePostValidator.getIssueType()))));
    }

    @Override
    public void deleteById(long issueId) {
        this.issueRepositoryJPA.deleteById(issueId);
    }

    @Override
    public Optional<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId) {
        Issue i = this.issueRepositoryJPA.getById(issueId);
        boolean projectIsUpdated = false;

        if(issuePatchValidator.getTitle() != null && !issuePatchValidator.getTitle().isBlank() && !issuePatchValidator.getTitle().equals(i.getTitle())) {
            i.setTitle(issuePatchValidator.getTitle());
            projectIsUpdated = true;
        }

        if(issuePatchValidator.getIssueType() != null && !issuePatchValidator.getIssueType().isBlank() && !issuePatchValidator.getIssueType().equals(i.getIssueType())) {
            i.setIssueType(issuePatchValidator.getIssueType());
            projectIsUpdated = true;
        }

        if (projectIsUpdated) {
            return Optional.of(mapIssueToDTO(this.issueRepositoryJPA.save(i)));
        } else {
            return Optional.empty();
        }
    }

    private IssueDTO mapIssueToDTO(Issue issue) {
        return new IssueDTO(issue.getId(), issue.getProject().getId(), issue.getTitle(), issue.getCommentNumber(), issue.getIssueType());
    }

    private List<IssueDTO> convertToDTOList(List<Issue> issueList) {
        return issueList.stream().map(this::mapIssueToDTO).collect(Collectors.toList());
    }
}
