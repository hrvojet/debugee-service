package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.*;
import htrcak.backend.core.projects.data.ProjectRepositoryJPA;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static htrcak.backend.utils.jpa.IssueSpecification.getById;
import static htrcak.backend.utils.jpa.IssueSpecification.findComment;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class IssueServiceImpl implements IssueService {

    Logger logger = LoggerFactory.getLogger(IssueServiceImpl.class);

    private final IssueRepositoryJPA issueRepositoryJPA;

    private final SecurityContextHolderUtils securityContextHolderUtils;

    private final ProjectRepositoryJPA projectRepositoryJPA;

    public IssueServiceImpl(IssueRepositoryJPA issueRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils, ProjectRepositoryJPA projectRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
        this.projectRepositoryJPA = projectRepositoryJPA;
    }


    @Override
    public Page<IssueDTO> searchIssues(Long id, Pageable pageable, IssueSearchCommand isc) {
        return issueRepositoryJPA.findAll(
                where(getById(id).and(findComment(isc))),
                pageable
        ).map(IssueDTO::new);
    }

    @Override
    public Page<IssueDTO> getAllIssuesForProject(long id, Pageable pageable) {
        return issueRepositoryJPA.findAll(where(getById(id)), pageable).map(IssueDTO::new);
    }

    @Override
    public Page<IssueDTO> findAll(Pageable pageable) {
        return issueRepositoryJPA.findAll(pageable).map(IssueDTO::new);
    }

    @Override
    public IssueDTO findById(long id) {
        return issueRepositoryJPA.findById(id).stream().map(this::mapIssueToDTO).findAny().orElse(null);
    }

    @Override
    public ResponseEntity<IssueDTO> saveNewIssue(IssuePostValidator issuePostValidator) {
        return new ResponseEntity<>(mapIssueToDTO(issueRepositoryJPA.save(new Issue(
                projectRepositoryJPA.getById(issuePostValidator.getProjectId()),
                issuePostValidator.getTitle(),
                issuePostValidator.getIssueType(),
                securityContextHolderUtils.getCurrentUser()
                ))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteById(long issueId) {

        long issueForDeletionID = this.issueRepositoryJPA.getById(issueId).getOriginalPoster().getId();
        if (issueForDeletionID == securityContextHolderUtils.getCurrentUser().getId() || securityContextHolderUtils.getCurrentUser().isAdmin()) {
            this.issueRepositoryJPA.deleteById(issueId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        logger.warn(MessageFormat.format("User with ID {0} not allowed to delete issue with id {1}", securityContextHolderUtils.getCurrentUser().getId(), issueId));
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<IssueDTO> updateById(IssuePatchValidator issuePatchValidator, long issueId) {
        Optional<Issue> issue = this.issueRepositoryJPA.findById(issueId);
        if (issue.isEmpty()) {
            logger.warn(MessageFormat.format("There is no issue with id [{0}]", issueId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (issue.get().getOriginalPoster().getId() != securityContextHolderUtils.getCurrentUser().getId()) {
            logger.warn(MessageFormat.format("User with id [{0}] not allowed to modify issue with id [{1}]", securityContextHolderUtils.getCurrentUser().getId(), issueId));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boolean projectIsUpdated = false;

        if(issuePatchValidator.getTitle() != null && !issuePatchValidator.getTitle().isBlank() && !issuePatchValidator.getTitle().equals(issue.get().getTitle())) {
            issue.get().setTitle(issuePatchValidator.getTitle());
            projectIsUpdated = true;
        }

        if(issuePatchValidator.getIssueType() != null && !issuePatchValidator.getIssueType().isBlank() && !issuePatchValidator.getIssueType().equals(issue.get().getIssueType())) {
            issue.get().setIssueType(issuePatchValidator.getIssueType());
            projectIsUpdated = true;
        }

        if(issuePatchValidator.isOpened() != issue.get().isOpened()) {
            issue.get().setOpened(issuePatchValidator.isOpened());
            projectIsUpdated = true;
        }

        if (projectIsUpdated) {
            return new ResponseEntity<>(mapIssueToDTO(this.issueRepositoryJPA.save(issue.get())), HttpStatus.OK);
        } else {
            logger.debug(MessageFormat.format("No change applied to issue with {0} ID", issueId));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private IssueDTO mapIssueToDTO(Issue issue) {
        return new IssueDTO(issue);
    }

    private List<IssueDTO> convertToDTOList(List<Issue> issueList) {
        return issueList.stream().map(this::mapIssueToDTO).collect(Collectors.toList());
    }
}
