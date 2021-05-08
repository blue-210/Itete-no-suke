#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(
  cd "$(dirname "$0")"
  pwd
)"
readonly PROJECT_HOME="${SCRIPT_DIR}/.."

export DATABASE_URL_POSTGRES="${DATABASE_URL_POSTGRES:-"jdbc:postgresql://localhost:5432/itetenosuke"}"
export DATABASE_USER="${DATABASE_USER:-sukeroku}"
export DATABASE_PASSWORD="${DATABASE_PASSWORD:-D23iKlso3iqoiad}"
export DATABASE="${DATABASE:-itetenosuke}"
export DATABASE_HOST="${DATABASE_HOST:-localhost}"
export POSTGRES_PASSWORD="${DATABASE_PASSWORD:-D23iKlso3iqoiad}"
export PGPASSWORD="${DATABASE_PASSWORD:-D23iKlso3iqoiad}"

main() {
  cd "${PROJECT_HOME}"

  # クリーンアップ
  docker-compose down --volumes

  # DB 起動
  docker-compose up -d
  # wait_for_postgresql_container_starting
  "${PROJECT_HOME}/shell/check_db_startup.sh" "${DATABASE_HOST}" "${DATABASE_USER}" "${DATABASE}"

  # ビルド
  "${PROJECT_HOME}/shell/run.sh" build

  # クリーンアップ
  docker-compose down --volumes
}
main "$@"
