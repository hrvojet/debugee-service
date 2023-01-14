package htrcak.backend.comments;

import htrcak.backend.comments.data.CommentDTO;
import htrcak.backend.comments.data.CommentRepositoryJPA;
import htrcak.backend.projects.Project;
import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.user.model.User;
import htrcak.backend.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJPA commentRepositoryJPA;

    public CommentServiceImpl(CommentRepositoryJPA commentRepositoryJPA) {
        this.commentRepositoryJPA = commentRepositoryJPA;
    }

    @Override
    public List<CommentDTO> findAll() {
        return commentRepositoryJPA.findAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return new CommentDTO(mapUserToDTO(comment.getUser()), comment.getText(), comment.getCreated(), null);
    }

    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
