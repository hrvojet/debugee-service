package htrcak.backend.core.comments;

import htrcak.backend.core.comments.data.CommentDTO;
import htrcak.backend.core.comments.data.CommentPatchValidator;
import htrcak.backend.core.comments.data.CommentPostValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getAllComments(@RequestParam(required = false) final Long issueId) {
        if (issueId == null) {
            return commentService.findAll();
        } else {
            return commentService.getAllCommentsByIssueId(issueId);
        }
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable final Long id) {
        return commentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> saveComment(@Valid @RequestBody final CommentPostValidator commentPostValidator) {
        return commentService.saveNewComment(commentPostValidator);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable long commentId) {
        return commentService.deleteById(commentId);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable final long commentId, @Valid @RequestBody final CommentPatchValidator commentPatchValidator) {
        return commentService.updateById(commentId, commentPatchValidator);
    }

}
