CREATE TABLE OBJECT (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(512) NOT NULL
);

CREATE TABLE list (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(512) NOT NULL
);

// ATTRIBUTES

CREATE TABLE attobj (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value int(11) NOT NULL,
  FOREIGN KEY(parent) REFERENCES object(id),
  FOREIGN KEY(value) REFERENCES object(id)
);

CREATE TABLE attstr (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value VARCHAR,
  CONSTRAINT attstr_parent FOREIGN KEY(parent) REFERENCES object(id) ON DELETE RESTRICT
);

CREATE TABLE attslug (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value VARCHAR(2048),
  UNIQUE(value),
  FOREIGN KEY(parent) REFERENCES object(id)
);

CREATE INDEX attslug_value ON attslug(value);

CREATE TABLE attdate (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value datetime,
  FOREIGN KEY(parent) REFERENCES object(id)
);

CREATE TABLE attint (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value VARCHAR(32),
  FOREIGN KEY(parent) REFERENCES object(id)
);

CREATE TABLE attblob (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value VARCHAR(64),
  FOREIGN KEY(parent) REFERENCES object(id)
);

CREATE TABLE attlist (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value int(11) NOT NULL,
  FOREIGN KEY(parent) REFERENCES object(id),
  FOREIGN KEY(value) REFERENCES list(id),
);


CREATE INDEX attobj_name ON attobj(name);
CREATE INDEX attstr_name ON attstr(name);
CREATE INDEX attslug_name ON attslug(name);
CREATE INDEX attdate_name ON attdate(name);
CREATE INDEX attint_name ON attint(name);
CREATE INDEX attblob_name ON attblob(name);
CREATE INDEX attlist_name ON attlist(name);

// LIST ELEMENTS

CREATE TABLE lsobj (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  index int(11) NOT NULL,
  value int(11) NOT NULL,
  FOREIGN KEY(parent) REFERENCES list(id),
  FOREIGN KEY(value) REFERENCES object(id)
);

CREATE TABLE lsstr (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  index int(11) NOT NULL,
  value VARCHAR,
  FOREIGN KEY(parent) REFERENCES list(id)

);

CREATE TABLE lsdate (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  parent int(11) NOT NULL,
  index int(11) NOT NULL,
  value datetime,
  FOREIGN KEY(parent) REFERENCES list(id)

);

CREATE INDEX lsobj_parent ON lsobj(parent);
CREATE INDEX lsstr_parent ON lsstr(parent);
CREATE INDEX lsdate_parent ON lsdate(parent);

CREATE INDEX lsobj_index ON lsobj(index);
CREATE INDEX lsstr_index ON lsstr(index);
CREATE INDEX lsdate_index ON lsdate(index);

