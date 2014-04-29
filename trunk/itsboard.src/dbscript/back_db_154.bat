set hostName= 172.17.189.154
set username=riil
set password=r4rfde32wsaq1
set port=3306
set dbname=itsboard200
set scriptdir=.

set params=--default-character-set=utf8 -h %hostName% -u%username% -p%password% -P%port% %dbname%
mysqldump -u %username% -p%password% %dbname% --result-file=%dbname%.sql
pause
mysqldump -u admin -padmin itsboard200 --result-file=itsboard200`date '+%Y-%m-%d %H:%M:%S'`.sql