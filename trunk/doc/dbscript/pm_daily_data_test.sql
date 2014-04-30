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

TRUNCATE TABLE t_pm_module;
INSERT INTO `t_pm_module` VALUES (1, 0, '短信接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2014-1-15 15:21:33');
INSERT INTO `t_pm_module` VALUES (2, 0, '告警报表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2014-1-15 15:21:41');

TRUNCATE TABLE t_pm_project;
INSERT t_pm_project SELECT
p.c_id,
p.c_code,
p.c_customer_id,
p.c_name,
p.c_short_name,
p.c_pm,
p.c_pm,
p.c_pm,
22,
p.c_category,
p.c_state,
p.c_state,
p.c_order_time,
p.c_money,
p.c_begin_date,
p.c_end_date,
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
FROM
t_project AS p
;


truncate table t_pm_daily;
INSERT INTO `t_pm_daily` VALUES (1, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '','', '', '2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (2, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '', '','2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (3, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (4, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (5, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (6, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '', '','2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (7, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (8, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (9, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '','', '', '2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (0, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '','', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);


INSERT INTO `t_pm_daily` VALUES (11, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (12, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '', '','2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (13, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (14, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (15, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (16, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (17, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (18, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (19, 'super', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'super', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (20, 'super', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '', '','2014-1-15 16:25:28', 'super', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);


INSERT INTO `t_pm_daily` VALUES (21, 'admin', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '','', '', '2014-1-15 15:49:43', 'admin', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (22, 'admin', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '', '', '','2014-1-15 16:25:28', 'admin', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (23, 'admin', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'admin', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (24, 'admin', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'admin', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (25, 'admin', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'admin', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (26, 'admin', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'admin', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (27, 'admin', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '', '', '','2014-1-15 15:49:43', 'admin', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (28, 'admin', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'admin', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (29, 'admin', 'dsad', 'eae', '2014-1-15', '1', 44.0, 1, 44, '44', '4', '', '', '','', '', '2014-1-15 15:49:43', 'admin', '2014-1-15 15:50:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_pm_daily` VALUES (30, 'admin', '434', '4345', '2014-1-15', '1', 44.0, 13, NULL, '', NULL, NULL, NULL, '','', '', '2014-1-15 16:25:28', 'admin', '2014-1-15 16:25:28', NULL, NULL, NULL, NULL, NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

