package htrcak.backend.utils.jpa;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import org.springframework.data.jpa.domain.Specification;

public class IssueSpecification {

    public static Specification<Issue> getById(Long id) {
        return id == null ? null : ((root, query, cb) -> cb.equal(root.get("project"), id));
    }

    public static Specification<Issue> findComment(IssueSearchCommand isc) {
        return isc == null ? null : ((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + isc.getTitle().toLowerCase() + "%"));
    }

}
