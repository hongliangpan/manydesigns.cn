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
TRUNCATE TABLE t_blogd_forum;
INSERT INTO `t_blogd_forum` VALUES (1, 'ITS业务看板', NULL, 1);
INSERT INTO `t_blogd_forum` VALUES (2, '项目日报', NULL, 2);
INSERT INTO `t_blogd_forum` VALUES (3, '用户权限管理', NULL, 3);

TRUNCATE TABLE t_blogd_type;
INSERT INTO `t_blogd_type` VALUES (1, 'Bug', NULL, 1);
INSERT INTO `t_blogd_type` VALUES (2, '新需求', NULL, 2);
INSERT INTO `t_blogd_type` VALUES (3, '需求变更', NULL, 3);
INSERT INTO `t_blogd_type` VALUES (4, '改进建议', NULL, 4);
INSERT INTO `t_blogd_type` VALUES (5, '任务', NULL, 5);

TRUNCATE TABLE t_blogd_state;
INSERT INTO `t_blogd_state` VALUES (1, '新建', NULL, 1);
INSERT INTO `t_blogd_state` VALUES (2, '接受', NULL, 2);
INSERT INTO `t_blogd_state` VALUES (3, '处理中', NULL, 3);
INSERT INTO `t_blogd_state` VALUES (4, '挂起', NULL, 4);
INSERT INTO `t_blogd_state` VALUES (5, '完成', NULL, 5);
INSERT INTO `t_blogd_state` VALUES (6, '退回', NULL, 6);


TRUNCATE TABLE t_blogd_level;
INSERT INTO `t_blogd_level` VALUES (1, '高', NULL, 1);
INSERT INTO `t_blogd_level` VALUES (2, '中', NULL, 2);
INSERT INTO `t_blogd_level` VALUES (3, '低', NULL, 3);


TRUNCATE TABLE t_blogd_module;
INSERT INTO `t_blogd_module` VALUES (1, 1, '客户管理', NULL, 1);
INSERT INTO `t_blogd_module` VALUES (2, 1, '项目管理', NULL, 2);
INSERT INTO `t_blogd_module` VALUES (3, 1, '项目矩阵', NULL, 3);
INSERT INTO `t_blogd_module` VALUES (4, 1, '客户矩阵', NULL, 4);
INSERT INTO `t_blogd_module` VALUES (5, 1, '常规数据分析', NULL, 5);
INSERT INTO `t_blogd_module` VALUES (6, 1, '异常数据分析', NULL, 6);
INSERT INTO `t_blogd_module` VALUES (7, 1, '维保与过期', NULL, 7);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

