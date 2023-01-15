package htrcak.backend.comments;

import htrcak.backend.comments.data.CommentDTO;
import htrcak.backend.comments.data.CommentPatchValidator;
import htrcak.backend.comments.data.CommentPostValidator;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDTO> findAll();

    CommentDTO getById(Long id);

    Optional<CommentDTO> saveNewIssue(CommentPostValidator commentPostValidator);

    void deleteById(long commentId);

    Optional<CommentDTO> updateById(long commentId, CommentPatchValidator commentPatchValidator);
}
