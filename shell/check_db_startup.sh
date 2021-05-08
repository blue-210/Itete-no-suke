#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

host="$1"
shift
user="$1"
shift
db="$1"

until psql -h "$host" -U "$user" -d "$db" -c '\l'; do
  echo >&2 "Waiting for postgres"
  sleep 1
done

echo >&2 "PostgreSQL is up"
exit
