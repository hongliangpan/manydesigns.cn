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


DROP TABLE IF EXISTS t_sys_user;
CREATE TABLE t_sys_user (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_code varchar(20) DEFAULT NULL COMMENT '员工编号',
  c_name VARCHAR(200) DEFAULT NULL COMMENT '账号',
  c_display_name VARCHAR(200) DEFAULT NULL COMMENT '姓名',
  c_password varchar(100) DEFAULT NULL COMMENT '密码',
  c_dept_id int(10) DEFAULT NULL COMMENT '部门',
  c_region_id int(10) COMMENT '区域',
  c_email varchar(100) DEFAULT NULL COMMENT '邮箱',
  c_token varchar(50) DEFAULT NULL COMMENT 'token',
  c_type varchar(20) DEFAULT NULL COMMENT '类型',
  c_sex varchar(6) DEFAULT NULL COMMENT '性别',
  c_mobile VARCHAR(200) DEFAULT NULL COMMENT '手机',
  c_phone VARCHAR(200) DEFAULT NULL COMMENT '电话',  
  c_fax varchar(100) DEFAULT NULL COMMENT '传真',
  c_address varchar(200) DEFAULT NULL COMMENT '地址',
  c_desc varchar(500) DEFAULT NULL COMMENT '描述信息',
  c_login_count int(11) DEFAULT '0' COMMENT '用户累计登录次数',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag2 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag3 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag4 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (c_id)
  -- , CONSTRAINT fk_su_region_id FOREIGN KEY (c_region_id) REFERENCES t_dict_region(c_id)
) ENGINE=InnoDB  COMMENT='用户';
ALTER TABLE t_sys_user ADD UNIQUE INDEX uq_user_name (c_name) ;
ALTER TABLE t_sys_user ADD UNIQUE INDEX uq_user_email (c_email) ;


