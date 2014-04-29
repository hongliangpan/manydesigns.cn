/*
项目日报子系统
MySQL - 5.6.14 : Database - itsboard
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS t_pm_day_off;
CREATE TABLE t_pm_day_off (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_date DATE COMMENT '日期',
  c_is_day_off tinyint(4)  DEFAULT '0' COMMENT '是否休息日',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='休息日';

DROP TABLE IF EXISTS t_pm_task_type;
CREATE TABLE t_pm_task_type (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(200) NOT NULL COMMENT '项目全称',
  c_desc varchar(4000)  COMMENT '描述',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='任务类型';

DROP TABLE IF EXISTS t_pm_project;
CREATE TABLE t_pm_project (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_code varchar(36) NOT NULL COMMENT '编码',
  c_customer_id int(10) COMMENT '客户ID',
  c_name varchar(200) NOT NULL COMMENT '项目全称',
  c_short_name varchar(200) DEFAULT NULL COMMENT '项目简称',
  c_pm_dev varchar(60) DEFAULT NULL COMMENT '项目经理', 
  c_pm_impl varchar(60) DEFAULT NULL COMMENT '实施经理', 
  c_pm_amoeba varchar(60) DEFAULT NULL COMMENT '巴长Amoeba', 
  c_order_days int(10) DEFAULT NULL COMMENT '下单人天',
  c_category varchar(10) NOT NULL COMMENT '项目类型',
  c_dev_state int(10) DEFAULT NULL COMMENT '开发状态', 
  c_state int(10) DEFAULT NULL COMMENT '项目状态', 
  c_order_time date DEFAULT NULL COMMENT '订单时间',

  c_money float(11,2) DEFAULT '0.00' COMMENT '项目金额(万元)', 
  c_begin_date date DEFAULT NULL COMMENT '开工时间',
  c_end_date date DEFAULT NULL COMMENT '竣工时间',

  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id),
  KEY (c_state)
) ENGINE=InnoDB  COMMENT='开发项目信息';


DROP TABLE IF EXISTS t_pm_daily;
CREATE TABLE t_pm_daily (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_user varchar(40) NOT NULL COMMENT '用户',
  c_title varchar(100) NOT NULL COMMENT '任务项',
  c_content varchar(100)  COMMENT '任务明细',
  c_date DATE NOT NULL COMMENT '日期',
  c_type1 varchar(40) NOT NULL COMMENT '任务类型',
  c_hours float(3,1) NOT NULL  COMMENT '工时',
  c_project_id int(11) NOT NULL COMMENT '项目',
  c_module_id int(11) DEFAULT NULL COMMENT '项目模块',
  c_issue varchar(4000) DEFAULT NULL COMMENT '问题建议',  
  c_best_practice varchar(4000) DEFAULT NULL COMMENT '最佳实践', 
  c_type2 varchar(80) DEFAULT NULL COMMENT '任务类型2',  
  c_attachment1 varchar(200)  COMMENT '附件1',
  c_attachment2 varchar(200)  COMMENT '附件2',
  c_attachment3 varchar(200)  COMMENT '附件3',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='日报';

DROP TABLE IF EXISTS t_pm_module;
CREATE TABLE t_pm_module (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_project_id int(10) DEFAULT 0 COMMENT '项目ID',
  c_name varchar(100) NOT NULL COMMENT '模块',
  c_sort_id int(10) DEFAULT NULL  COMMENT '排序号',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='RIIL模块';

DROP TABLE IF EXISTS t_pm_issue;
CREATE TABLE t_pm_issue (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_user varchar(40) NOT NULL COMMENT '用户',
  c_date DATE NOT NULL COMMENT '日期',
  c_issue varchar(4000) DEFAULT NULL COMMENT '问题建议',
  c_project_id int(11) DEFAULT NULL COMMENT '项目',
  c_module_id int(11) DEFAULT NULL COMMENT '项目模块',
  c_level tinyint(4)  DEFAULT '0' COMMENT '等级',   
  c_resolve_user varchar(4000) DEFAULT NULL COMMENT '问题跟踪人/责任人',
  c_is_resolve tinyint(4)  DEFAULT '0' COMMENT '是否解决', 
  c_resolve varchar(4000) DEFAULT NULL COMMENT '解决方案',  
  c_resolve_date DATE NOT NULL COMMENT '解决日期',
  c_source varchar(20) DEFAULT NULL COMMENT '问题来源',  
  c_source_id varchar(4000) DEFAULT NULL COMMENT '来源标识',
  c_attachment1 varchar(200)  COMMENT '附件1',
  c_attachment2 varchar(200)  COMMENT '附件2',
  c_attachment3 varchar(200)  COMMENT '附件3',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='问题建议';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

