#!/usr/bin/env bash
# wait-for-it.sh simplified

set -e

hostport="$1"
shift
cmd="$@"

# tách host và port
host=$(echo $hostport | cut -d':' -f1)
port=$(echo $hostport | cut -d':' -f2)

until nc -z "$host" "$port"; do
  >&2 echo "Service $host:$port is unavailable - sleeping"
  sleep 1
done

>&2 echo "Service $host:$port is up - executing command"
exec $cmd
