package htrcak.backend.utils.jpa;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import htrcak.backend.core.label.Label;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class IssueSpecification {

    public static Specification<Issue> getById(Long id) {
        return id == null ? null : ((root, query, cb) -> cb.equal(root.get("project"), id));
    }

    public static Specification<Issue> findComment(IssueSearchCommand isc) {
        return isc.getTitle() == null ? null : ((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + isc.getTitle().toLowerCase() + "%"));
    }

    public static Specification<Issue> findLabel(Long labelID) {
        // SELECT * FROM ISSUE INNER JOIN ISSUE_LABEL ON ISSUE.ID = ISSUE_LABEL.ISSUE_ID INNER JOIN LABEL ON LABEL.ID = ISSUE_LABEL.LABEL_ID WHERE LABEL.ID = 3;
        return labelID == null ? null : ((root, query, cb) -> {
            Join<Issue, Label> labelJoin = root.join("labelsSet", JoinType.INNER);

            return cb.equal(labelJoin.get("id"), labelID);
        });
    }

    public static Specification<Issue> findUser(Long userId) {
        return userId == null ? null : ((issue, query, cb) -> cb.equal(issue.get("originalPoster").get("id"), userId));
    }

}
