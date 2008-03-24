
CREATE TABLE jiveForum (
  forumID               BIGINT NOT NULL,
  name                  VARCHAR(255) UNIQUE NOT NULL,
  description           TEXT,
  modDefaultThreadVal   BIGINT NOT NULL,
  modMinThreadVal       BIGINT NOT NULL,
  modDefaultMsgVal      BIGINT NOT NULL,
  modMinMsgVal          BIGINT NOT NULL,
  modifiedDate          VARCHAR(15) NOT NULL,
  creationDate          VARCHAR(15) NOT NULL,
  PRIMARY KEY           (forumID),
  INDEX jiveForum_name_idx    (name(10))
)TYPE=InnoDB;

CREATE TABLE jiveForumProp (
  forumID       BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (forumID,name)
)TYPE=InnoDB;

CREATE TABLE jiveThread (
  threadID          BIGINT NOT NULL,
  forumID           BIGINT NOT NULL,
  rootMessageID     BIGINT NOT NULL,
  modValue          BIGINT NOT NULL,
  rewardPoints      INT NOT NULL,
  creationDate      VARCHAR(15) NOT NULL,
  modifiedDate      VARCHAR(15) NOT NULL,
  PRIMARY KEY       (threadID),
  INDEX jiveThread_forumID_idx (forumID),
  INDEX jiveThread_modValue_idx (modValue),
  INDEX jiveThread_cDate_idx   (creationDate),
  INDEX jiveThread_mDate_idx   (modifiedDate)
)TYPE=InnoDB;

CREATE TABLE jiveThreadProp (
  threadID      BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (threadID,name)
)TYPE=InnoDB;

CREATE TABLE jiveMessage (
  messageID             BIGINT NOT NULL,
  parentMessageID       BIGINT NULL, #defaul is null 
  threadID              BIGINT NOT NULL,
  forumID               BIGINT NOT NULL,
  userID                BIGINT NULL,
  subject               VARCHAR(255),
  body                  TEXT,
  modValue              BIGINT NOT NULL,
  rewardPoints          INT NOT NULL,
  creationDate          VARCHAR(15) NOT NULL,
  modifiedDate          VARCHAR(15) NOT NULL,
  PRIMARY KEY           (messageID),
  INDEX jiveMessage_threadID_idx  (threadID),
  INDEX jiveMessage_forumID_idx   (forumID),
  INDEX jiveMessage_userID_idx    (userID),
  INDEX jiveMessage_modValue_idx  (modValue),
  INDEX jiveMessage_cDate_idx     (creationDate),
  INDEX jiveMessage_mDate_idx     (modifiedDate)
)TYPE=InnoDB;

CREATE TABLE jiveMessageProp (
  messageID    BIGINT NOT NULL,
  name         VARCHAR(100) NOT NULL,
  propValue    TEXT NOT NULL,
  PRIMARY KEY  (messageID,name)
)TYPE=InnoDB;


CREATE TABLE jiveID (
  idType        INT NOT NULL,
  id            BIGINT NOT NULL,
  PRIMARY KEY   (idType)
)TYPE=InnoDB;


# Finally, insert default table values.

# Unique ID entry for forum, thread, messages, user, group.
# The User ID entry starts at 200 (after admin user entry).
insert into jiveID values (0, 100);
insert into jiveID values (1, 100);
insert into jiveID values (2, 100);
insert into jiveID values (3, 200);
insert into jiveID values (4, 100);
