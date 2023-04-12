package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDTO> findAll();

    CommentDTO getById(Long id);

    ResponseEntity<CommentDTO> saveNewComment(CommentPostValidator commentPostValidator);

    ResponseEntity<?> deleteById(long commentId);

    ResponseEntity<CommentDTO> updateById(long commentId, CommentPatchValidator commentPatchValidator);
}
