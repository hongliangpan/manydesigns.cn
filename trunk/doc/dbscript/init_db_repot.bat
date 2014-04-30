set hostName= 127.0.0.1
set username=admin
set password=123456
set port=3306
set dbname=report
set scriptdir=.

set params=--default-character-set=utf8 -h %hostName% -u%username% -p%password% -P%port% %dbname%



mysql  %params%< %scriptdir%/report_table.sql
mysql  %params%< %scriptdir%/report_data.sql
mysql  %params%< %scriptdir%/report_type_tree_table.sql

mysql  %params%< %scriptdir%/report_data_test_before.sql
mysql  %params%< %scriptdir%/report_data_test.sql
mysql  %params%< %scriptdir%/report_data_test_end.sql
pause