DROP TABLE IF EXISTS t_sys_group;
CREATE TABLE t_sys_group (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(100) DEFAULT NULL COMMENT '名称',
  c_display_name VARCHAR(200) DEFAULT NULL COMMENT '名称',
  c_region_level int(10) DEFAULT NULL COMMENT '区域级别',
  c_desc varchar(500) DEFAULT NULL COMMENT '备注',
  c_sort_id int(11) DEFAULT NULL COMMENT '显示顺序',
  c_role varchar(500) DEFAULT NULL COMMENT '权限角色',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag2 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag3 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  c_tag4 VARCHAR(200) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='工作组';

DROP TABLE IF EXISTS t_sys_group_user_rel;
CREATE TABLE t_sys_group_user_rel (
  c_group_id int(10) NOT NULL COMMENT '工作组ID',
  c_user_id int(10) NOT NULL COMMENT '用户ID',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_group_id,c_user_id),
  CONSTRAINT fk_sgu_group_id FOREIGN KEY (c_group_id) REFERENCES t_sys_group (c_id) ON DELETE CASCADE,
  CONSTRAINT fk_sgu_user_id FOREIGN KEY (c_user_id) REFERENCES t_sys_user (c_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='用户和分组关系表';

DROP TABLE IF EXISTS t_sys_group_region_rel;
CREATE TABLE t_sys_group_region_rel (
  c_group_id int(10) NOT NULL COMMENT '工作组ID',
  c_region_id int(10) NOT NULL COMMENT '区域',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_group_id,c_region_id),
  CONSTRAINT fk_sre_group_id FOREIGN KEY (c_group_id) REFERENCES t_sys_group (c_id),
  CONSTRAINT fk_sre_region_id FOREIGN KEY (c_region_id) REFERENCES t_dict_region (c_id)
) ENGINE=InnoDB COMMENT='区域和分组关系表';

DROP TABLE IF EXISTS t_sys_dept;
CREATE TABLE t_sys_dept (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_code varchar(50) DEFAULT NULL COMMENT '编号',
  c_name varchar(100) DEFAULT NULL COMMENT '名称',
  c_parent_id int(10) DEFAULT NULL COMMENT '上级部门',
  c_desc varchar(500) DEFAULT NULL COMMENT '备注',
  c_path varchar(255) DEFAULT NULL COMMENT '树级路径',
  c_level int(11) DEFAULT NULL COMMENT '树层级',
  c_sort_id int(11) DEFAULT NULL COMMENT '顺序',
  c_config varchar(500) DEFAULT NULL COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 varchar(50) DEFAULT NULL COMMENT '备用字段',
  c_tag2 varchar(50) DEFAULT NULL COMMENT '备用字段',
  c_tag3 varchar(50) DEFAULT NULL COMMENT '备用字段',
  c_tag4 varchar(50) DEFAULT NULL COMMENT '备用字段',
  c_full_path_name varchar(500) DEFAULT NULL COMMENT '全路径名称',
  PRIMARY KEY (c_id)
  -- , CONSTRAINT fk_dept_parent FOREIGN KEY (c_parent_id) REFERENCES t_sys_dept (c_id)
) ENGINE=InnoDB COMMENT='部门';

insert  into `t_sys_dept`(`c_id`,`c_code`,`c_name`,`c_parent_id`,`c_desc`,`c_path`,`c_level`,`c_sort_id`,`c_config`,`c_create_user`,`c_create_time`,`c_modify_user`,`c_modify_time`,`c_tag1`,`c_tag2`,`c_tag3`,`c_tag4`,`c_full_path_name`) values (1,'1','ITS',NULL,'','1',1,NULL,NULL,NULL,NULL,NULL,'2014-01-16 21:46:27',NULL,NULL,NULL,NULL,'ITS'),(2,'2','交付部',1,'','1.2',2,2,NULL,NULL,NULL,NULL,'2014-01-16 21:46:27',NULL,NULL,NULL,NULL,'ITS/交付部'),(3,'3','实施',2,'','1.2.3',3,3,NULL,NULL,NULL,NULL,'2014-01-16 21:46:27',NULL,NULL,NULL,NULL,'ITS/交付部/实施'),(4,'4','项目开发部',1,'','1.4',2,4,NULL,NULL,NULL,NULL,'2014-01-16 21:46:27',NULL,NULL,NULL,NULL,'ITS/项目开发部'),(5,'5','pmo',2,'','1.2.5',3,5,NULL,NULL,NULL,NULL,'2014-01-16 21:46:27',NULL,NULL,NULL,NULL,'ITS/交付部/pmo');
insert  into `t_sys_group`(`c_id`,`c_name`,`c_display_name`,`c_region_level`,`c_desc`,`c_sort_id`,`c_role`,`c_config`,`c_create_user`,`c_create_time`,`c_modify_user`,`c_modify_time`,`c_tag1`,`c_tag2`,`c_tag3`,`c_tag4`) values (0,'super','超级用户',0,'开发权限',NULL,'DEVELOP',NULL,NULL,NULL,NULL,'2014-01-15 18:08:35',NULL,NULL,NULL,NULL),(1,'管理员','所有权限',NULL,'管理员，具有所有编辑权限',1,'edit.field.all',NULL,'super','2014-01-16 21:47:13','super','2014-01-16 21:47:13',NULL,NULL,NULL,NULL),(2,'可以调整','可以调整，不能删除',NULL,'可以调整，不能删除基础信息，不能修改系统字段',2,'edit.field.part',NULL,'super','2014-01-16 21:47:22','super','2014-01-16 21:47:22',NULL,NULL,NULL,NULL),(3,'只能看','只能看',NULL,'只能查看，不能修改任何信息',3,'edit.field.view',NULL,'super','2014-01-16 21:47:31','super','2014-01-16 21:47:31',NULL,NULL,NULL,NULL);
insert  into `t_sys_group_user_rel`(`c_group_id`,`c_user_id`,`c_config`,`c_create_user`,`c_create_time`,`c_modify_user`,`c_modify_time`) values (0,0,NULL,NULL,NULL,NULL,'2014-01-15 15:41:35'),(0,2,NULL,NULL,NULL,NULL,'2014-01-15 15:41:35'),(1,0,NULL,NULL,NULL,NULL,'2014-01-15 18:06:34'),(1,1,NULL,NULL,NULL,NULL,'2014-01-15 18:06:29'),(2,2,NULL,NULL,NULL,NULL,'2014-01-15 18:11:42'),(3,3,NULL,NULL,NULL,NULL,'2014-01-15 18:11:56');
insert  into `t_sys_user`(`c_id`,`c_code`,`c_name`,`c_display_name`,`c_password`,`c_dept_id`,`c_region_id`,`c_email`,`c_token`,`c_type`,`c_sex`,`c_mobile`,`c_phone`,`c_fax`,`c_address`,`c_desc`,`c_login_count`,`c_create_user`,`c_create_time`,`c_modify_user`,`c_modify_time`,`c_tag1`,`c_tag2`,`c_tag3`,`c_tag4`) values (0,NULL,'super',NULL,'GzIxZVzrt6H3g+3fJ9JUyg==',NULL,NULL,'ruijie_its@163.com','qx0n3melstbzyiazqbi2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2014-01-15 15:41:35',NULL,NULL,NULL,NULL),(1,NULL,'admin','管理员','ISMvKXpXpadDiUoOSoAfww==',NULL,NULL,'hongliangpan@gmail.com','6667acd1-0db6-4192-8b2d-76f91f2b94c5','','','','','','','',NULL,'super','2014-01-16 21:46:56','super','2014-01-16 21:46:56',NULL,NULL,NULL,NULL),(2,'','可以调整','可以调整',NULL,1,1,'riilriil@163.com','','','','','','','','',NULL,NULL,NULL,NULL,'2014-01-15 18:11:25',NULL,NULL,NULL,NULL),(3,'','查看','查看',NULL,1,1,'test28797575@163.com','','','','','','','','',NULL,NULL,NULL,NULL,'2014-01-15 18:11:23',NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


UNLOCK TABLES;