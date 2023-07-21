package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;
import htrcak.backend.core.comments.data.CommentRepositoryJPA;
import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.issues.data.IssueRepositoryJPA;
import htrcak.backend.core.projects.Project;
import htrcak.backend.core.projects.data.ProjectRepositoryJPA;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static htrcak.backend.utils.jpa.CommentSpecification.getAllWithIssueID;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CommentServiceImpl implements CommentService {

    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepositoryJPA commentRepositoryJPA;

    private final IssueRepositoryJPA issueRepositoryJPA;

    private final ProjectRepositoryJPA projectRepositoryJPA;

    private final SecurityContextHolderUtils securityContextHolderUtils;

    public CommentServiceImpl(CommentRepositoryJPA commentRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils, IssueRepositoryJPA issueRepositoryJPA, ProjectRepositoryJPA projectRepositoryJPA) {
        this.commentRepositoryJPA = commentRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
        this.issueRepositoryJPA = issueRepositoryJPA;
        this.projectRepositoryJPA = projectRepositoryJPA;
    }

    @Override
    public List<CommentDTO> findAll() {
        return commentRepositoryJPA.findAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getById(Long id) {
        return commentRepositoryJPA.findById(id).stream().map(this::mapCommentToDTO).findFirst().orElse(null);
    }

    @Override
    public ResponseEntity<CommentDTO> saveNewComment(CommentPostValidator commentPostValidator) {

        Optional<Issue> issue = this.issueRepositoryJPA.findById(commentPostValidator.getIssueId());
        if (issue.isEmpty()) {
            logger.warn(MessageFormat.format("There is no issue with id [{0}]", commentPostValidator.getIssueId()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        issue.get().setCommentNumber(issue.get().getCommentNumber() + 1);
        this.issueRepositoryJPA.save(issue.get());

        Comment newComment = commentRepositoryJPA.save(new Comment(securityContextHolderUtils.getCurrentUser(), commentPostValidator.getText(), issue.get()));
        propagateEditToProject(newComment);

        return new ResponseEntity<>(mapCommentToDTO(newComment), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteById(long commentId) {
        Comment comment = this.commentRepositoryJPA.getById(commentId);
        long commentForDeletion = comment.getUser().getId();

        if (commentForDeletion == securityContextHolderUtils.getCurrentUser().getId() || securityContextHolderUtils.getCurrentUser().isAdmin()) {

            Optional<Issue> issue = this.issueRepositoryJPA.findById(comment.getIssue().getId());
            if (issue.isEmpty()) {
                logger.warn(MessageFormat.format("There is no issue with id [{0}]", comment.getIssue().getId()));
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (issue.get().getCommentNumber() > 0) {
                issue.get().setCommentNumber(issue.get().getCommentNumber() - 1);
                this.issueRepositoryJPA.save(issue.get());
            }

            this.commentRepositoryJPA.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        logger.warn(MessageFormat.format("User with ID {0} not allowed to delete comment with ID {1}", securityContextHolderUtils.getCurrentUser().getId(), commentId));
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @Override
    public ResponseEntity<CommentDTO> updateById(long commentId, CommentPatchValidator commentPatchValidator) { //Todo možda vraćati responseEntityje<?>...?
        Optional<Comment> comment = commentRepositoryJPA.findById(commentId);
        if(comment.isEmpty()) {
            logger.warn(MessageFormat.format("There is no comment with id [{0}]", commentId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (comment.get().getUser().getId() != securityContextHolderUtils.getCurrentUser().getId()) {
            logger.warn(MessageFormat.format("User with ID [{0}] not allowed to modify comment with ID [{1}]", securityContextHolderUtils.getCurrentUser().getId(), commentId));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boolean commentIsUpdated = false;
        if(commentPatchValidator.getText() != null && !commentPatchValidator.getText().isBlank() && !commentPatchValidator.getText().equals(comment.get().getText())) {
            comment.get().setText(commentPatchValidator.getText());
            commentIsUpdated = true;
        }

        if(commentIsUpdated) {
            Comment updatedComment = commentRepositoryJPA.save(comment.get());
            propagateEditToProject(updatedComment);
            propagateEditToIssue(updatedComment);
            return new ResponseEntity<>(mapCommentToDTO(updatedComment), HttpStatus.OK);
        } else {
            logger.debug(MessageFormat.format("No change applied to comment with {0} ID", commentId));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public List<CommentDTO> getAllCommentsByIssueId(long id) {
        return convertToDTOList(commentRepositoryJPA.findAll(where(getAllWithIssueID(id))));
    }


    private CommentDTO mapCommentToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                mapUserToDTO(comment.getUser()),
                comment.getText(),
                comment.getCreated(),
                comment.getEdited()
        );
    }

    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getWebUrl()
        );
    }

    private List<CommentDTO> convertToDTOList(List<Comment> commentList) {
        return commentList.stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    private void propagateEditToProject(Comment comment) {
        Project project = projectRepositoryJPA.getById(comment.getIssue().getProject().getId());
        project.setEdited(comment.getEdited());
        projectRepositoryJPA.save(project);
    }

    private void propagateEditToIssue(Comment comment) {
        Issue issue = issueRepositoryJPA.getById(comment.getIssue().getId());
        issue.setEdited(comment.getEdited());
        issueRepositoryJPA.save(issue);
    }
}
