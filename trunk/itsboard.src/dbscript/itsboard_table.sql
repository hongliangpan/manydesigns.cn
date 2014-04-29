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

DROP TABLE IF EXISTS t_dict_complaint;
CREATE TABLE t_dict_complaint (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='投诉原因';

DROP TABLE IF EXISTS t_dict_industry;
CREATE TABLE t_dict_industry (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_type1 varchar(40) NOT NULL COMMENT '行业',
  c_type2 varchar(80) COMMENT '细分市场',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  PRIMARY KEY (c_id),
  KEY k_type1 (c_type1),
  KEY k_type2 (c_type2)
) ENGINE=InnoDB  COMMENT='行业及细分市场';

DROP TABLE IF EXISTS t_dict_region;
CREATE TABLE t_dict_region (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_region1 varchar(40) NOT NULL COMMENT '片区',
  c_region2 varchar(40) NOT NULL COMMENT '大区',
  c_region3 varchar(40) NOT NULL COMMENT '省区',
  c_sort_id int(10)  COMMENT '排序号',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  PRIMARY KEY (c_id),
  KEY k_region1 (c_region1),
  KEY k_region2 (c_region2),
  KEY k_region3 (c_region3)
) ENGINE=InnoDB AUTO_INCREMENT=53  COMMENT='区域';

DROP TABLE IF EXISTS t_dict_state;
CREATE TABLE t_dict_state (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(40) NOT NULL  COMMENT '名称',
  c_sort_id int(10)  COMMENT '排序号',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id),
  KEY (c_name)
) ENGINE=InnoDB  COMMENT='项目状态';

DROP TABLE IF EXISTS t_dict_version;
CREATE TABLE t_dict_version (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_product varchar(20) NOT NULL DEFAULT ''COMMENT '产品',
  c_version varchar(40) NOT NULL COMMENT '版本',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  PRIMARY KEY (c_id),
  KEY (c_version)
) ENGINE=InnoDB  COMMENT='产品版本';

