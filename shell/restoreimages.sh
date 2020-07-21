#!/usr/bin/env bash

set -euo pipefail

function main() {
  restoreimages "$1"
  restart
}

function restoreimages() {
  echo "画像をバックアップよりリストアします。"
  docker \
    run --rm --volumes-from itetenosuke-app \
    -v "$(pwd)/backup":/backup \
    busybox tar xvf /backup/images_backup_"${1}".tar
  echo "画像のリストアが完了しました。"
}

function restart() {
  echo "Dockerを再起動します。"
  docker-compose restart
  echo "Dockerを再起動しました。"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
  main "$@"
fi
