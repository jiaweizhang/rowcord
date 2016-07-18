# Add Users

# --- !Ups

CREATE TABLE Users (
  userId BIGSERIAL NOT NULL,
  email varchar(255) NOT NULL,
  passhash varchar(255) NOT NULL,
  CONSTRAINT PK_users PRIMARY KEY(userId),
  CONSTRAINT UQ_users_email UNIQUE(email)
);

# --- !Downs

DROP TABLE Users;