DROP TABLE IF EXISTS t_dict_module;
CREATE TABLE t_dict_module (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_name varchar(100) NOT NULL COMMENT '模块',
  c_sort_id int(10)  COMMENT '排序号',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='RIIL模块';

DROP TABLE IF EXISTS t_customer;
CREATE TABLE t_customer (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  c_code varchar(36) COMMENT '编码',
  c_name varchar(200) NOT NULL COMMENT '客户名称',
  c_short_name varchar(200) COMMENT '客户简称',
  c_region1 varchar(40) NOT NULL COMMENT '片区',
  c_region2 varchar(40) NOT NULL COMMENT '大区',
  c_region3 varchar(40) NOT NULL COMMENT '省区',
  c_industry1 varchar(40) NOT NULL COMMENT '行业',
  c_industry2 varchar(80) COMMENT '细分市场',
  c_pm varchar(60) COMMENT '客户负责人', 
  c_pmb varchar(60) COMMENT '乙方负责人', 
  c_money float(11,2) DEFAULT '0.00' COMMENT '总合同额(万元)', 
  c_is_abnormal tinyint(4)  DEFAULT '0' COMMENT '是否异常客户',
  c_memo text COMMENT '备注',
  
  c_state int(10) COMMENT '客户状态',
  c_warranty varchar(20) COMMENT '维保期',
  c_warranty_end date COMMENT '维保结束期',
  c_is_expired tinyint(4)  COMMENT '是否过保',

  c_client_aspiration varchar(40) COMMENT '客户意愿',
  c_is_template tinyint(4)  COMMENT '是否样板客户',
  c_is_template_boutique tinyint(4)  COMMENT '是否精品样板客户',
  c_memo2 text COMMENT '备注',
  c_is_difficult tinyint(4)  DEFAULT '0' COMMENT '是否难点客户',
  c_is_good tinyint(4)  DEFAULT '0' COMMENT '是否用的好',
  c_is_template_conditions tinyint(4)  COMMENT '是否具备样板条件',
  c_version1 varchar(40) COMMENT 'RIIL产品版本',
  c_version2 varchar(40) COMMENT 'BMC产品版本',
  c_is_develop tinyint(4)  DEFAULT '0' COMMENT '是否二次开发',
  c_develop_info varchar(4000) DEFAULT '0' COMMENT '二次开发内容',
  
  c_itsm_level varchar(10) COMMENT '客户运维水平',
  c_itsm_number varchar(10) COMMENT '运维人员数量',
  c_itsm_mode varchar(10) COMMENT '客户日常运维模式',
  c_itsm_software varchar(4000) COMMENT '客户常用其它软件功能说明',
  c_itsm_attachment varchar(100) COMMENT '附件',
  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) COMMENT '扩展信息',
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
  /*,
  CONSTRAINT fk_customer_region1 FOREIGN KEY (c_region1) REFERENCES t_dict_region(c_region1) ,
  CONSTRAINT fk_customer_region2 FOREIGN KEY (c_region2) REFERENCES t_dict_region(c_region2) ,
  CONSTRAINT fk_customer_region3 FOREIGN KEY (c_region3) REFERENCES t_dict_region(c_region3) ,
  CONSTRAINT fk_customer_industry1 FOREIGN KEY (c_industry1) REFERENCES t_dict_industry(c_type1) ,
  CONSTRAINT fk_customer_industry2  FOREIGN KEY (c_industry2) REFERENCES t_dict_industry(c_type2) ,
  CONSTRAINT fk_customer_version1 FOREIGN KEY (c_version1) REFERENCES t_dict_version(c_version) ,
  CONSTRAINT fk_customer_version2 FOREIGN KEY (c_version2) REFERENCES t_dict_version(c_version) ,
  CONSTRAINT fk_customer_state FOREIGN KEY (c_state) REFERENCES t_dict_state(c_name) 
  */
) ENGINE=InnoDB  COMMENT='客户信息';
ALTER TABLE `t_customer` ADD UNIQUE INDEX `idx_customer_name` (`c_name`) ;

DROP TABLE IF EXISTS t_customer_pm;
CREATE TABLE t_customer_pm (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  c_name varchar(20) NOT NULL COMMENT '姓名',
  c_title varchar(20) COMMENT '职称',
  c_dept varchar(100) COMMENT '公司及部门',
  c_phone1 varchar(500) NOT NULL COMMENT '移动电话',
  c_phone2 varchar(500) COMMENT '固定电话',
  c_email varchar(500) COMMENT 'email',
  c_address varchar(4000) COMMENT '住址',
  c_birthday date  COMMENT '生日',
  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_cpm_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='客户联系人';

DROP TABLE IF EXISTS t_customer_warranty;
CREATE TABLE t_customer_warranty (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  c_project_id int(10) COMMENT '项目ID',
  c_warranty_begin date COMMENT '维保开始时间',
  c_warranty_end date COMMENT '维保结束时间',
  c_warranty varchar(20) COMMENT '维保期',
  c_is_expired tinyint(4) COMMENT '是否过保',
  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_cw_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='客户维保信息';

DROP TABLE IF EXISTS t_customer_complaint;
CREATE TABLE t_customer_complaint (
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  c_project_id int(10) COMMENT '项目ID',
  c_complaint_id int(10) NOT NULL COMMENT '投诉原因id',
  c_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投诉时间',
  c_complaint_info varchar(4000) NOT NULL COMMENT '投诉内容',
  c_is_resolve tinyint(4)  DEFAULT '0' COMMENT '是否解决',
  c_current_resolve varchar(4000) COMMENT '投诉当前解决情况',
  c_config varchar(500) COMMENT '扩展信息',
  
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time timestamp COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_customer_id,c_project_id,c_complaint_id)
  , CONSTRAINT fk_cc_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE
  -- ,CONSTRAINT fk_cc_complaint_id FOREIGN KEY (c_complaint_id) REFERENCES t_dict_complaint(c_id)
) ENGINE=InnoDB  COMMENT='客户投诉';

DROP TABLE IF EXISTS t_project;
CREATE TABLE t_project (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_code varchar(36) NOT NULL COMMENT '编码',
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  c_name varchar(200) NOT NULL COMMENT '项目全称',
  c_short_name varchar(200) COMMENT '项目简称',
  c_order_time date COMMENT '订单时间',
  c_money float(11,2) DEFAULT '0.00' COMMENT '项目金额(万元)', 
  c_begin_date date COMMENT '开工时间',
  c_end_date date COMMENT '竣工时间',
  c_category varchar(10) NOT NULL COMMENT '项目类型',
  c_pm varchar(60) COMMENT '项目负责人', 
  c_state int(10) COMMENT '项目状态',
  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id),
  KEY (c_state),
  CONSTRAINT fk_project_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE
  -- ,  CONSTRAINT fk_project_state FOREIGN KEY (c_state) REFERENCES t_dict_state(c_name)
) ENGINE=InnoDB  COMMENT='项目信息';
ALTER TABLE t_project ADD UNIQUE INDEX idx_project_name (c_name) ;
ALTER TABLE t_project ADD UNIQUE INDEX idx_project_code (c_code) ;

