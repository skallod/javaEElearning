CREATE TABLE users (
  USER_ID INT NOT NULL,
  USERNAME VARCHAR(45) NOT NULL,
  PASSWORD VARCHAR(45) NOT NULL,
  ENABLED boolean NOT NULL,
  CONSTRAINT USER_ID_pkey PRIMARY KEY (USER_ID)
);

CREATE TABLE user_roles (
  USER_ROLE_ID INT NOT NULL,
  USER_ID INT NOT NULL,
  AUTHORITY VARCHAR(45) NOT NULL,
  CONSTRAINT USER_ROLE_ID_pkey PRIMARY KEY (USER_ROLE_ID),
  CONSTRAINT FK_user_roles FOREIGN KEY (USER_ID) REFERENCES users (USER_ID)
);

INSERT INTO users (USER_ID, USERNAME,PASSWORD, ENABLED) VALUES (1, 'admin', 'admin', TRUE);
INSERT INTO users (USER_ID, USERNAME,PASSWORD, ENABLED) VALUES (2, 'user', 'user', TRUE);

INSERT INTO user_roles (USER_ROLE_ID, USER_ID,AUTHORITY) VALUES (1, 1, 'ROLE_USER');
INSERT INTO user_roles (USER_ROLE_ID, USER_ID,AUTHORITY) VALUES (2, 1, 'ROLE_ADMIN');
INSERT INTO user_roles (USER_ROLE_ID, USER_ID,AUTHORITY) VALUES (3, 2, 'ROLE_USER');
