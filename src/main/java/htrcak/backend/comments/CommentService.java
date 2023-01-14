package htrcak.backend.comments;

import htrcak.backend.comments.data.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findAll();
}
