/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- **********************************************************
-- 报表 rpt 区别于 已有的report
-- 报表相关 字典rptd_ 
-- 报表相关日志 t_rptl

-- **********************************************************

DROP TABLE IF EXISTS t_rptd_field_map;
CREATE TABLE t_rptd_field_map(
  c_id varchar(60) NOT NULL COMMENT '字段',
  c_name varchar(50) NOT NULL COMMENT '显示名称',
  c_data_type varchar(30) COMMENT '数据类型',
  c_unit varchar(50) COMMENT '单位',
  c_create_user varchar(36) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(36) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 VARCHAR(50) COMMENT '冗余字段',
  c_tag2 VARCHAR(50) COMMENT '冗余字段',
  c_tag3 VARCHAR(50) COMMENT '冗余字段',
  c_tag4 VARCHAR(50) COMMENT '冗余字段', 
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='字段名称映射';

DROP TABLE IF EXISTS t_rptd_data_type;
CREATE TABLE t_rptd_data_type(
  c_id varchar(36) NOT NULL COMMENT '编码',
  c_name varchar(200) NOT NULL COMMENT '类型',
  c_sort_id int(11)  COMMENT '顺序',
  c_tag1 VARCHAR(50) COMMENT '冗余字段',
  c_tag2 VARCHAR(50) COMMENT '冗余字段',
  c_tag3 VARCHAR(50) COMMENT '冗余字段',
  c_tag4 VARCHAR(50) COMMENT '冗余字段', 
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='字段数据类型';

DROP TABLE IF EXISTS t_rptd_period_type;
CREATE TABLE t_rptd_period_type(
  c_id varchar(36) NOT NULL COMMENT '编码',
  c_name varchar(200) NOT NULL COMMENT '周期',
  c_is_enabled tinyint(4)  COMMENT '是否启用',
  c_table_suffix varchar(20) COMMENT '数据表名后缀，如_xx，替换为1h,6h,1d,1w,',
  c_sort_id int(11)  COMMENT '顺序',
  c_tag1 VARCHAR(50) COMMENT '冗余字段',
  c_tag2 VARCHAR(50) COMMENT '冗余字段',
  c_tag3 VARCHAR(50) COMMENT '冗余字段',
  c_tag4 VARCHAR(50) COMMENT '冗余字段', 
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='周期类型,小时、日报、周报、月报、季报、半年报、年报';

-- 报表类型
-- 模块角度：配置报表、性能报表、流量报表、告警报表、机房报表等
-- 资源角度：网络设备、主机、数据库、中间件等
-- 一级可以是 模块 ，二级是资源 ，三级是具体报表
DROP TABLE IF EXISTS t_rpt_type;
CREATE TABLE t_rpt_type(
  c_id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  c_code varchar(36) COMMENT '编码',
  c_name varchar(200) NOT NULL COMMENT '类型',
  c_parent_id varchar(36)  COMMENT '上级类型',
  c_path varchar(255)  COMMENT '树级路径',
  c_level tinyint(4)  COMMENT '树层级',
  c_sort_id int(11)  COMMENT '顺序',
  c_full_path_name varchar(500)  COMMENT '全路径名称',
  c_desc varchar(500)  COMMENT '备注',
  c_config varchar(500)  COMMENT '扩展信息',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_tag1 varchar(50)  COMMENT '备用字段',
  c_tag2 varchar(50)  COMMENT '备用字段',
  c_tag3 varchar(50)  COMMENT '备用字段',
  c_tag4 varchar(50)  COMMENT '备用字段',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='报表类型';

DROP TABLE IF EXISTS t_rptd_display_type;
CREATE TABLE t_rptd_display_type(
  c_id varchar(36) NOT NULL COMMENT '编码',
  c_name varchar(200) NOT NULL COMMENT '类型',
  c_class varchar(500) COMMENT '实现类',
  c_sort_id int(11)  COMMENT '顺序',
  c_desc varchar(500)  COMMENT '备注',
  c_config varchar(500)  COMMENT '扩展信息',
  c_tag1 varchar(50)  COMMENT '备用字段',
  c_tag2 varchar(50)  COMMENT '备用字段',
  c_tag3 varchar(50)  COMMENT '备用字段',
  c_tag4 varchar(50)  COMMENT '备用字段',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='报表展现类型，如表、图、图表、表图、组合、定制';

-- 报表定义 及参数定义
DROP TABLE IF EXISTS t_rpt_def;
CREATE TABLE t_rpt_def (
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_name_en varchar(200) NOT NULL COMMENT '英文名称',
  c_name varchar(200) NOT NULL COMMENT '报表名称', 
  c_title varchar(2000) COMMENT '报表标题', 
  c_sub_title varchar(2000) COMMENT '报表子标题', 
  c_type_id varchar(20) NOT NULL  COMMENT '类型',
  c_display_type_id varchar(36) NOT NULL  COMMENT '展现类型',
  c_is_regular tinyint(4) COMMENT '是否定期报表',
  c_is_query tinyint(4) COMMENT '是否查询报表',
  c_class varchar(500) COMMENT '实现类',
  c_chart_type varchar(20) COMMENT '图类型',
  c_chart_orientation varchar(20) COMMENT '图方向，水平Horizontal，垂直Vertical',
  c_sql varchar(4000) COMMENT '表格sql',
  c_chart_sql varchar(4000) COMMENT '附属图sql',
  c_chart_x varchar(100) COMMENT '图X轴字段',
  c_chart_y varchar(100) COMMENT '图Y轴字段',
  c_chart_value varchar(100) COMMENT '图值字段',
  c_row_number_type varchar(10) COMMENT '行号类型',
  c_is_show_page_number tinyint(4) default 1 COMMENT '是否显示页码',
  c_is_enabled tinyint(4) default 1 COMMENT '是否启用',
  c_sub_rpt_def_ids varchar(2000) COMMENT '子报表id',
  c_help varchar(2000) COMMENT '帮助说明',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='报表定义';

DROP TABLE IF EXISTS t_rpt_def_field;
CREATE TABLE t_rpt_def_field (
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_rpt_def_id varchar(36) NOT NULL COMMENT '报表定义',
  c_field varchar(200) NOT NULL COMMENT '字段，如：res.c_res_type_id',
  c_calc_exp varchar(200) COMMENT '计算表达式，如2倍均值',
  c_head_group varchar(100) COMMENT '列分组，子head为最大值最小值',
  c_head_group2 varchar(100) COMMENT '列分组2，如流量 子head为入流量、出流量',
  c_width int(5) COMMENT '宽度',
  c_display_name varchar(200) NOT NULL COMMENT '显示名称，如：资源类型',
  c_alignment varchar(10) COMMENT '对齐方式，enum HorizontalAlignment LEFT,CENTER,RIGHT,JUSTIFIED',
  c_pattern varchar(200) COMMENT '显示样式，如:#,###.0',
  c_data_type varchar(30) COMMENT '数据类型',
  c_is_group tinyint(4) COMMENT '是否分组字段',
  c_group_alignment varchar(10) COMMENT '对齐方式',
  c_is_sum tinyint(4) COMMENT '是否全局汇总字段',
  c_is_subtotal tinyint(4) COMMENT '是否分组汇总字段',
  c_sum_method varchar(500) COMMENT '汇总方法',
  c_is_percentage tinyint(4) COMMENT '是否百分比字段',
  c_is_query_time tinyint(4) COMMENT '是否查询时间字段',
  c_query_range varchar(10) COMMENT '查询访问 =、in、between、!=,notin',
  c_url varchar(500) COMMENT '钻取页面url',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_def_field_id FOREIGN KEY (c_rpt_def_id) REFERENCES t_rpt_def(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表定义字段';
/* 安装A字段分组c_is_group,按照B字段汇总c_is_subtotal ;c_is_sum是否全局汇总 */

DROP TABLE IF EXISTS t_rpt_def_period;
CREATE TABLE t_rpt_def_period (
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_rpt_def_id varchar(36) NOT NULL COMMENT '报表定义',
  c_period_type_id varchar(50) COMMENT '周期类型',
  c_table_name varchar(20) COMMENT 'sql中数据表名后缀',
  c_table_suffix varchar(20) COMMENT '数据表名后缀，如_xx，替换为1h,6h,1d,1w,1m',
  c_sort_id int(11)  COMMENT '顺序',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_def_period_id FOREIGN KEY (c_rpt_def_id) REFERENCES t_rpt_def(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表定义周期';

DROP TABLE IF EXISTS t_rpt_def_param;
CREATE TABLE t_rpt_def_param (
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_rpt_def_id varchar(36) NOT NULL COMMENT '报表定义',
  c_field varchar(200) NOT NULL COMMENT '参数字段，如：c_res_type_id',
  c_display_name varchar(200) NOT NULL COMMENT '显示名称，如：资源类型',
  c_tips varchar(200) COMMENT '提示',
  c_sql varchar(2000) COMMENT '参数数据来源sql',
  c_is_single tinyint(4) COMMENT '是否单值，1=单选；0多选',
  c_is_query_param tinyint(4) COMMENT '是否查询参数',
  c_is_build_param tinyint(4) COMMENT '是否构建参数',  
  c_display_style varchar(32) COMMENT '显示类型，下拉框、单选、复选、左右选择、弹出窗口、custom等',
  c_param_type varchar(36) COMMENT '参数类型,如：资源选择、接口选择',
  c_url varchar(500) COMMENT '定制页面url',
  c_depend_def_param_id varchar(36) COMMENT '依赖参数',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10) COMMENT '排序号',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_def_param_id FOREIGN KEY (c_rpt_def_id) REFERENCES t_rpt_def(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表定义参数';


-- 报表实例-------------------------------
DROP TABLE IF EXISTS t_rpt;
CREATE TABLE t_rpt(
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_rpt_def_id varchar(36) NOT NULL COMMENT '报表定义',
  c_name varchar(200)  NOT NULL COMMENT '报表名称',
  c_title varchar(2000) COMMENT '报表标题', 
  c_sub_title varchar(2000) COMMENT '报表子标题', 
  c_order_field varchar(32) COMMENT '排序字段',
  c_order_type varchar(32) COMMENT '排序方式:asc/desc',
  c_doc_types varchar(36) NOT NULL COMMENT '生成文件类型',
  c_page_rows tinyint(4) COMMENT '每页行数',
  c_topn tinyint(4) COMMENT 'Topn数量',
  c_group_id varchar(36) COMMENT '分组ID',
  c_domain_id varchar(36) COMMENT '所属域ID',
  c_region_id varchar(36) COMMENT '区域ID',
  c_param_value1 varchar(100) COMMENT '参数1，key=value',
  c_param_value2 varchar(100) COMMENT '参数2',
  c_param_value3 varchar(200) COMMENT '参数3',
  c_param_value4 varchar(200) COMMENT '参数4',
  c_param_value5 varchar(200) COMMENT '参数5',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id),
  KEY UQ_rpt(c_create_user)
) ENGINE=InnoDB  COMMENT='报表';

DROP TABLE IF EXISTS t_rpt_param;
CREATE TABLE t_rpt_param(
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_rpt_id varchar(36) COMMENT '报表',
  c_def_param_id varchar(36) COMMENT '参数定义',
  c_value_id varchar(60) COMMENT '参数值ID',
  c_value_name varchar(60) COMMENT '参数值名称',
  c_value_info1 varchar(60) COMMENT '其他信息1',
  c_value_info2 varchar(60) COMMENT '其他信息2',
  c_value_info3 varchar(60) COMMENT '其他信息3',
  c_value_info4 varchar(60) COMMENT '其他信息4',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_param_id FOREIGN KEY (c_rpt_id) REFERENCES t_rpt(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表参数';

DROP TABLE IF EXISTS t_rpt_field;
CREATE TABLE t_rpt_field(
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_rpt_id varchar(36) COMMENT '报表',
  c_def_field_id varchar(36) COMMENT '字段定义',
  c_calc_exp varchar(200) COMMENT '计算表达式，如2倍均值',
  c_is_display tinyint(4) COMMENT '是否显示',
  c_display_name varchar(100) COMMENT '显示名称',
  c_config varchar(500) COMMENT '扩展信息',
  c_sort_id int(10)  COMMENT '排序号',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_field_id FOREIGN KEY (c_rpt_id) REFERENCES t_rpt(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表字段';

DROP TABLE IF EXISTS t_rpt_period;
CREATE TABLE t_rpt_period(
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_period_type_id varchar(50) COMMENT '周期类型',
  c_rpt_id varchar(36) COMMENT '报表',
  c_def_period_id varchar(36) COMMENT '定义周期',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
  ,CONSTRAINT fk_rpt_period_id FOREIGN KEY (c_rpt_id) REFERENCES t_rpt(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表参数';
/*
DROP TABLE IF EXISTS t_rpt_doc_type;
CREATE TABLE t_rpt_doc_type(
  c_rpt_id varchar(36) NOT NULL COMMENT '报表',
  c_doc_type varchar(36) NOT NULL COMMENT '文件类型',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_rpt_id,c_doc_type)
  ,CONSTRAINT fk_rpt_doc_id FOREIGN KEY (c_rpt_id) REFERENCES t_rpt(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='报表参数';
*/
-- 订阅信息----
DROP TABLE IF EXISTS t_rpt_subscribe;
CREATE TABLE t_rpt_subscribe (
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_rpt_id varchar(36) NOT NULL COMMENT '报表ID',
  c_period_type_id varchar(50) COMMENT '周期类型',
  c_rpt_name varchar(200) COMMENT '订阅的报表名称',
  c_file_format varchar(20) COMMENT '文件格式： PDF XLS',
  c_send_time varchar(100) NOT NULL COMMENT '发送时间',
  c_subscribe_user varchar(36) NOT NULL COMMENT '订阅用户ID',
  c_subscribe_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订阅时间',
  c_params varchar(2048) COMMENT '订阅参数Map',
  c_module VARCHAR(64) COMMENT '订阅报表所属模块 kpi：KPI统计分析；motoroom：机房模块',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id) 
) ENGINE=InnoDB  COMMENT='报表订阅';
CREATE INDEX idx_rpt_subscribe_user ON t_rpt_subscribe(c_subscribe_user);
CREATE INDEX idx_rpt_subscribe_query ON t_rpt_subscribe(c_period_type_id,c_rpt_name,c_subscribe_user,c_subscribe_time);

DROP TABLE IF EXISTS t_rptl_send_og;
CREATE TABLE t_rptl_send_og (
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_subscribe_id varchar(36) NOT NULL COMMENT '订阅单号ID',
  c_rpt_id varchar(36) COMMENT '报表id',
  c_name varchar(200) COMMENT '报表名称',
  c_start_time DATETIME NOT NULL COMMENT '发送开始时间',
  c_end_time DATETIME COMMENT '发送结束时间',  
  c_period_type_id varchar(36) NOT NULL COMMENT '周期类型',
  c_params varchar(2000) COMMENT '生成时参数信息',
  c_is_succ tinyint(4) COMMENT '是否成功',
  c_err_msg varchar(2000) COMMENT '错误信息',
  c_memo varchar(500) COMMENT '备注',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',  
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='报表订阅发送日志';
CREATE INDEX idx_rpt_subscribe_log ON t_rptl_send_og (c_subscribe_id);
create index idx_rpt_subscribe_log_time on t_rptl_send_og(c_start_time);


DROP TABLE IF EXISTS t_rptl_gen_log;
CREATE TABLE t_rptl_gen_log (
  c_id varchar(36) NOT NULL COMMENT '主键',
  c_rpt_id varchar(36) NOT NULL COMMENT '报表id',
  c_name varchar(200) COMMENT '报表名称',
  c_start_time DATETIME NOT NULL COMMENT '生成开始时间',
  c_end_time DATETIME COMMENT '生成结束时间',
  c_period_type_id varchar(36) NOT NULL COMMENT '周期类型',
  c_params varchar(2000) COMMENT '生成时参数信息',
  c_is_succ tinyint(4) COMMENT '是否成功',
  c_err_msg varchar(2000) COMMENT '错误信息',
  c_memo varchar(500) COMMENT '备注',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4', 
  PRIMARY KEY (c_id),
  KEY (c_rpt_id,c_start_time)
) ENGINE=InnoDB  COMMENT='报表生成日志';


DROP TABLE IF EXISTS t_rpt_group;
CREATE TABLE t_rpt_group(
  c_id varchar(36) NOT NULL COMMENT '编号',
  c_name varchar(200) COMMENT '分组',
  c_tag1 VARCHAR(50) COMMENT '冗余字段',
  c_tag2 VARCHAR(50) COMMENT '冗余字段',
  c_tag3 VARCHAR(50) COMMENT '冗余字段',
  c_tag4 VARCHAR(50) COMMENT '冗余字段', 
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB COMMENT='报表分组';

DROP TABLE IF EXISTS t_sys_seq;

CREATE TABLE t_sys_seq (
  c_name varchar(20) NOT NULL COMMENT '序列名',
  c_value int(10) UNSIGNED NOT NULL COMMENT '当前值',
  c_table varchar(40) COMMENT '对应表',
  PRIMARY KEY  (c_name)
);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('one',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('field',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('period',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('param',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('def_field',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('def_period',0);
INSERT INTO t_sys_seq(c_name,c_value) VALUES('def_param',0);

DELIMITER $$
DROP FUNCTION IF EXISTS seq$$
CREATE FUNCTION seq(seq_name char (20)) returns int
begin
UPDATE t_sys_seq SET c_value=last_insert_id(c_value+1) WHERE c_name=seq_name;
RETURN last_insert_id();
end$$
DELIMITER ;
-- SELECT SEQ('ONE'),SEQ('ONE');

DROP TABLE IF EXISTS t_rpti_import;
CREATE TABLE t_rpti_import(
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_name varchar(36) COMMENT '说明',
  c_sql varchar(2000) COMMENT 'sql',
  c_file varchar(2000) COMMENT '文件',
  c_table varchar(40) COMMENT '表',
  c_fields varchar(2000) COMMENT '字段s',
  c_config varchar(500) COMMENT '扩展信息',
  c_head_lines tinyint(4) COMMENT '表头行数',
  c_is_merge_cell tinyint(4) COMMENT '是否包含合并单元格',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
) ENGINE=InnoDB  COMMENT='数据导入';

DROP TABLE IF EXISTS t_rpti_import_log;
CREATE TABLE t_rpti_import_log(
  c_id varchar(36) NOT NULL COMMENT 'ID',
  c_import_id varchar(36) COMMENT 'import_id',
  c_file varchar(2000) COMMENT '文件',
  c_start_time DATETIME NOT NULL COMMENT '生成开始时间',
  c_end_time DATETIME COMMENT '生成结束时间',
  c_params varchar(2000) COMMENT '生成时参数信息',
  c_is_succ tinyint(4) COMMENT '是否成功',
  c_err_msg varchar(2000) COMMENT '错误信息',
  c_config varchar(500) COMMENT '扩展信息',
  c_tag1 VARCHAR(200) COMMENT '冗余字段1',
  c_tag2 VARCHAR(200) COMMENT '冗余字段2',
  c_tag3 VARCHAR(200) COMMENT '冗余字段3',
  c_tag4 VARCHAR(200) COMMENT '冗余字段4',
  c_create_user varchar(20) COMMENT '创建用户',
  c_create_time DATETIME COMMENT '创建时间',
  c_modify_user varchar(20) COMMENT '修改用户',
  c_modify_time TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (c_id)
 ,CONSTRAINT fk_rpti_import_id FOREIGN KEY (c_import_id) REFERENCES t_rpti_import(c_id) ON DELETE CASCADE
) ENGINE=InnoDB  COMMENT='数据导入日志';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
