DELETE FROM issue;
DELETE FROM project;

INSERT INTO project(id, title, description) VALUES (1, 'Title 1', 'Description of the first project');
INSERT INTO project(id, title, description) VALUES (2, 'Title 2', 'Description of the second project');
INSERT INTO project(id, title, description) VALUES (3, 'Title 3', 'Description of the third project');

INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (1, 'Issue 1', 0, 'Bug', 1);
INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (2, 'Issue 2', 0, 'Feature', 1);
INSERT INTO issue(id, title, comment_number, issue_type, project_id) VALUES (3, 'Issue 1', 0, 'Feature', 2);
