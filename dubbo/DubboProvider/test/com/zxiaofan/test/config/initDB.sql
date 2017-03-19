#建库字符编码为utf8的库studydb
CREATE DATABASE IF NOT EXISTS StudyDB DEFAULT CHARACTER SET utf8;
#建表user
CREATE TABLE
IF NOT EXISTS USER (
	id INT (10) NOT NULL auto_increment,
	userName VARCHAR (20) NOT NULL,
	age INT (4),
	addTime DATETIME,
	modifyTime TIMESTAMP,
	isDelete INT(4),
	PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;