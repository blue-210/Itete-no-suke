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

main() {
  cd "${PROJECT_HOME}"

  # クリーンアップ
  docker-compose down

  # MySQL 起動
  docker-compose up -d
  # wait_for_postgresql_container_starting

  # ビルド
  "${PROJECT_HOME}/shell/run.sh" build

  # クリーンアップ
  docker-compose down
}

main "$@"
