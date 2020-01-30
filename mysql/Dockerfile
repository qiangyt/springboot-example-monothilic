FROM docker.wxcount.com:4443/library/mysql:5.7.21
LABEL maintainer='qiangyt@wxcount.com'

COPY ./mysql.cnf          /etc/mysql/conf.d
COPY ./create_table.sql   /docker-entrypoint-initdb.d/

