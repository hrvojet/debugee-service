CREATE TABLE IF NOT EXISTS gitlab_user(
    id INT PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL,
    is_admin BOOLEAN NOT NULL,
    web_url VARCHAR(256),
    avatar_url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS project (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner INT NOT NULL,
    title VARCHAR(256) NOT NULL,
    description VARCHAR(512) NOT NULL,
    created TIMESTAMP NOT NULL,
    edited TIMESTAMP NOT NULL,
    closed_issues INT NOT NULL,
    opened_issues INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES gitlab_user(id)
);

CREATE TABLE IF NOT EXISTS label (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id INT NOT NULL,
    label_name VARCHAR(64) NOT NULL,
    description VARCHAR(256),
    color_hex VARCHAR(7),
    FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE IF NOT EXISTS issue (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    created TIMESTAMP NOT NULL,
    edited TIMESTAMP NOT NULL,
    comment_number INT NOT NULL,
    issue_type VARCHAR(32) NOT NULL,
    project_id INT NOT NULL,
    original_poster INT NOT NULL,
    is_opened BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (original_poster) REFERENCES gitlab_user(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    author INT NOT NULL,
    issue_id INT NOT NULL,
    text VARCHAR(4096) NOT NULL,
    created TIMESTAMP NOT NULL,
    edited TIMESTAMP NOT NULL,
    FOREIGN KEY (issue_id) REFERENCES issue(id),
    FOREIGN KEY (author) REFERENCES gitlab_user(id)
);

CREATE TABLE IF NOT EXISTS issue_label (
    label_id INT NOT NULL,
    issue_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_favourite_project (
    user_id INT NOT NULL,
    project_id INT NOT NULL
);
