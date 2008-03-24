
#create security

#use security;
# --------------------------------------------------------
#     <application-policy name = "SecurityRealm">
       <authentication>
          <login-module code = "org.jboss.security.auth.spi.DatabaseServerLoginModule"  flag = "required">
             <module-option name = "dsJndiName">java:/Security</module-option>
             <module-option name="principalsQuery">SELECT password AS Password FROM user WHERE name = ?</module-option>
             <module-option name="rolesQuery">SELECT RL.name AS Roles, 'Roles' AS RoleGroups FROM role as RL, user as U ,  users_roles as RU WHERE U.userid = RU.userid and RU.roleid = RL.roleid  and U.name = ?</module-option>
                <module-option name="debug">true</module-option>
              <module-option name="hashAlgorithm">MD5</module-option>
              <module-option name="hashEncoding">hex</module-option>
          </login-module>
       </authentication>
  </application-policy>    

# --------------------------------------------------------



CREATE TABLE user (
  userId          char(50) binary NOT NULL default '',
  password        char(50) binary default NULL,
  name            char(50) default NULL,
  email           char(50) default NULL,
  PRIMARY KEY  (userId),
  UNIQUE KEY email (email),
  UNIQUE KEY name (name),
  KEY name_2 (name),
  KEY email_2 (email)
) TYPE=InnoDB;

CREATE TABLE role (
  roleId          char(50) binary NOT NULL default '',
  name            char(100) default NULL,
  PRIMARY KEY  (roleId)
) TYPE=InnoDB;


CREATE TABLE users_roles (
  userId          char(50) NOT NULL default '',
  roleId          char(50) NOT NULL default '',
  KEY userId (userId)
) TYPE=InnoDB;

CREATE TABLE passwordassit (
  userId           char(50) NOT NULL default '',
  passwdtype       varchar(100) default NULL,
  passwdanswer     varchar(100) default NULL,
  PRIMARY KEY  (userid),
  KEY passwdtype (passwdtype)
)  TYPE=InnoDB;


INSERT INTO `user` ( `userId` ,  `name` , `password` , `email` ) VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@yoursite.com');
INSERT INTO `role` ( `roleId` , `name` ) VALUES ('2', 'Admin');
INSERT INTO users_roles VALUES ('1', '2');

