DELETE FROM issue;
DELETE FROM project;

INSERT INTO project(id, title, description) VALUES (1, 'Title 1', 'Description of first project');

INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (1, 'Issue 1', 0, 'Bug', 1);
INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (2, 'Issue 2', 0, 'Feature', 1);