set hostName= 172.17.189.239
set username=admin
set password=admin
set port=3306
set dbname=itsboard200
set scriptdir=.

set params=--default-character-set=utf8 -h %hostName% -u%username% -p%password% -P%port% %dbname%
echo "�����������ȱ��ݡ�����ִ�У��Ƿ������"
pause

mysql  %params%< %scriptdir%/itsboard_table.sql
mysql  %params%< %scriptdir%/itsboard_user.sql
mysql  %params%< %scriptdir%/itsboard_data.sql
mysql  %params%< %scriptdir%/itsboard_dept_table.sql

mysql  %params%< %scriptdir%/itsboard_data_test.sql

mysql  %params%< %scriptdir%/pm_daily_table.sql
mysql  %params%< %scriptdir%/pm_daily_data.sql
mysql  %params%< %scriptdir%/pm_daily_data_test.sql

mysql  %params%< %scriptdir%/bugs_table.sql
mysql  %params%< %scriptdir%/bugs_data.sql

mysql  %params%< %scriptdir%/report_table.sql
mysql  %params%< %scriptdir%/report_data.sql
mysql  %params%< %scriptdir%/report_type_tree_table.sql
mysql  %params%< %scriptdir%/report_data_test.sql


pause