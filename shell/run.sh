#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(
  cd "$(dirname "$0")"
  pwd
)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..

export LOG_LEVEL=DEBUG

main() {
  local sub_command="$1"

  cd "${PROJECT_HOME}"

  case "${sub_command}" in

  develop)
    ./gradlew \
      clean \
      flywayMigrate \
      generateJooq \
      bootRun
    ;;

  build)
    ./gradlew \
      clean \
      flywayMigrate --stacktrace \
      generateJooq \
      build
    ;;

  test)
    ./gradlew \
      clean \
      flywayMigrate \
      generateJooq \
      test
    ;;

  jar)
    java -jar build/libs/*.jar
    ;;

  *)
    $@
    ;;

  esac
}

main "$@"
