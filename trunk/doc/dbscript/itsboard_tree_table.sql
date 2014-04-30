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
DROP TABLE IF EXISTS tmp_tree_node;
CREATE TABLE tmp_tree_node
(
c_sno INT PRIMARY KEY AUTO_INCREMENT,
c_id int,
c_level int(11) DEFAULT NULL COMMENT '树层级',
c_path varchar(255) DEFAULT NULL COMMENT '树级路径',
c_path_name varchar(500) DEFAULT NULL COMMENT '全路径名称'
);
DROP TABLE IF EXISTS t_tree_node;
create table t_tree_node
(
c_id int primary key,
c_name varchar(20),
c_parent_id int
);
Insert INTO t_tree_node VALUES(1,'A',null);  
Insert INTO t_tree_node VALUES(2,'B',1);  
Insert INTO t_tree_node VALUES(3,'C',1);  
Insert INTO t_tree_node VALUES(4,'D',2);  
Insert INTO t_tree_node VALUES(5,'E',3);  
Insert INTO t_tree_node VALUES(6,'F',3);  
Insert INTO t_tree_node VALUES(7,'G',5);  
Insert INTO t_tree_node VALUES(8,'H',7);  
Insert INTO t_tree_node VALUES(9,'I',8);  
Insert INTO t_tree_node VALUES(10,'J',8); 
DELIMITER $$
DROP PROCEDURE  IF EXISTS showChildLst $$
 CREATE PROCEDURE showChildLst (IN rootId INT)
 BEGIN
  TRUNCATE TABLE tmp_tree_node;
  SET @@max_sp_recursion_depth=10;
  CALL createChildLst(rootId,0);
  SELECT tmp.*,t.* FROM tmp_tree_node tmp,t_tree_node t WHERE tmp.c_id=t.c_id ORDER BY tmp.c_sno;
 END;
 $$
DROP PROCEDURE  IF EXISTS createChildLst $$
 CREATE PROCEDURE createChildLst(IN rootId INT,IN nDepth INT)
 BEGIN
  DECLARE done INT DEFAULT 0;
  DECLARE v_id INT;
  DECLARE v_path VARCHAR(50);
  DECLARE v_path_name VARCHAR(500);    
  DECLARE cur1 CURSOR FOR 
  SELECT c_id FROM t_tree_node WHERE c_parent_id=rootId;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
  
  INSERT INTO tmp_tree_node 
	SELECT NULL, t.c_id, 
	  nDepth AS c_level,
	  CONCAT(IFNULL(tmp.c_path,p.c_id), '.', t.c_id) c_path, 
	  CONCAT(IFNULL(tmp.c_path_name,p.c_name), '/', t.c_name) c_path_name 
	  FROM t_tree_node t 
	  INNER JOIN t_tree_node p  ON t.c_parent_id = p.c_id
	  LEFT JOIN tmp_tree_node tmp ON tmp.c_id =t.c_parent_id
	  WHERE t.c_parent_id=rootId;

  OPEN cur1;
  FETCH cur1 INTO v_id;

  WHILE done=0 DO
          CALL createChildLst(v_id,nDepth+1);
          FETCH cur1 INTO v_id;
  END WHILE;

  CLOSE cur1;
 END;
 $$
DELIMITER ;
set max_sp_recursion_depth=12;
call showChildLst(1);
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
