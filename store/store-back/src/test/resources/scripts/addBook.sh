curl -X POST \
  'http://localhost:8181/book/add' \
  -H 'x-auth-token: 16674a35-7b59-406b-8923-9c61bfa592c6'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"author":"fadsfa","title":"fdasdfaf","publisher":"fdadfsa"}'
