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


DROP TABLE IF EXISTS t_dict_common;
CREATE TABLE t_dict_common (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_type varchar(40) NOT NULL  COMMENT '类型',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_value varchar(500) NOT NULL  COMMENT '值',
  c_desc varchar(40) NOT NULL  COMMENT '描述',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='通用字典';

DROP TABLE IF EXISTS t_sys_properties;
CREATE TABLE t_sys_properties(
  c_id varchar(40) NOT NULL COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_value varchar(500) NOT NULL  COMMENT '值',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='系统配置属性';

/*
DROP TABLE IF EXISTS t_dict_type;

CREATE TABLE t_dict_type (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='字典类型';

DROP TABLE IF EXISTS t_dict_willingness;
CREATE TABLE t_dict_willingness (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='购买意愿';
*/

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