DROP TABLE IF EXISTS t_project_order;
CREATE TABLE t_project_order (
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  c_project_id int(10) NOT NULL DEFAULT '0' COMMENT '项目ID',
  c_service_name varchar(200) NOT NULL COMMENT '服务产品名称',
  c_quantity int(10) NOT NULL  COMMENT '数量',
  c_attachment1 varchar(200)  COMMENT '附件1',
  c_attachment2 varchar(200)  COMMENT '附件2',
  c_attachment3 varchar(200)  COMMENT '附件3',
  c_info varchar(4000)  COMMENT '其它信息',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id),
  CONSTRAINT fk_po_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE
--  ,CONSTRAINT fk_po_project_id FOREIGN KEY (c_project_id) REFERENCES t_project(c_id)
) ENGINE=InnoDB  COMMENT='项目订单';

DROP TABLE IF EXISTS t_project_module;
CREATE TABLE t_project_module(
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
  --  c_project_id int(10) COMMENT '项目ID',
  c_module_id int(10) NOT NULL COMMENT '模块ID',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_customer_id,c_module_id),
  KEY module (c_module_id),
  CONSTRAINT fk_pm_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE,
  -- CONSTRAINT fk_pm_project_id FOREIGN KEY (c_project_id) REFERENCES t_project (c_id) ON DELETE CASCADE,
  CONSTRAINT fk_pm_module_id FOREIGN KEY (c_module_id) REFERENCES t_dict_module (c_id) ON DELETE CASCADE 
) ENGINE=InnoDB  COMMENT='项目模块';

