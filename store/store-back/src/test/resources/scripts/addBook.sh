curl -X POST \
  'http://localhost:8181/book/add' \
  -H 'x-auth-token: da2fed7d-79ca-4c05-905d-469eb32c05a5'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"author":"fadsfa","title":"fdasdfaf","publisher":"fdadfsa"}'
