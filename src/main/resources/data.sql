DELETE FROM issue;
DELETE FROM project;
DELETE FROM comment;

INSERT INTO gitlab_user(id, username, email) VALUES (5, 'Reporter', 'reporter@asd.asd');
INSERT INTO gitlab_user(id, username, email) VALUES (99, 'Hrva', 'hrva@va.hr');

INSERT INTO project(id, title, description, closed_issues, opened_issues) VALUES (1, 'Title 1', 'Description of the first project', 0, 0);
INSERT INTO project(id, title, description, closed_issues, opened_issues) VALUES (2, 'Title 2', 'Description of the second project', 0, 0);
INSERT INTO project(id, title, description, closed_issues, opened_issues) VALUES (3, 'Title 3', 'Description of the third project', 0, 0);

INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (1, 'Issue 1', 0, 'Bug', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (2, 'Issue 2', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (3, 'Issue 3', 0, 'Feature', 2);
UPDATE project SET opened_issues = opened_issues + 1 where id = 2;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (1, 5, 'This is some long text that is here, bla bla...', CURRENT_TIMESTAMP, null, 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;
