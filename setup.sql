DROP TABLE ergworkouts;
DROP TABLE subgroupmembers;
DROP TABLE subgroups;
DROP TABLE groupapplications;
DROP TABLE groupmembers;
DROP TABLE groups;
DROP TABLE users;

CREATE TABLE users (
  user_id    SERIAL PRIMARY KEY  NOT NULL,
  email      VARCHAR(255) UNIQUE NOT NULL,
  passhash   VARCHAR(255)        NOT NULL,
  first_name VARCHAR(255)        NOT NULL,
  last_name  VARCHAR(255)        NOT NULL,
  verified   BOOLEAN             NOT NULL,
  joindate   TIMESTAMP DEFAULT current_timestamp
);

INSERT INTO users (email, passhash, first_name, last_name, verified)
VALUES ('admin@admin.com',
        '1000:b80b084e592ead78a94324c8adf58466b14265b2df4fdd1e:17010407d54439f13d7b4058bef6ee5e8d528c8a41999b43',
        'Jiawei', 'Zhang', TRUE);

CREATE TABLE groups (
  group_id    SERIAL PRIMARY KEY  NOT NULL,
  group_name  VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(2000)       NOT NULL,
  public_bool BOOLEAN             NOT NULL,
  createdate  TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE groupmembers (
  group_id   INT     NOT NULL REFERENCES groups (group_id) ON DELETE CASCADE,
  user_id    INT     NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
  admin_bool BOOLEAN NOT NULL,
  coach_bool BOOLEAN NOT NULL,
  joindate   TIMESTAMP DEFAULT current_timestamp,
  UNIQUE (group_id, user_id)
);

CREATE TABLE groupapplications (
  group_id  INT NOT NULL REFERENCES groups (group_id) ON DELETE CASCADE,
  user_id   INT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
  applydate TIMESTAMP DEFAULT current_timestamp,
  UNIQUE (group_id, user_id)
);

CREATE TABLE subgroups (
  subgroup_id   SERIAL PRIMARY KEY NOT NULL,
  group_id      INT                NOT NULL REFERENCES groups (group_id) ON DELETE CASCADE,
  subgroup_name VARCHAR(255)       NOT NULL,
  description   VARCHAR(2000)      NOT NULL,
  createdate    TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE subgroupmembers (
  subgroup_id INT NOT NULL REFERENCES subgroups (subgroup_id) ON DELETE CASCADE,
  user_id     INT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
  joindate    TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE ergworkouts (
  ergworkout_id SERIAL PRIMARY KEY NOT NULL,
  user_id       INT                NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
  workoutdate   TIMESTAMP DEFAULT current_timestamp,
  comment       VARCHAR(255),
  device        VARCHAR(255)       NOT NULL,
  heartrate     INT,
  type          VARCHAR(255)       NOT NULL,
  time          INT                NOT NULL,
  distance      INT                NOT NULL,
  rating        INT                NOT NULL,
  split         INT                NOT NULL,
  format        VARCHAR(255)       NOT NULL,
  details       VARCHAR(255)       NOT NULL
);





