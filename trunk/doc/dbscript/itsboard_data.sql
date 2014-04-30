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

insert  into t_dict_complaint(c_id,c_name,c_config,c_sort_id) values (1,'加备注',NULL,1),(2,'商务',NULL,2),(3,'服务',NULL,3),(4,'产品',NULL,4),(5,'其它',NULL,5);

insert  into t_dict_industry(c_id,c_type1,c_type2,c_config,c_sort_id,c_tag1,c_tag2) values (1,'金融','保险',NULL,1,NULL,NULL),(2,'金融','银行',NULL,2,NULL,NULL),(3,'金融','邮政集团',NULL,3,NULL,NULL),(4,'金融','证券',NULL,4,NULL,NULL),(5,'SMB','酒店',NULL,5,NULL,NULL),(6,'SMB','网吧',NULL,6,NULL,NULL),(7,'政府','政务',NULL,7,NULL,NULL),(8,'政府','公检法司',NULL,8,NULL,NULL),(9,'政府','党务',NULL,9,NULL,NULL),(10,'政府','央企',NULL,10,NULL,NULL),(11,'政府','能源交通',NULL,11,NULL,NULL),(12,'政府','军队',NULL,12,NULL,NULL),(13,'教育','校园网',NULL,13,NULL,NULL),(14,'教育','实验室',NULL,14,NULL,NULL),(15,'企业','制造业',NULL,15,NULL,NULL),(16,'企业','现代服务业',NULL,16,NULL,NULL),(17,'企业','文化产业',NULL,17,NULL,NULL),(18,'企业','企业其他',NULL,18,NULL,NULL),(19,'运营商','电信运营商',NULL,19,NULL,NULL),(20,'互联网','大型互联网企业',NULL,20,NULL,NULL),(21,'互联网','中小型互联网企业',NULL,21,NULL,NULL),(22,'互联网','IDC企业',NULL,22,NULL,NULL),(23,'医疗','医院',NULL,23,NULL,NULL),(24,'医疗','区卫/基卫',NULL,24,NULL,NULL),(25,'公共事业','文化',NULL,25,NULL,NULL),(26,'公共事业','交通',NULL,26,NULL,NULL);

insert  into t_dict_region(c_id,c_region1,c_region2,c_region3,c_sort_id,c_config,c_tag1,c_tag2) values (1,'北区','东北','大连',1,NULL,NULL,NULL),(2,'北区','东北','黑龙江',2,NULL,NULL,NULL),(3,'北区','东北','吉林',3,NULL,NULL,NULL),(4,'北区','东北','辽宁',4,NULL,NULL,NULL),(5,'北区','华北','河南',5,NULL,NULL,NULL),(6,'北区','华北','湖北',6,NULL,NULL,NULL),(7,'北区','华北','江西',7,NULL,NULL,NULL),(8,'北区','华北','山西',8,NULL,NULL,NULL),(9,'北区','津鲁','山东',9,NULL,NULL,NULL),(10,'北区','津鲁','天津',10,NULL,NULL,NULL),(11,'北区','京冀','北京',11,NULL,NULL,NULL),(12,'北区','京冀','河北',12,NULL,NULL,NULL),(13,'北区','京冀','行业系统部',13,NULL,NULL,NULL),(14,'南区','国际','国际',14,NULL,NULL,NULL),(15,'南区','华东','福建',15,NULL,NULL,NULL),(16,'南区','华东','厦门',16,NULL,NULL,NULL),(17,'南区','华南','广东',17,NULL,NULL,NULL),(18,'南区','华南','海南',18,NULL,NULL,NULL),(19,'南区','华南','深圳',19,NULL,NULL,NULL),(20,'南区','华中','安徽',20,NULL,NULL,NULL),(21,'南区','华中','湖南',21,NULL,NULL,NULL),(22,'南区','华中','江苏',22,NULL,NULL,NULL),(23,'南区','华中','上海',23,NULL,NULL,NULL),(24,'南区','华中','苏宁',24,NULL,NULL,NULL),(25,'南区','华中','浙江',25,NULL,NULL,NULL),(26,'西区','西北','甘肃',26,NULL,NULL,NULL),(27,'西区','西北','内蒙古',27,NULL,NULL,NULL),(28,'西区','西北','宁夏',28,NULL,NULL,NULL),(29,'西区','西北','青海',29,NULL,NULL,NULL),(30,'西区','西北','陕西',30,NULL,NULL,NULL),(31,'西区','西北','新疆',31,NULL,NULL,NULL),(32,'西区','西南','广西',32,NULL,NULL,NULL),(33,'西区','西南','贵州',33,NULL,NULL,NULL),(34,'西区','西南','四川',34,NULL,NULL,NULL),(35,'西区','西南','西藏',35,NULL,NULL,NULL),(36,'西区','西南','云南',36,NULL,NULL,NULL),(37,'西区','西南','重庆',37,NULL,NULL,NULL);

insert  into t_dict_state(c_id,c_name,c_sort_id,c_config) values (1,'售后',1,NULL),(2,'待验收',2,NULL),(3,'实施中',3,NULL),(4,'未启动',4,NULL),(5,'挂起',5,NULL),(6,'无需实施',6,NULL);

insert  into t_dict_version(c_id,c_product,c_version,c_config,c_sort_id,c_tag1,c_tag2) values (1,'RIIL','6.1',NULL,1,NULL,NULL),(2,'RIIL','6.1.1',NULL,2,NULL,NULL),(3,'RIIL','6.1.2',NULL,3,NULL,NULL),(4,'RIIL','6.1.7',NULL,4,NULL,NULL),(5,'RIIL','6.2',NULL,5,NULL,NULL),(6,'RIIL','6.2.1',NULL,6,NULL,NULL),(7,'RIIL','6.2.2',NULL,7,NULL,NULL),(8,'RIIL','6.3',NULL,8,NULL,NULL),(9,'RIIL','Smart6.0',NULL,9,NULL,NULL);

insert  into t_dict_module(c_id,c_name,c_sort_id,c_config) values (1,'01 首页',1,NULL),(2,'02 IT健康指数',2,NULL),(3,'03 业务服务',3,NULL),(4,'04 事件告警中心',4,NULL),(5,'05 资源管理',5,NULL),(6,'06 网络管理',6,NULL),(7,'07 无线管理',7,NULL),(8,'08 虚拟化管理',8,NULL),(9,'09 日志监控',9,NULL),(10,'10 流量分析',10,NULL),(11,'11 机房管理',11,NULL),(12,'12 CMDB',12,NULL),(13,'13 知识库',13,NULL),(14,'14 KPI&统计报表',14,NULL),(15,'15 存储管理',15,NULL),(16,'16 分级管理',16,NULL),(17,'17 IPad客户端',17,NULL);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

