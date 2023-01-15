package htrcak.backend.comments;

import htrcak.backend.comments.data.CommentDTO;
import htrcak.backend.comments.data.CommentPatchValidator;
import htrcak.backend.comments.data.CommentPostValidator;
import htrcak.backend.comments.data.CommentRepositoryJPA;
import htrcak.backend.issues.data.IssueRepositoryJPA;
import htrcak.backend.projects.Project;
import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.user.data.UserRepositoryJPA;
import htrcak.backend.user.model.User;
import htrcak.backend.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJPA commentRepositoryJPA;

    private final UserRepositoryJPA userRepositoryJPA;

    private final IssueRepositoryJPA issueRepositoryJPA;

    public CommentServiceImpl(CommentRepositoryJPA commentRepositoryJPA, UserRepositoryJPA userRepositoryJPA, IssueRepositoryJPA issueRepositoryJPA) {
        this.commentRepositoryJPA = commentRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
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
    public Optional<CommentDTO> saveNewIssue(CommentPostValidator commentPostValidator) {
        return Optional.of(mapCommentToDTO(commentRepositoryJPA.save(new Comment(userRepositoryJPA.getById(commentPostValidator.getUserId()), commentPostValidator.getText(), issueRepositoryJPA.getById(commentPostValidator.getIssueId())))));
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
        return new CommentDTO(comment.getId(), mapUserToDTO(comment.getUser()), comment.getText(), comment.getCreated(), comment.getEdited());
    }

    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
