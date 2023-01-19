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
@RequestMapping("comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable final Long id) {
        return commentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> saveComment(@Valid @RequestBody final CommentPostValidator commentPostValidator) {

        return commentService.saveNewIssue(commentPostValidator)
                .map(IssueDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(IssueDTO))
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build()
                );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable long commentId) {
        try{
            commentService.deleteById(commentId);
        } catch (EmptyResultDataAccessException e) {
            Map<String,String> json = new HashMap<>();
            json.put("projectID",Long.toString(commentId));
            json.put("error","No comment with such ID");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable final long commentId, @Valid @RequestBody final CommentPatchValidator commentPatchValidator) {

        return commentService.updateById(commentId, commentPatchValidator)
                .map(IssueDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(IssueDTO))
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build()
                );
    }

}
