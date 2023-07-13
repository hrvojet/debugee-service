DELETE FROM issue;
DELETE FROM project;
DELETE FROM comment;

INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (7, 'debugee', 'de@bug.e', true, 'http://192.168.99.101/debugee', 'https://www.gravatar.com/avatar/ebbc39f503f663fb0d4d901d778442c8?s=80&d=identicon');
INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (5, 'Reporter', 'reporter@asd.asd', false, 'http://192.168.99.101/reporter', 'https://www.gravatar.com/avatar/6ed78b513cc97bfad8ca0e97a8347605?s=80&d=identicon');
INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (3, 'developer', 'developer@example.com', false, 'http://192.168.99.101/developer', 'http://192.168.99.101/uploads/-/system/user/avatar/3/avatar.png');
INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (99, 'Hrva', 'hrva@va.hr', false, 'https://example.com/', 'https://material.angular.io/assets/img/examples/shiba1.jpg');
INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (98, 'Luca', 'luca@lu.ca', false, 'https://example.com/', 'https://material.angular.io/assets/img/examples/shiba1.jpg');
INSERT INTO gitlab_user(id, username, email, is_admin, web_url, avatar_url) VALUES (97, 'Lero', 'lero@le.pl', false, 'https://example.com/', 'https://material.angular.io/assets/img/examples/shiba1.jpg');

INSERT INTO project(id, owner, title, description, created, closed_issues, opened_issues) VALUES (1, 99, 'Debugee-web', 'Description of the first project', '2022-12-31 23.00.00', 0, 0);
INSERT INTO project(id, owner, title, description, created, closed_issues, opened_issues) VALUES (2, 98, 'Debugee-backend-services', 'Backend service for debugee project written in java/spring', '2022-12-22 21.00.00', 0, 0);
INSERT INTO project(id, owner, title, description, created, closed_issues, opened_issues) VALUES (3, 97, 'python-pandas', 'Pandas framework for data manipulation or whatnot', '2022-12-12 21.43.00', 0, 0);
INSERT INTO project(id, owner, title, description, created, closed_issues, opened_issues) VALUES (4, 3, 'Devs project', 'DEV dev', '2023-03-21 15.43.44', 0, 0);

INSERT INTO issue(id, original_poster, title, created, comment_number, issue_type, project_id) VALUES (1, 5, 'Problem with persistence', '2023-03-21 09.11.00', 0, 'Bug', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, created, comment_number, issue_type, project_id) VALUES (2, 5, 'Unable to install dependency', '2023-04-27 09.11.00', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, created, comment_number, issue_type, project_id) VALUES (3, 5, 'We need better', '2023-05-23 09.11.00', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, created, comment_number, issue_type, project_id) VALUES (4, 3, 'How to uninstall dependency', '2023-06-14 09.11.00', 0, 'Feature', 1);
UPDATE project SET opened_issues = opened_issues + 1 where id = 1;

INSERT INTO issue(id, original_poster, title, created, comment_number, issue_type, project_id) VALUES (5, 3, 'We need better solution', '2023-07-12 09.11.00', 0, 'Feature', 2);
UPDATE project SET opened_issues = opened_issues + 1 where id = 2;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (1, 5, 'This is some long text that is here, bla bla...', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (2, 5, 'Next text', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (3, 5, 'This comment should be deleted', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (4, 5, 'Next two!!', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (5, 5, 'Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle jfd ksdf test my text. Be cool and deleted, very long text lorem ipsum anor mle', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (6, 3, 'This is not working!!!', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (7, 7, 'Yes it is working!', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 1;

INSERT INTO comment(id, author, text, created, edited, issue_id) VALUES (8, 5, '2 issue', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2);
UPDATE issue SET comment_number = comment_number + 1 WHERE id = 2;
