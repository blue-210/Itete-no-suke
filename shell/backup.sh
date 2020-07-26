#!/usr/bin/env bash

set -euo pipefail

function main() {
  backupdb
  backupimages
}

function backupdb() {
  docker \
    run --rm --volumes-from itetenosuke-db \
    -v "$(pwd)/backup":/backup \
    busybox tar cvf /backup/db_backup_"$(date +%Y%m%d%H)".tar /var/lib/postgresql/data
}

function backupimages() {
  docker \
    run --rm --volumes-from itetenosuke-app \
    -v "$(pwd)/backup":/backup \
    busybox tar cvf /backup/images_backup_"$(date +%Y%m%d%H)".tar /static/images
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
  main "$@"
fi
