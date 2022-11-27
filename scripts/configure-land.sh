#!/bin/bash

curl localhost:8080/api/v1/lands/a1/configure  -i -X PUT -H 'Content-Type: application/json' -d '{
  "waterConfigs": [
    {"cron": "0/5 * * ? * * *", "duration": 10, "amountOfWater": 210},
    {"cron": "0/10 * * ? * * *", "duration": 17, "amountOfWater": 89}
  ]
}'

