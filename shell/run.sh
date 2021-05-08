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
export DATABASE_URL_POSTGRES="${DATABASE_URL_POSTGRES:-'jdbc:postgresql://localhost:5432/itetenosuke'}"
export DATABASE_USER="${DATABASE_USER:-sukeroku}"
export DATABASE_PASSWORD="${DATABASE_PASSWORD:-D23iKlso3iqoiad}"

main() {
  local sub_command="$1"

  cd "${PROJECT_HOME}"

  case "${sub_command}" in

  develop)
    ./gradlew \
      clean \
      flywayMigrate \
      generateTablesJooqSchemaSource \
      bootRun
    ;;

  build)
    ./gradlew \
      clean \
      dependencyCheckAnalyze \
      flywayMigrate \
      generateTablesJooqSchemaSource \
      build
    ;;

  test)
    ./gradlew \
      clean \
      flywayMigrate \
      generateTablesJooqSchemaSource \
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
