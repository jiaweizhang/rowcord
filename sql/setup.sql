/* Users table */
CREATE TABLE IF NOT EXISTS users (
  userId   BIGSERIAL    NOT NULL,
  email    VARCHAR(255) NOT NULL,
  passhash VARCHAR(255) NOT NULL,
  CONSTRAINT PK_users PRIMARY KEY (userId),
  CONSTRAINT UQ_users_email UNIQUE (email)
);

/* GroupType table */
CREATE TABLE IF NOT EXISTS groupTypes (
  groupTypeId          INT          NOT NULL,
  groupTypeName        VARCHAR(255) NOT NULL,
  groupTypeDescription VARCHAR(255) NOT NULL,
  CONSTRAINT PK_groupTypes PRIMARY KEY (groupTypeId)
);

/* Fill groupType table */
INSERT INTO groupTypes
(groupTypeId, groupTypeName, groupTypeDescription)
  SELECT
    1,
    'Open',
    'An open group'
  WHERE
    NOT EXISTS(
        SELECT groupTypeId
        FROM groupTypes
        WHERE groupTypeId = 1
    );

INSERT INTO groupTypes
(groupTypeId, groupTypeName, groupTypeDescription)
  SELECT
    2,
    'Closed',
    'A closed group'
  WHERE
    NOT EXISTS(
        SELECT groupTypeId
        FROM groupTypes
        WHERE groupTypeId = 2
    );

INSERT INTO groupTypes
(groupTypeId, groupTypeName, groupTypeDescription)
  SELECT
    3,
    'Secret',
    'A secret group'
  WHERE
    NOT EXISTS(
        SELECT groupTypeId
        FROM groupTypes
        WHERE groupTypeId = 3
    );

/* Groups table */
CREATE TABLE IF NOT EXISTS groups (
  groupId            BIGSERIAL     NOT NULL,
  groupName          VARCHAR(255)  NOT NULL,
  groupDescription   VARCHAR(2000) NOT NULL,
  groupTypeId        INT           NOT NULL,
  defaultPermissions BIGINT        NOT NULL DEFAULT 0,
  CONSTRAINT PK_groups PRIMARY KEY (groupId),
  CONSTRAINT UX_groups_groupName UNIQUE (groupName),
  CONSTRAINT FK_groups_groupTypeId FOREIGN KEY (groupTypeId) REFERENCES groupTypes (groupTypeId)
);

/* Groupmembers table */
CREATE TABLE IF NOT EXISTS groupMembers (
  groupId     BIGINT NOT NULL,
  userId      BIGINT NOT NULL,
  permissions BIGINT NOT NULL,
  CONSTRAINT PK_groupMembers PRIMARY KEY (groupId, userId),
  CONSTRAINT FK_groupMembers_groupId FOREIGN KEY (groupId) REFERENCES groups (groupId),
  CONSTRAINT FK_groupMembers_userId FOREIGN KEY (userId) REFERENCES users (userId)
);

/* Group invitations table */
CREATE TABLE IF NOT EXISTS groupInvitations (
  groupId BIGINT NOT NULL,
  userId  BIGINT NOT NULL,
  CONSTRAINT PK_groupInvitations PRIMARY KEY (groupId, userId),
  CONSTRAINT FK_groupInvitations_groupId FOREIGN KEY (groupId) REFERENCES groups (groupId),
  CONSTRAINT FK_groupInvitations_userId FOREIGN KEY (userId) REFERENCES users (userId)
);

/* Login logging */
CREATE TABLE IF NOT EXISTS loginLogs (
  userId    BIGINT                                                         NOT NULL,
  isSuccess BOOLEAN                                                        NOT NULL,
  timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc') NOT NULL,
  ip        INET                                                           NOT NULL,
  CONSTRAINT FK_loginLogs_userId FOREIGN KEY (userId) REFERENCES users (userId)
);