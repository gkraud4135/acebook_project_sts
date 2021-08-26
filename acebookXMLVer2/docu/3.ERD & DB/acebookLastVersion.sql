-- MySQL Script generated by MySQL Workbench
-- Mon Jul 12 10:41:18 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------
drop database if EXISTS `project` ;
CREATE SCHEMA IF NOT EXISTS `project`;
USE `project` ;
-- -----------------------------------------------------
-- Table `project`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`member` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `age` VARCHAR(45) NOT NULL,
  `gender` ENUM('남자', '여자', '기타') NOT NULL,
  `friendcount` INT NOT NULL DEFAULT 0,
  `rdate` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  `state` ENUM('승인중', '회원', '탈퇴') NOT NULL DEFAULT '승인중',
  `profile` INT NOT NULL DEFAULT 0,
  `backprofile` INT NOT NULL DEFAULT 0,
  `code` VARCHAR(45) NULL,
  PRIMARY KEY (`sn`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`friend` (
  `my_sn` INT NOT NULL,
  `friend_sn` INT NOT NULL,
  `state` ENUM('신청중', '친구', '차단') NULL,
  PRIMARY KEY (`my_sn`, `friend_sn`),
  INDEX `fk_friend_member1_idx` (`my_sn` ASC) ,
  INDEX `fk_friend_member2_idx` (`friend_sn` ASC) ,
  CONSTRAINT `fk_friend_member1`
    FOREIGN KEY (`my_sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_friend_member2`
    FOREIGN KEY (`friend_sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`state` (
  `sn` INT NOT NULL,
  `state` ENUM('접속중', '로그오프', '기타용무중') NULL,
  `logintime` DATE NULL,
  `logouttime` DATE NULL,
  `viewtime` TIMESTAMP NULL,
  `lastviewtime` TIMESTAMP NULL,
  PRIMARY KEY (`sn`),
  CONSTRAINT `fk_state_member1`
    FOREIGN KEY (`sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`board` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `writer` INT NOT NULL,
  `contents` VARCHAR(800) NOT NULL,
  `wdate` TIMESTAMP NULL DEFAULT current_timestamp,
  `state` ENUM('전체', '친구', '프로필', '광고', '차단', '공유', '백프로필') NOT NULL DEFAULT '프로필',
  PRIMARY KEY (`sn`),
  INDEX `fk_board_member1_idx` (`writer` ASC) ,
  CONSTRAINT `fk_board_member1`
    FOREIGN KEY (`writer`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`comment` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `board_sn` INT NOT NULL,
  `writer` INT NOT NULL,
  `contents` VARCHAR(45) NOT NULL,
  `wdate` TIMESTAMP NULL DEFAULT current_timestamp,
  INDEX `fk_comment_board1_idx` (`board_sn` ASC) ,
  PRIMARY KEY (`sn`),
  INDEX `fk_comment_member1_idx` (`writer` ASC) ,
  CONSTRAINT `fk_comment_board1`
    FOREIGN KEY (`board_sn`)
    REFERENCES `project`.`board` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_member1`
    FOREIGN KEY (`writer`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`attach`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`attach` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `board_sn` INT NOT NULL,
  `filename` VARCHAR(45) NULL,
  `filesize` INT NULL,
  INDEX `fk_attach_board1_idx` (`board_sn` ASC) ,
  PRIMARY KEY (`sn`),
  CONSTRAINT `fk_attach_board1`
    FOREIGN KEY (`board_sn`)
    REFERENCES `project`.`board` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`hashtag` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `board_sn` INT NOT NULL,
  `hashName` VARCHAR(45) NULL,
  INDEX `fk_table1_board1_idx` (`board_sn` ASC) ,
  PRIMARY KEY (`sn`),
  CONSTRAINT `fk_table1_board1`
    FOREIGN KEY (`board_sn`)
    REFERENCES `project`.`board` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`commentLike`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`commentLike` (
  `comment_sn` INT NOT NULL,
  `writer` INT NOT NULL,
  `like` INT NULL,
  `report` INT NULL,
  INDEX `fk_commentLike_comment1_idx` (`comment_sn` ASC) ,
  PRIMARY KEY (`comment_sn`, `writer`),
  INDEX `fk_commentLike_member1_idx` (`writer` ASC) ,
  CONSTRAINT `fk_commentLike_comment1`
    FOREIGN KEY (`comment_sn`)
    REFERENCES `project`.`comment` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_commentLike_member1`
    FOREIGN KEY (`writer`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`memberpost`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`memberpost` (
  `sn` INT NOT NULL,
  `post` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `addresstail` VARCHAR(45) NULL,
  `birthplace` ENUM('서울', '인천', '대전', '대구', '광주', '울산', '부산', '제주도', '울릉도', '독도', '경기도', '강원도', '전라북도', '전라남도', '경상북도', '경상남도', '충청북도', '충청남도') NULL,
  PRIMARY KEY (`sn`),
  CONSTRAINT `fk_memberpost_member1`
    FOREIGN KEY (`sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`memberschool`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`memberschool` (
  `sn` INT NOT NULL,
  `row` VARCHAR(45) NULL,
  `middle` VARCHAR(45) NULL,
  `high` VARCHAR(45) NULL,
  `university` VARCHAR(45) NULL,
  PRIMARY KEY (`sn`),
  CONSTRAINT `fk_memberschool_member1`
    FOREIGN KEY (`sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`chatroom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`chatroom` (
  `id` VARCHAR(45) NOT NULL,
  `roomname` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`chat` (
  `chatroom_id` VARCHAR(45) NOT NULL,
  `member_sn` INT NOT NULL,
  INDEX `fk_chat_member1_idx` (`member_sn` ASC),
  INDEX `fk_chat_chatroom1_idx` (`chatroom_id` ASC),
  PRIMARY KEY (`chatroom_id`, `member_sn`),
  CONSTRAINT `fk_chat_member1`
    FOREIGN KEY (`member_sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chat_chatroom1`
    FOREIGN KEY (`chatroom_id`)
    REFERENCES `project`.`chatroom` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`chatting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`chatting` (
  `sn` INT NOT NULL AUTO_INCREMENT,
  `chatroom_id` VARCHAR(45) NOT NULL,
  `writer` INT NOT NULL,
  `contents` VARCHAR(100) NULL,
  `rdate` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (`sn`, `chatroom_id`),
  INDEX `fk_chatting_chat1_idx` (`chatroom_id` ASC, `writer` ASC),
  CONSTRAINT `fk_chatting_chat1`
    FOREIGN KEY (`chatroom_id` , `writer`)
    REFERENCES `project`.`chat` (`chatroom_id` , `member_sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`boardlike`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`boardlike` (
  `board_sn` INT NOT NULL,
  `writer` INT NOT NULL,
  `like` INT NULL,
  `report` INT NULL,
  INDEX `fk_boardlike_board1_idx` (`board_sn` ASC) ,
  INDEX `fk_boardlike_member1_idx` (`writer` ASC) ,
  PRIMARY KEY (`writer`, `board_sn`),
  CONSTRAINT `fk_boardlike_board1`
    FOREIGN KEY (`board_sn`)
    REFERENCES `project`.`board` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_boardlike_member1`
    FOREIGN KEY (`writer`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project`.`sharing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project`.`sharing` (
  `board_sn` INT NOT NULL,
  `getboard` INT NULL,
  INDEX `fk_sharing_board1_idx` (`board_sn` ASC) ,
  CONSTRAINT `fk_sharing_board1`
    FOREIGN KEY (`board_sn`)
    REFERENCES `project`.`board` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `project`.`ip` (
  `sn` INT NOT NULL,
  `ip` VARCHAR(45) NULL,
  INDEX `fk_ip_member1_idx` (`sn` ASC),
  CONSTRAINT `fk_ip_member1`
    FOREIGN KEY (`sn`)
    REFERENCES `project`.`member` (`sn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




insert into member(id,password,name,age,gender,state) values ('sung','1234','성상현','1992-07-06','남자','회원');
insert into member(id,password,name,age,gender,state) values ('woong','1234','이영웅','1996-11-24','남자','회원');
insert into member(id,password,name,age,gender,state) values ('hak','1234','이학명','1998-04-29','남자','회원');
select year(now())-year(age)+1 as age from member where sn=1;