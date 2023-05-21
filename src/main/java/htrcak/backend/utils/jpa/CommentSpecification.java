package htrcak.backend.utils.jpa;

import htrcak.backend.core.comments.Comment;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {

    public static Specification<Comment> getAllWithIssueID(Long id) {
        return id == null ? null : (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("issue"), id)));
    }
}
