CREATE TABLE IF NOT EXISTS project (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    description VARCHAR(512) NOT NULL
    );

CREATE TABLE IF NOT EXISTS issue (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    comment_number INT NOT NULL,
    issue_type VARCHAR(32) NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    author VARCHAR(256) NOT NULL,
    text VARCHAR(4096) NOT NULL,
    created TIMESTAMP NOT NULL,
    edited TIMESTAMP,
    issue_id INT NOT NULL,
    FOREIGN KEY (issue_id) REFERENCES issue(id)
);