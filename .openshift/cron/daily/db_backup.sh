#!/bin/sh
mkdir -p ${OPENSHIFT_DATA_DIR}backups
timestamp=$( date +%T )

mysqldump --host=${OPENSHIFT_MYSQL_DB_HOST}:${OPENSHIFT_MYSQL_DB_PORT} --user=${OPENSHIFT_MYSQL_DB_USERNAME} --password=${OPENSHIFT_MYSQL_DB_PASSWORD} beer > ${OPENSHIFT_DATA_DIR}backups/beer-$timestamp

rc=$?
if [ $rc -ne 0 ] ; then
  exit $rc
fi

cat ${OPENSHIFT_DATA_DIR}backups/beer-$timestamp | gzip -v > ${OPENSHIFT_DATA_DIR}backups/beer-$timestamp.gz

rc=$?
if [ $rc -ne 0 ] ; then
  exit $rc
fi

rm ${OPENSHIFT_DATA_DIR}backups/beer-$timestamp
find ${OPENSHIFT_DATA_DIR}backups/ -mindepth 1 -mtime +1 -delete
