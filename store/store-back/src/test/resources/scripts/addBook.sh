curl -X POST \
  'http://localhost:8181/api/v1/book/add' \
  -H 'x-auth-token: 9db8eae7-855b-4f57-8cb9-b9dd94230ff6'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"author":"fadsfa","title":"fdasdfaf","publisher":"fdadfsa"}'
