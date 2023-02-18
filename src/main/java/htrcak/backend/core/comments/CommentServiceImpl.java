package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;
import htrcak.backend.core.comments.data.CommentRepositoryJPA;
import htrcak.backend.core.issues.data.IssueRepositoryJPA;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

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
    public Optional<CommentDTO> saveNewComment(CommentPostValidator commentPostValidator) {
        return Optional.of(mapCommentToDTO(commentRepositoryJPA.save(new Comment(securityContextHolderUtils.getCurrentUser(), commentPostValidator.getText(), issueRepositoryJPA.getById(commentPostValidator.getIssueId())))));
    }

    @Override
    public void deleteById(long commentId) {
        this.commentRepositoryJPA.deleteById(commentId);
    }

    @Override
    public Optional<CommentDTO> updateById(long commentId, CommentPatchValidator commentPatchValidator) {
        Comment c = commentRepositoryJPA.getById(commentId);
        boolean commentIsUpdated = false;

        if(commentPatchValidator.getText() != null && !commentPatchValidator.getText().isBlank() && !commentPatchValidator.getText().equals(c.getText())) {
            c.setText(commentPatchValidator.getText());
            commentIsUpdated = true;
        }

        if(commentIsUpdated) {
            return Optional.of(mapCommentToDTO(commentRepositoryJPA.save(c)));
        } else {
            return Optional.empty();
        }
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
                user.getEmail()
        );
    }
}
