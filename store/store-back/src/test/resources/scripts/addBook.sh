curl -X POST \
  'http://localhost:8181/book/add' \
  -H 'x-auth-token: 73bcc895-5fab-462d-aa9b-e4408f7732af'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"author":"fadsfa","title":"fdasdfaf","publisher":"fdadfsa"}'
