/*
SQLyog Ultimate v9.01 
MySQL - 5.6.14 : Database - itsboard
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS t_blog_subject;
CREATE TABLE t_blog_subject (
 c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
 c_title VARCHAR(200) COMMENT '标题',
 c_forum_id int(10) COMMENT '子系统',
 c_module_id int(10) COMMENT '模块',
 c_type_id int(10) COMMENT '类型',
 c_level_id int(10) COMMENT '等级',
 c_summary varchar(100) COMMENT '摘要',
 c_body varchar(4000) COMMENT '内容',
 c_author varchar(100) COMMENT '用户',
 c_processor varchar(100) COMMENT '处理人',
 c_expect_time DATETIME COMMENT '期望完成时间',
 c_finish_time DATETIME COMMENT '实际完成时间',
 c_attachment1 varchar(200) COMMENT '附件1',
 c_attachment2 varchar(200) COMMENT '附件2',
 c_attachment3 varchar(200) COMMENT '附件3',
 c_attachment4 varchar(200) COMMENT '附件4',
 c_attachment5 varchar(200) COMMENT '附件5',
 c_attachment6 varchar(200) COMMENT '附件6',
 c_state_id int(10) COMMENT '状态',
 c_create_user varchar(20) COMMENT '创建用户',
 c_create_time DATETIME COMMENT '创建时间',
 c_modify_user varchar(20) COMMENT '修改用户',
 c_modify_time TIMESTAMP COMMENT '修改时间',
 c_tag1 VARCHAR(200) COMMENT '备用字段',
 c_tag2 VARCHAR(200) COMMENT '备用字段',
 c_tag3 VARCHAR(200) COMMENT '备用字段',
 c_tag4 VARCHAR(200) COMMENT '备用字段',
 PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='主题';


DROP TABLE IF EXISTS t_blog_comment;
CREATE TABLE t_blog_comment (
 c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
 c_subject_id int(10) COMMENT '主题',
 c_body varchar(4000) COMMENT '评论内容',
 c_author varchar(100) COMMENT '用户',
 c_state_id int(10) COMMENT '状态',
 c_attachment1 varchar(200) COMMENT '附件1',
 c_attachment2 varchar(200) COMMENT '附件2',
 c_attachment3 varchar(200) COMMENT '附件3',
 c_attachment4 varchar(200) COMMENT '附件4',
 c_attachment5 varchar(200) COMMENT '附件5',
 c_attachment6 varchar(200) COMMENT '附件6', 
 c_config varchar(500) COMMENT '扩展信息',
 c_create_user varchar(20) COMMENT '创建用户',
 c_create_time DATETIME COMMENT '创建时间',
 c_modify_user varchar(20) COMMENT '修改用户',
 c_modify_time TIMESTAMP COMMENT '修改时间',
 c_tag1 VARCHAR(200) COMMENT '备用字段',
 c_tag2 VARCHAR(200) COMMENT '备用字段',
 c_tag3 VARCHAR(200) COMMENT '备用字段',
 c_tag4 VARCHAR(200) COMMENT '备用字段',
 PRIMARY KEY (c_id)
 , CONSTRAINT fk_blog_subject FOREIGN KEY (c_subject_id) REFERENCES t_blog_subject(c_id)
) ENGINE=InnoDB COMMENT='评论';


DROP TABLE IF EXISTS t_blogd_type;
CREATE TABLE t_blogd_type (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='缺陷类型';

DROP TABLE IF EXISTS t_blogd_level;
CREATE TABLE t_blogd_level (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='缺陷等级';

DROP TABLE IF EXISTS t_blogd_state;
CREATE TABLE t_blogd_state (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='缺陷状态';

DROP TABLE IF EXISTS t_blogd_forum;
CREATE TABLE t_blogd_forum (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='系统';

DROP TABLE IF EXISTS t_blogd_module;
CREATE TABLE t_blogd_module (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_forum_id int(10) NOT NULL COMMENT '系统',  
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
  , CONSTRAINT fk_blog_forum FOREIGN KEY (c_forum_id) REFERENCES t_blogd_forum(c_id)
) ENGINE=InnoDB COMMENT='模块';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


UNLOCK TABLES;