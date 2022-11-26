#!/bin/bash

curl localhost:8080/api/v1/lands/a1/configure  -i -X PUT -H 'Content-Type: application/json' -d '{
  "waterConfigs": [
    {"start": "10:00:00", "end": "11:00:00", "amountOfWater": 210},
    {"start": "17:00:07.456", "end": "19:31:43", "amountOfWater": 89}
  ]
}'

