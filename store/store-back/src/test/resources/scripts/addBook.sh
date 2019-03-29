curl -X POST \
  'http://localhost:8181/book/add' \
  -H 'x-auth-token: f3c0265a-1039-4eec-907a-c6456099d6d1'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"author":"fadsfa","title":"fdasdfaf","publisher":"fdadfsa"}'
