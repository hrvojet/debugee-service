package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDTO> findAll();

    CommentDTO getById(Long id);

    Optional<CommentDTO> saveNewComment(CommentPostValidator commentPostValidator);

    void deleteById(long commentId);

    Optional<CommentDTO> updateById(long commentId, CommentPatchValidator commentPatchValidator);
}
