#!/bin/bash

username=admin
password=123456
dbname=report

mysqldump -u $username -p$password --single_transaction --opt $dbname | gzip > /home/portofino-4.1.beta5/db/itsboard200_`date '+%Y%m%d_%H%M%S'`.sql.gz

#15 * * * * /home/portofino-4.1.beta5/db/backup_mysql.sh
#mysqldump -u $username -p$password $dbname --result-file=$dbname_`date '+%Y%m%d_%H%M%S'`.sql
#mysqldump -u admin -padmin itsboard200 --result-file=itsboard200_`date '+%Y%m%d_%H%M%S'`.sql