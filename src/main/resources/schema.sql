CREATE TABLE IF NOT EXISTS gitlab_user(
    id INT PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS project (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner INT NOT NULL,
    title VARCHAR(256) NOT NULL,
    description VARCHAR(512) NOT NULL,
    closed_issues INT NOT NULL,
    opened_issues INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES gitlab_user(id)
    );

CREATE TABLE IF NOT EXISTS issue (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    comment_number INT NOT NULL,
    issue_type VARCHAR(32) NOT NULL,
    project_id INT NOT NULL,
    status VARCHAR(45),
    FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    author INT NOT NULL,
    issue_id INT NOT NULL,
    text VARCHAR(4096) NOT NULL,
    created TIMESTAMP NOT NULL,
    edited TIMESTAMP,
    FOREIGN KEY (issue_id) REFERENCES issue(id),
    FOREIGN KEY (author) REFERENCES gitlab_user(id)
);