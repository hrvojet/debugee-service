DELETE FROM issue;
DELETE FROM project;
DELETE FROM comment;

INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (7, 'debugee', 'de@bug.e', true);
INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (5, 'Reporter', 'reporter@asd.asd', false);
INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (3, 'developer', 'developer@example.com', false);
INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (99, 'Hrva', 'hrva@va.hr', false);
INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (98, 'Luca', 'luca@lu.ca', false);
INSERT INTO gitlab_user(id, username, email, is_admin) VALUES (97, 'Lero', 'lero@le.pl', false);

INSERT INTO project(id, owner, title, description, closed_issues, opened_issues) VALUES (1, 99, 'Title 1', 'Description of the first project', 0, 0);
INSERT INTO project(id, owner, title, description, closed_issues, opened_issues) VALUES (2, 98, 'Title 2', 'Description of the second project', 0, 0);
INSERT INTO project(id, owner, title, description, closed_issues, opened_issues) VALUES (3, 97, 'Title 3', 'Description of the third project', 0, 0);

INSERT INTO issue(id, original_poster, title, comment_number, issue_type, project_id) VALUES (1, 5, 'Problem with persistence', 0, 'Bug', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, comment_number, issue_type, project_id) VALUES (2, 5, 'Unable to install dependency', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, comment_number, issue_type, project_id) VALUES (3, 5, 'We need better', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, comment_number, issue_type, project_id) VALUES (4, 5, 'How to uninstall dependency', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, comment_number, issue_type, project_id) VALUES (5, 5, 'We need better solution', 0, 'Feature', 2);
UPDATE project SET opened_issues = opened_issues + 1 where id = 2;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (1, 5, 'This is some long text that is here, bla bla...', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (2, 5, 'Next text', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (3, 5, 'This comment should be deleted', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (4, 5, 'Next two!!', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (5, 5, 'Be cool and deleted', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;
