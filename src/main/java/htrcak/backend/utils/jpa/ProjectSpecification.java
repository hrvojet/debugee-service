package htrcak.backend.utils.jpa;

import htrcak.backend.core.projects.Project;
import htrcak.backend.core.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Collection;

public class ProjectSpecification {

    public static Specification<Project> favouriteByUser(final Long userId) {
        return userId == null ? null : (root, query, cb) -> {

            long userIdp = userId;
            query.distinct(true);
            Root<Project> project = root;
            /*Subquery<User> userSubquery = query.subquery(User.class);
            Root<User> user = userSubquery.from(User.class);*/
            Subquery<User> userSubquery = query.subquery(User.class);
            Root<User> user = userSubquery.from(User.class);

            Expression<Collection<Project>> favouriteProjects = user.get("favouriteProjects");
            userSubquery.select(user);
            userSubquery.where(cb.equal(user.get("id"), userIdp), cb.isMember(project, favouriteProjects));
            return cb.exists(userSubquery);
        };
    }
}
