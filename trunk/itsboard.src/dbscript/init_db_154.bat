set hostName= 172.17.189.15400
set username=riil
set password=r4rfde32wsaq1
set port=3306
set dbname=itsboard200000
set scriptdir=.

set params=--default-character-set=utf8 -h %hostName% -u%username% -p%password% -P%port% %dbname%

mysql  %params%< %scriptdir%/itsboard_table.sql
mysql  %params%< %scriptdir%/itsboard_data.sql
mysql  %params%< %scriptdir%/itsboard_data_test.sql
pause
mysql  %params%< %scriptdir%/itsboard_user.sql


pause