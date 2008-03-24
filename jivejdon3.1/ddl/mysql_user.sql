
CREATE TABLE jiveUser (
  userID         BIGINT NOT NULL,
  username        VARCHAR(30) UNIQUE NOT NULL,
  passwordHash    VARCHAR(32) NOT NULL,
  name            VARCHAR(100),
  nameVisible     INT NOT NULL,
  email           VARCHAR(100) NOT NULL,
  emailVisible    INT NOT NULL,
  rewardPoints    INT NOT NULL,
  creationDate    VARCHAR(15) NOT NULL,
  modifiedDate    VARCHAR(15) NOT NULL,
  PRIMARY KEY     (userID),
  INDEX jiveUser_username_idx (username(10)),
  INDEX jiveUser_cDate_idx    (creationDate)
)TYPE=InnoDB;

insert into jiveUser (userID,name,username,passwordHash,email,emailVisible,nameVisible,creationDate,modifiedDate,rewardPoints)
  values (1,'Administrator','admin','21232f297a57a5a743894a0e4a801fc3','admin@yoursite.com',1,1,'0','0',0);

CREATE TABLE jiveUserProp (
  userID        BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  propValue     TEXT NOT NULL,
  PRIMARY KEY   (userID,name)
);

CREATE TABLE jiveReward (
  userID          char(50) NOT NULL,
  creationDate    VARCHAR(15) NOT NULL,
  rewardPoints    BIGINT NOT NULL,
  messageID       BIGINT NULL,
  threadID        BIGINT NULL,
  INDEX jiveReward_userID_idx (userID),
  INDEX jiveReward_creationDate_idx (creationDate),
  INDEX jiveReward_messageID_idx (messageID),
  INDEX jiveReward_threadID_idx (threadID)
)TYPE=InnoDB;


CREATE TABLE jiveModeration (
  objectID    BIGINT NOT NULL,
  objectType  BIGINT NOT NULL,
  userID      char(50) NULL,
  modDate     VARCHAR(15) NOT NULL,
  modValue    BIGINT NOT NULL,
  INDEX jiveModeration_objectID_idx (objectID),
  INDEX jiveModeration_objectType_idx (objectType),
  INDEX jiveModeration_userID_idx (userID)
)TYPE=InnoDB;

