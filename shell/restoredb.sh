#!/usr/bin/env bash

set -euo pipefail

function main() {
  restoredb "$1"
  restart
}

function restoredb() {
  echo "DBをバックアップよりリストアします。"
  docker \
    run --rm --volumes-from itetenosuke-db \
    -v "$(pwd)/backup":/backup \
    busybox tar xvf /backup/db_backup_"${1}".tar
  echo "DBのリストアが完了しました。"
}

function restart() {
  echo "Dockerを再起動します。"
  docker-compose restart
  echo "Dockerを再起動しました。"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
  main "$@"
fi
