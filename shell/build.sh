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

readonly DB_SERVICE='postgreSQL'

get_log_count_in_service() {
  local target_service="$1"
  local grep_condition="$2"

  docker-compose logs "${target_service}" |
    grep "${grep_condition}" |
    wc -l
}

#
# PostgreSQL のコンテナが起動完了したかを返します
#
is_postgresql_container_ready() {
  local ready_log_count="$(get_log_count_in_service "${DB_SERVICE}" 'postgred: ready for connections.')"
  [[ ready_log_count -eq 2 ]]
}

#
# 対象のコンテナでエラーが発生したかを返します
#
error_occurred_in_service() {
  local target_service="$1"

  local error_log_count="$(get_log_count_in_service "${target_service}" 'ERROR')"
  [[ error_log_count -gt 0 ]]
}

#
# MySQL のコンテナの起動を待ちます
#
wait_for_postgresql_container_starting() {
  while true; do
    if is_postgresql_container_ready; then
      set +o xtrace
      echo '=================================='
      echo "${DB_SERVICE} container is ready !"
      echo '=================================='
      set -o xtrace
      break

    elif error_occurred_in_service "${DB_SERVICE}"; then
      set +o xtrace
      echo '==========================================' >&2
      echo "Error occurred in ${DB_SERVICE} container." >&2
      echo '==========================================' >&2
      set -o xtrace
      break

    else
      # 起動中の場合
      echo 'Waiting for container starting...'
      sleep 1
    fi
  done
}

main() {
  cd "${PROJECT_HOME}"

  # クリーンアップ
  docker-compose down

  # MySQL 起動
  docker-compose up -d
  # wait_for_postgresql_container_starting

  # ビルド
  "${PROJECT_HOME}/gradlew" build --stacktrace

  # クリーンアップ
  docker-compose down
}

main "$@"