DROP TABLE IF EXISTS t_project_module_hot;
CREATE TABLE t_project_module_hot (
  c_customer_id int(10) NOT NULL COMMENT '客户ID',
--  c_project_id int(10) COMMENT '项目ID',
  c_module_id int(10) NOT NULL COMMENT '模块ID',
  c_config varchar(500) COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_customer_id,c_module_id),
  KEY module (c_module_id),
  CONSTRAINT fk_pmh_customer_id FOREIGN KEY (c_customer_id) REFERENCES t_customer(c_id) ON DELETE CASCADE,
  -- CONSTRAINT fk_pmh_project_id FOREIGN KEY (c_project_id) REFERENCES t_project (c_id) ,
  CONSTRAINT fk_pmh_module_id FOREIGN KEY (c_module_id) REFERENCES t_dict_module (c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='项目热点模块';

DELIMITER $$
DROP PROCEDURE IF EXISTS proc_update_customer_money
$$
CREATE PROCEDURE proc_update_customer_money()
BEGIN
	UPDATE t_customer c
	SET c.c_money = (
		SELECT
			SUM(p.c_money)
		FROM
			t_project p
		WHERE
			(c.c_id = p.c_customer_id)
		GROUP BY
			c_customer_id
	);
END
$$

DROP TRIGGER IF EXISTS tri_project_money_delete
$$
CREATE TRIGGER tri_project_money_delete
AFTER DELETE ON t_project
FOR EACH ROW
BEGIN
	CALL proc_update_customer_money();
END
$$

DROP TRIGGER IF EXISTS tri_project_money_insert
$$
CREATE TRIGGER tri_project_money_insert
AFTER INSERT ON t_project
FOR EACH ROW
BEGIN
	CALL proc_update_customer_money();
END
$$

DROP TRIGGER IF EXISTS tri_project_money_update
$$
CREATE TRIGGER tri_project_money_update
AFTER UPDATE ON t_project
FOR EACH ROW
BEGIN
   CALL proc_update_customer_money();
END
$$
DELIMITER ;

CREATE OR REPLACE VIEW v_project AS
SELECT
p.c_id,
p.c_code,
p.c_customer_id,
p.c_name,
p.c_short_name,
p.c_order_time,
p.c_money,
p.c_begin_date,
p.c_end_date,
p.c_category,
p.c_pm ,
p.c_state ,
c.c_name c_customer_name,
c.c_short_name c_customer_short_name,
c.c_region1,
c.c_region2,
c.c_region3,
c.c_industry1,
c.c_industry2,
c.c_pm c_customer_pm,
c.c_pmb,
c.c_money c_customer_money,
c.c_is_abnormal,
c.c_state c_customer_state,
c.c_warranty,
c.c_warranty_end,
c.c_is_expired,
c.c_client_aspiration,
c.c_is_template,
c.c_is_template_boutique,
c.c_is_difficult,
c.c_is_good,
c.c_is_template_conditions,
c.c_version1,
c.c_version2,
c.c_is_develop,
c.c_develop_info,
c.c_itsm_level,
c.c_itsm_number,
c.c_itsm_mode,
c.c_info
FROM t_project p
INNER JOIN t_customer c ON p.c_customer_id= c.c_id;


-- 更新维保期
DELIMITER $$
DROP FUNCTION IF EXISTS is_expired
$$
CREATE FUNCTION is_expired(value1 DATE)
RETURNS INT
BEGIN
	DECLARE result1 INT;
	SELECT IF(DATEDIFF(value1, NOW())<0,1,-1) INTO result1;
	RETURN result1;
END
$$
-- 更新维保表是否过期
DROP PROCEDURE IF EXISTS proc_update_warranty_is_expired
$$
CREATE PROCEDURE proc_update_warranty_is_expired()
BEGIN
	UPDATE t_customer_warranty SET c_is_expired =is_expired(c_warranty_end);
END
$$
-- 根据子表，更新客户表
DROP PROCEDURE IF EXISTS proc_update_customer_warranty
$$
CREATE PROCEDURE proc_update_customer_warranty()
BEGIN
	UPDATE t_customer cc,
	 (
		SELECT c_customer_id, MAX(w.c_warranty_end) c_warranty_end, 
		IF ( DATEDIFF( MAX(w.c_warranty_end), NOW()) < 0, 1 ,- 1 ) c_is_expired
		FROM t_customer_warranty w
		INNER JOIN t_customer c ON c.c_id = w.c_customer_id
		GROUP BY c_customer_id
	) t
	SET cc.c_warranty_end = t.c_warranty_end,
	 cc.c_is_expired = t.c_is_expired
	WHERE
		(cc.c_id = t.c_customer_id);
END
$$

DROP TRIGGER IF EXISTS tri_customer_warranty_delete
$$
CREATE TRIGGER tri_customer_warranty_delete
AFTER DELETE ON t_customer_warranty
FOR EACH ROW
BEGIN
	CALL proc_update_customer_warranty();
END
$$

DROP TRIGGER IF EXISTS tri_customer_warranty_insert
$$
CREATE TRIGGER tri_customer_warranty_insert
AFTER INSERT ON t_customer_warranty
FOR EACH ROW
BEGIN
	CALL proc_update_customer_warranty();
END
$$

DROP TRIGGER IF EXISTS tri_customer_warranty_update
$$
CREATE TRIGGER tri_customer_warranty_update
AFTER UPDATE ON t_customer_warranty
FOR EACH ROW
BEGIN
   CALL proc_update_customer_warranty();
END
$$

-- 定期更新是否过期信息 event
DROP PROCEDURE IF EXISTS job_update_warranty
$$
CREATE PROCEDURE job_update_warranty()
BEGIN
	CALL proc_update_warranty_is_expired();
	CALL proc_update_customer_warranty();
END
$$
DROP  event IF EXISTS event_update_warranty$$
CREATE event event_update_warranty
ON SCHEDULE EVERY 60 MINUTE
STARTS DATE_ADD( DATE_ADD( DATE_ADD(CURDATE(),INTERVAL HOUR(CURTIME()) HOUR), INTERVAL 1 HOUR), INTERVAL 8 MINUTE)  
DO CALL job_update_warranty()$$


DELIMITER ;

CREATE OR REPLACE VIEW v_project200 AS
SELECT
c.c_id c_c_id ,
c.c_code c_c_code ,
c.c_name c_c_name ,
c.c_short_name c_c_short_name ,
c.c_region1 c_c_region1 ,
c.c_region2 c_c_region2 ,
c.c_region3 c_c_region3 ,
c.c_industry1 c_c_industry1 ,
c.c_industry2 c_c_industry2 ,
c.c_pm c_c_pm ,
c.c_pmb c_c_pmb ,
c.c_money c_c_money ,
c.c_is_abnormal c_c_is_abnormal ,
c.c_memo c_c_memo ,
c.c_state c_c_state ,
c.c_warranty c_c_warranty ,
c.c_warranty_end c_c_warranty_end ,
c.c_is_expired c_c_is_expired ,
c.c_client_aspiration c_c_client_aspiration ,
c.c_is_template c_c_is_template ,
c.c_is_template_boutique c_c_is_template_boutique ,
c.c_memo2 c_c_memo2 ,
c.c_is_difficult c_c_is_difficult ,
c.c_is_good c_c_is_good ,
c.c_is_template_conditions c_c_is_template_conditions ,
c.c_version1 c_c_version1 ,
c.c_version2 c_c_version2 ,
c.c_is_develop c_c_is_develop ,
c.c_develop_info c_c_develop_info ,
c.c_itsm_level c_c_itsm_level ,
c.c_itsm_number c_c_itsm_number ,
c.c_itsm_mode c_c_itsm_mode ,
c.c_itsm_software c_c_itsm_software ,
c.c_itsm_attachment c_c_itsm_attachment ,
c.c_info c_c_info ,
c.c_config c_c_config ,
c.c_create_user c_c_create_user ,
c.c_create_time c_c_create_time ,
c.c_modify_user c_c_modify_user ,
c.c_modify_time c_c_modify_time ,
c.c_tag1 c_c_tag1 ,
c.c_tag2 c_c_tag2 ,
c.c_tag3 c_c_tag3 ,
c.c_tag4 c_c_tag4 ,
p.c_id,
p.c_code,
p.c_customer_id,
p.c_name,
p.c_short_name,
p.c_order_time,
p.c_money,
p.c_begin_date,
p.c_end_date,
p.c_category,
p.c_pm,
p.c_state,
p.c_info,
p.c_config,
p.c_create_user,
p.c_create_time,
p.c_modify_user,
p.c_modify_time,
p.c_tag1,
p.c_tag2,
p.c_tag3,
p.c_tag4
FROM t_customer c
INNER JOIN t_project p
ON p.c_customer_id=c.c_id;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

