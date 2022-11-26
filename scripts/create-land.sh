#!/bin/bash

curl localhost:8080/api/v1/lands -i -H 'Content-Type: application/json' -d '{
  "landId": "a1"
}'
