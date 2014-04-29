INSERT INTO itsboard.t_customer 
SELECT
(@rowNum:=@rowNum+1) as c_id,
p.id c_code,
p.name c_name,
p.short_name  c_short_name,
p.region1 c_region1,
p.region2 c_region2,
p.region3 c_region3,
p.indestry c_industry1,
'' c_industry2,
'' c_pm,
p.pm_name c_pmb,
p.money  c_money,
0 c_is_abnormal,
'' c_memo,
p.state c_state,
p.warranty c_warranty,
null c_warranty_end,
p.warranty_pass c_is_expired,
p.client_aspiration  c_client_aspiration,
p.is_template c_is_template,
p.is_template c_is_template_boutique,
'' c_memo2,
p.is_difficult  c_is_difficult,
p.is_good  c_is_good,
p.is_tmpt_pass c_is_template_conditions,
p.version c_version1,
'' c_version2,
0 c_is_develop,
'' c_develop_info,
'' c_itsm_level,
'' c_itsm_number,
'' c_itsm_mode,
'' c_itsm_software,
'' c_itsm_attachment,
p.info c_info,
'' c_config,
'' c_tag1,
'' c_tag2,
'' c_tag3,
'' c_tag4
FROM
project AS p,
(Select (@rowNum :=0) ) b;

INSERT INTO itsboard.t_project
SELECT
(@rowNum:=@rowNum+1) as c_id,
p.id c_code,
'1' c_customer_id,
p.name c_name,
p.short_name  c_short_name,
p.order_time   c_order_time,
p.money  c_money,
p.begin_date  c_begin_date,
p.end_date   c_end_date,
p.category   c_category,
p.pm_name c_pm,
p.state c_state,
p.info c_info,
'' c_config,
'' c_tag1,
'' c_tag2,
'' c_tag3,
'' c_tag4
FROM
project AS p,
(Select (@rowNum :=0) ) b;

UPDATE itsboard.t_project p  SET  p.c_customer_id=c_id;