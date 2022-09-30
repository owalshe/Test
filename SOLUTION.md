Ran out of time to attempt part 3.

Curl Requests:
curl -X GET http://localhost:8080/binRangeInfoSearch/4319000000000001
curl -X DELETE http://localhost:8080/binRangeInfoSearch/D893D80C-E7CF-4BA7-9F26-AE289D29E136
curl -X POST http://localhost:8080/binRangeInfoSearch -H "Content-Type: application/json" -d '{"start": 8000, "end": 8999, "bankName": "BOI", "currencyCode": "USD"}'
curl -X PUT http://localhost:8080/binRangeInfoSearch/7DECD7DB-1E93-4A44-BEB9-D71E277A8239 -H "Content-Type: application/json" -d '{"ref": "7DECD7DB-1E93-4A44-BEB9-D71E277A8239", "start": 8000, "end": 8999, "bankName": "BOI", "currencyCode": "USD"}'
