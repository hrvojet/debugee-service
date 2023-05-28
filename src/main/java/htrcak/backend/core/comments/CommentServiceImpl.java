package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;
import htrcak.backend.core.comments.data.CommentRepositoryJPA;
import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssueRepositoryJPA;
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

    private final SecurityContextHolderUtils securityContextHolderUtils;


    private final IssueRepositoryJPA issueRepositoryJPA;

    public CommentServiceImpl(CommentRepositoryJPA commentRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils, IssueRepositoryJPA issueRepositoryJPA) {
        this.commentRepositoryJPA = commentRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
        this.issueRepositoryJPA = issueRepositoryJPA;
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
        return new ResponseEntity<>(
            mapCommentToDTO(commentRepositoryJPA.save(
                new Comment(
                    securityContextHolderUtils.getCurrentUser(),
                    commentPostValidator.getText(),
                    issueRepositoryJPA.getById(commentPostValidator.getIssueId())))),
            HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteById(long commentId) {
        long commentForDeletion = this.commentRepositoryJPA.getById(commentId).getUser().getId();
        if (commentForDeletion == securityContextHolderUtils.getCurrentUser().getId() || securityContextHolderUtils.getCurrentUser().isAdmin()) {
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
            return new ResponseEntity<>(mapCommentToDTO(commentRepositoryJPA.save(comment.get())), HttpStatus.OK);
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
}
