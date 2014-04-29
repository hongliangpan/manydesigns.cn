-- riil 模块
SELECT m1.c_y c_name, m1.c_value c_value_m ,h1.c_value c_value_h FROM
( SELECT 'm' c_x, d.c_name c_y, COUNT(0) c_value, '部署模块' c_labelx, d.c_name c_labely FROM t_project_module m
INNER JOIN t_dict_module d ON d.c_id = m.c_module_id
INNER JOIN t_customer c ON m.c_customer_id = c.c_id
WHERE 1=1 GROUP BY d.c_id ) m1
INNER JOIN
( SELECT 'h' c_x, d.c_name c_y, COUNT(0) c_value , '热点模块', d.c_name c_module_name FROM t_project_module_hot m
INNER JOIN t_dict_module d ON d.c_id = m.c_module_id
INNER JOIN t_customer c ON m.c_customer_id = c.c_id
WHERE 1=1 GROUP BY d.c_id ) h1 ON m1.c_y=h1.c_y
ORDER BY m1.c_x DESC, m1.c_value DESC

-- 客户
SELECT c.c_code, c.c_short_name, c.c_region1, c.c_region2, c.c_region3, c.c_industry1, c.c_industry2, c.c_pm, c.c_money, c.c_is_abnormal, c.c_state 
FROM t_customer c limit 50

