
CREATE TABLE setup (
  name varchar(50) NOT NULL default '',
  value text NOT NULL,
  PRIMARY KEY name (name)  
)TYPE=InnoDB;


CREATE TABLE upload (
  objectId            char(50) NOT NULL default '',
  name                varchar(50) default '',
  description         varchar(200) default '',
  datas               blob,
  size                int(20) NOT NULL default '0',  
  messageId           varchar(20) NOT NULL default '0',
  creationDate        VARCHAR(15) NOT NULL,
  PRIMARY KEY  (objectId),
  KEY messageId (messageId)
) ;