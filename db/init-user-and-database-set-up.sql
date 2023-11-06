create database study;

create user 'studyuser'@'%' identified by '1234';

GRANT ALL PRIVILEGES ON study.* TO 'studyuser'@'%';

FLUSH PRIVILEGES;