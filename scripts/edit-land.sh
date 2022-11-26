#!/bin/bash

curl localhost:8080/api/v1/lands/a1 -i -X PATCH -H 'Content-Type: application/json' -d '{
  "area": 123
}'
