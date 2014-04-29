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
-- temp table
DROP TABLE IF EXISTS tmp_rpt_tree_node;
CREATE TABLE IF NOT EXISTS tmp_rpt_tree_node
(
c_sno INT PRIMARY KEY AUTO_INCREMENT,
c_id INT,
c_level INT(11) DEFAULT NULL COMMENT '树层级',
c_path VARCHAR(255) DEFAULT NULL COMMENT '树级路径',
c_path_name VARCHAR(500) DEFAULT NULL COMMENT '全路径名称'
) ENGINE=MEMORY;
DELIMITER $$
-- root udpate
DROP PROCEDURE  IF EXISTS reportTypeUpdate $$
CREATE PROCEDURE reportTypeUpdate ()
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE v_id INT;

DECLARE cur1 CURSOR FOR 
SELECT c_id FROM t_rpt_type WHERE c_parent_id IS NULL OR c_parent_id ='';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

OPEN cur1;
FETCH cur1 INTO v_id;

WHILE done=0 DO
	  CALL reportTypeUpdateOneTree(v_id);
	  FETCH cur1 INTO v_id;
END WHILE;

CLOSE cur1;
END;
$$

DROP PROCEDURE  IF EXISTS reportTypeUpdateOneTree $$
 CREATE PROCEDURE reportTypeUpdateOneTree (IN rootId INT)
 BEGIN

	CREATE TABLE IF NOT EXISTS tmp_rpt_tree_node
	(
	c_sno INT PRIMARY KEY AUTO_INCREMENT,
	c_id int,
	c_level int(11) DEFAULT NULL COMMENT '树层级',
	c_path varchar(255) DEFAULT NULL COMMENT '树级路径',
	c_path_name varchar(500) DEFAULT NULL COMMENT '全路径名称'
	) ENGINE=MEMORY;
	
  DELETE FROM tmp_rpt_tree_node;
    INSERT INTO tmp_rpt_tree_node 
	SELECT NULL, t.c_id, 
	  1 AS c_level,
	  t.c_id c_path, 
	  t.c_name c_path_name 
	  FROM t_rpt_type t 
	  WHERE t.c_id=rootId;
  SET @@max_sp_recursion_depth=10;
  CALL reportChildTypeGenerate(rootId,2);
  UPDATE t_rpt_type d,tmp_rpt_tree_node tmp  SET d.c_level=tmp.c_level,d.c_path = tmp.c_path,d.c_full_path_name=tmp.c_path_name WHERE d.c_id =tmp.c_id;
  COMMIT;
 END;
 $$
DROP PROCEDURE  IF EXISTS reportChildTypeGenerate $$
 CREATE PROCEDURE reportChildTypeGenerate(IN rootId INT,IN nDepth INT)
 BEGIN
  DECLARE done INT DEFAULT 0;
  DECLARE v_id INT;
  DECLARE v_path VARCHAR(50);
  DECLARE v_path_name VARCHAR(500);    
  DECLARE cur1 CURSOR FOR 
  SELECT c_id FROM t_rpt_type WHERE c_parent_id=rootId;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
  
  INSERT INTO tmp_rpt_tree_node 
	SELECT NULL, t.c_id, 
	  nDepth AS c_level,
	  CONCAT(IFNULL(tmp.c_path,p.c_id), '.', t.c_id) c_path, 
	  CONCAT(IFNULL(tmp.c_path_name,p.c_name), '/', t.c_name) c_path_name 
	  FROM t_rpt_type t 
	  INNER JOIN t_rpt_type p  ON t.c_parent_id = p.c_id
	  LEFT JOIN tmp_rpt_tree_node tmp ON tmp.c_id =t.c_parent_id
	  WHERE t.c_parent_id=rootId;
  OPEN cur1;
  FETCH cur1 INTO v_id;

  WHILE done=0 DO
          CALL reportChildTypeGenerate(v_id,nDepth+1);
          FETCH cur1 INTO v_id;
  END WHILE;

  CLOSE cur1;
 END;
 $$
-- trigger
/*DROP TRIGGER IF EXISTS tri_dept_insert
$$
CREATE TRIGGER tri_dept_insert
AFTER INSERT ON t_rpt_type
FOR EACH ROW
BEGIN
	call reportTypeUpdateOneTree(1);
END$$

DROP TRIGGER IF EXISTS tri_dept_update$$
CREATE TRIGGER tri_dept_update
AFTER UPDATE ON t_rpt_type
FOR EACH ROW
BEGIN
   call reportTypeUpdateOneTree(1);
END$$*/
DELIMITER ;
set max_sp_recursion_depth=12;
call reportTypeUpdate();


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
