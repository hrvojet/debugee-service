package htrcak.backend.core.comments.data;

import htrcak.backend.core.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentRepositoryJPA extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

}
